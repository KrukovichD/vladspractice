package com.example.vladspractice.presentation

import android.content.ContentValues
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vladspractice.Domain.models.NS_SEMK

@Composable
fun TableScreen(viewModel: MainViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val request by viewModel.request.observeAsState("")
    val dataList by viewModel.dataList.observeAsState(emptyList())
    val tableName = remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column {
            ColumnSelectionScreen(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            MyButton(text ="Получить данные из БД" ) {
                viewModel.fetchDataFromDb()
            }
            //InputRow(tableName)
            //buttonPlace(viewModel, navController)
            dataTable(dataList)
        }

    }
}


@Composable
fun ColumnSelectionScreen(viewModel: MainViewModel) {
    val availableColumns = listOf(
        "KMC", "KRK", "KT", "EMK", "PR", "KTARA", "GTIN", "EMKPOD"
    )
    val selectedColumns = viewModel.selectedColumns

    LazyColumn {
        itemsIndexed(availableColumns.chunked(4)) { rowIndex, rowColumns ->
            Row {
                rowColumns.forEach { column ->
                    val isSelected = selectedColumns.contains(column)
                    ColumnSelectionItem(
                        column = column,
                        isSelected = isSelected,
                        onToggle = {
                            if (isSelected) {
                                viewModel.removeColumn(column)
                            } else {
                                viewModel.addColumn(column)
                            }
                        }
                    )
                }
            }
        }
    }



}

@Composable
fun ColumnSelectionItem(column: String, isSelected: Boolean, onToggle: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onToggle() },
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(text = column, style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
fun InputRow(tableName: MutableState<String>){

    Column() {
        OutlinedTextField(
            value = tableName.value,
            onValueChange = { newText -> tableName.value = newText },
            label = { Text("Таблица") },
            modifier = Modifier.padding(bottom = 8.dp).width(200.dp).height(60.dp)
        )
    }
}
@Composable
fun dataTable(dataList: List<ContentValues>) {
    val scrollStateHorizontal = rememberScrollState()
    val scrollStateVertical = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
            .horizontalScroll(scrollStateHorizontal)
            .verticalScroll(scrollStateVertical)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(5.dp)
        ) {
            TableHeader(text = "№", 20)
            TableHeader(text = "PR", 20)
            TableHeader(text = "KRK", 50)
            TableHeader(text = "KT", 50)
            TableHeader(text = "EMK", 50)
            TableHeader(text = "EMKPOD", 50)
            TableHeader(text = "KMC", 120)
            TableHeader(text = "KTARA", 120)
            TableHeader(text = "GTIN", 120)

        }

        dataList.forEachIndexed { index, contentValues ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(if (index % 2 == 0) Color.White else Color.LightGray)
            ) {
                TableCell(text = (index+1).toString(), 20)
                TableCell(text = contentValues.getAsString("PR") ?: "", 20)
                TableCell(text = contentValues.getAsString("KRK") ?: "", 50)
                TableCell(text = contentValues.getAsString("KT") ?: "", 50)
                TableCell(text = contentValues.getAsString("EMK") ?: "", 50)
                TableCell(text = contentValues.getAsString("EMKPOD") ?: "", 50)
                TableCell(text = contentValues.getAsString("KMC") ?: "", 120)
                TableCell(text = contentValues.getAsString("KTARA") ?: "", 120)
                TableCell(text = contentValues.getAsString("GTIN") ?: "", 120)

            }
        }
    }
}

@Composable
fun TableHeader(text: String, weightRow: Int) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.LightGray)
            .width(weightRow.dp)
            .height(25.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun TableCell(text: String, weightRow: Int) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .width(weightRow.dp)
            .height(25.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = if (text.length > 15) "${text.take(14)}..." else text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun buttonPlace(viewModel: MainViewModel, navController: NavHostController) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyButton(text = "Main Screen") {
            navController.navigate("main_screen")
        }
        Spacer(modifier = Modifier.width(8.dp))
        MyButton(text = "Create Table") {
            viewModel.createTable(context, "request.json")
        }
        MyButton(text = "SQLite") {
            val tableName = "NS_SEMK"
            val selectedColumn = null
            val selectedValue = null
            val listColumnsForReturn = emptyList<String>()
            viewModel.getDataFromBd(tableName, selectedColumn, selectedValue, listColumnsForReturn)
        }
    }

}



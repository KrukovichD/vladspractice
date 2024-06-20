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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vladspractice.Domain.models.NS_SEMK

@Composable
fun TableScreen(viewModel: MainViewModel, navController: NavHostController) {
    val dataList by viewModel.dataList.observeAsState(emptyList())

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column {
            DataTable(dataList)
        }

    }
}
@Composable
fun DataTable(dataList: List<ContentValues>) {
    val scrollStateVertical = rememberLazyListState()
    val scrollStateHorizontal = rememberScrollState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = scrollStateVertical,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.White)
            .horizontalScroll(scrollStateHorizontal) // Используем вертикальный scrollStateHorizontal
    ) {
        item {
            TableHeaderRow()
        }

        items(dataList.size) { index ->
            val contentValues = dataList[index]
            DataTableRow(index + 1, contentValues)
        }
    }
}

@Composable
fun TableHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(5.dp)
    ) {
        TableHeader(text = "№", 40)
        TableHeader(text = "PR", 20)
        TableHeader(text = "KRK", 50)
        TableHeader(text = "KT", 50)
        TableHeader(text = "EMK", 50)
        TableHeader(text = "EMKPOD", 50)
        TableHeader(text = "KMC", 120)
        TableHeader(text = "KTARA", 120)
        TableHeader(text = "GTIN", 120)
        TableHeader(text = "id", 120)
    }
}

@Composable
fun DataTableRow(index: Int, contentValues: ContentValues) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
            .background(if (index % 2 == 0) Color.White else Color.LightGray)
    ) {
        TableCell(text = (index+1).toString(), 40)
        TableCell(text = contentValues.getAsString("PR") ?: "", 20)
        TableCell(text = contentValues.getAsString("KRK") ?: "", 50)
        TableCell(text = contentValues.getAsString("KT") ?: "", 50)
        TableCell(text = contentValues.getAsString("EMK") ?: "", 50)
        TableCell(text = contentValues.getAsString("EMKPOD") ?: "", 50)
        TableCell(text = contentValues.getAsString("KMC") ?: "", 120)
        TableCell(text = contentValues.getAsString("KTARA") ?: "", 120)
        TableCell(text = contentValues.getAsString("GTIN") ?: "", 120)
        TableCell(text = contentValues.getAsString("id") ?: "", 120)
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




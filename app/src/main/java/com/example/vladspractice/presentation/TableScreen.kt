package com.example.vladspractice.presentation

import android.content.ContentValues
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

@Composable
fun TableScreen(viewModel: MainViewModel, navController: NavHostController) {
    val dataList by viewModel.dataList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)

    if (isLoading) {
        IndeterminateCircularIndicator()
    } else {
        if (dataList.isNotEmpty()) {
            DataTable(dataList, viewModel)
        }
    }



}

@Composable
fun DataTable(dataList: List<ContentValues>, viewModel: MainViewModel) {
    val scrollStateVertical = rememberLazyListState()
    val scrollStateHorizontal = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .horizontalScroll(scrollStateHorizontal)
    ) {
        LazyColumn(
            state = scrollStateVertical,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                TableHeaderRow(viewModel.selectedColumnsReturn)
            }
            items(dataList.size) { index ->
                DataTableRow(index = index, contentValues = dataList[index], viewModel.selectedColumnsReturn)
            }
        }
    }
}

@Composable
fun IndeterminateCircularIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.5f))
    }
}




@Composable
fun TableHeaderRow(selectedColumnsReturn: List<String>) {
    selectedColumnsReturn.forEachIndexed { index, column ->
        Log.d("TableHeaderRow", "Column $index: $column")
    }

    if (selectedColumnsReturn.isEmpty()) {
        Text(
            text = "No columns selected",
            color = Color.Red,
            modifier = Modifier.padding(end = 5.dp)
        )
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(5.dp)
        ) {
            TableHeader(text = "№", weightRow = 30)
            selectedColumnsReturn.forEachIndexed { index, item ->
                Log.d("TableHeaderRow", "Rendering item at index $index: $item")
                TableHeader(item, 120)

            }
        }
    }
}

@Composable
fun DataTableRow(index: Int, contentValues: ContentValues, selectedColumnsReturn: List<String>) {
    if (selectedColumnsReturn.isNotEmpty()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp)
                .background(if (index % 2 == 0) Color.White else Color.LightGray)
        ) {
            TableCell(text = (index+1).toString(), weightRow = 30 )
            selectedColumnsReturn.forEach{ item ->
                TableCell(text = contentValues.getAsString(item) ?: "", weightRow = 120)
            }
        }
    }


}

@Composable
fun TableHeader(text: String, weightRow: Int) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.LightGray)
            .width(weightRow.dp)
            .height(25.dp),
        verticalArrangement = Arrangement.Center

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




package com.example.vladspractice.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import javax.sql.RowSetEvent

@Composable
fun SettingPage(viewModel: MainViewModel, navController: NavController) {
    var context = LocalContext.current
    Column {
        DropDown(viewModel)
        MyButton(text ="Получить данные из БД" ) {
            viewModel.fetchDataFromDb(viewModel)
            navController.navigate("table_screen")
        }
        ColumnSelectionScreen(viewModel)

    }

    LaunchedEffect(viewModel.selectedTable) {
        viewModel.getListTableField()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(viewModel: MainViewModel) {
    val list by viewModel.tableNames.observeAsState(emptyList())
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = viewModel.selectedTable,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                list.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            viewModel.selectedTable = text
                            isExpanded = false
                            viewModel.removeColumnReturn(null)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Text(text = "Currently selected: ${viewModel.selectedTable}")
    }
}


@Composable
fun ColumnSelectionScreen(viewModel: MainViewModel) {
    val availableColumns by viewModel.tableField.observeAsState(emptyList())
    val selectedColumns = viewModel.selectedColumnsReturn

    LazyColumn {
        itemsIndexed(availableColumns.chunked(6)) { rowIndex, rowColumns ->
            Column {
                rowColumns.forEach { column ->
                    val isSelected = selectedColumns.contains(column)
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                    ) {
                        ColumnSelectionItem(
                            column = column,
                            isSelected = isSelected,
                            onToggle = {
                                if (isSelected) {
                                    viewModel.removeColumnReturn(column)
                                } else {
                                    viewModel.addColumnReturn(column)
                                }
                            }
                        )

                        var text by rememberSaveable { mutableStateOf("") }
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            value = text,
                            onValueChange = { newText ->
                                text = newText
                                if (newText.isEmpty()) {
                                    viewModel.removeColumnValue(column)
                                } else {
                                    viewModel.addColumnValue(column, newText)
                                }
                            },
                            placeholder = {
                                Text("Enter value")
                            }
                        )
                    }
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





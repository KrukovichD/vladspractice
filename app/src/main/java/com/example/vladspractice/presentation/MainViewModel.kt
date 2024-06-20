package com.example.vladspractice.presentation

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vladspractice.Data.DB.SQL.SqlRepositoryImp
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.ParserRepository
import com.example.vladspractice.Domain.usecase.CreateTableUseCase
import com.example.vladspractice.Domain.usecase.DeleteDataUseCase
import com.example.vladspractice.Domain.usecase.GetDataFromFileUseCase
import com.example.vladspractice.Domain.usecase.GetDataUseCase
import com.example.vladspractice.Domain.usecase.GetListTableBdUseCase
import com.example.vladspractice.Domain.usecase.GetListTableFieldsUseCase
import com.example.vladspractice.Domain.usecase.GetRequestUseCase
import com.example.vladspractice.Domain.usecase.InsertDataUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DatabaseRepository,  private val fileRepository: ParserRepository) : ViewModel()  {


    private val insertUseCase = InsertDataUseCase(dataRepository)
    private val getDataseCase = GetDataUseCase(dataRepository)
    private val deleteDataUseCase = DeleteDataUseCase(dataRepository)
    private val createTable = CreateTableUseCase(dataRepository)
    private val getListTable = GetListTableBdUseCase(dataRepository)
    private val getListTableFieldsUseCase = GetListTableFieldsUseCase(dataRepository)

    private val getDataFile = GetDataFromFileUseCase(fileRepository)
    private val getRequest = GetRequestUseCase(fileRepository)

    private val _selectedColumns = mutableMapOf<String, String?>()
    val selectedColumns: Map<String, String?>
        get() = _selectedColumns.toMap()

    var selectedTable by mutableStateOf("")

    private val _orientation = MutableLiveData("Vertical")
    val orientation: LiveData<String> = _orientation

    private val _request = MutableLiveData("none")
    val request: LiveData<String> =  _request

    private val _dataList = MutableLiveData<List<ContentValues>>()
    val dataList: LiveData<List<ContentValues>> get() = _dataList

    var CREATE_TABLE: String? = null

    private val _selectedColumnsReturn = mutableStateListOf<String>()
    val selectedColumnsReturn: List<String> = _selectedColumnsReturn

    private val _tableNames = MutableLiveData<List<String>>()
    val tableNames: LiveData<List<String>>  get() = _tableNames

    private val _tableField = MutableLiveData <List<String>>()
    val tableField: LiveData<List<String>>  get() = _tableField


    fun rotateScreen() {
        _orientation.value = if (_orientation.value == "Vertical") "Horizontal" else "Vertical"
    }

    fun getInfo(context: Context){
        Log.d("MainViewModel", "_selectedColumns: $_selectedColumns")
    }
    fun writeToBd(context: Context, fileName: String) {

        viewModelScope.launch {
            insertUseCase.invoke(getDataFile.getXmlData(context, fileName))
        }

    }
    fun getListTableField(){
        viewModelScope.launch {
            if (selectedTable != ""){
                _tableField.value = getListTableFieldsUseCase.invoke("$selectedTable")
                _tableField.observeForever { item ->
                    Log.d("List Table" , "$item")
                }
            }
        }


    }
    fun getListOfTableBd(){
        var LIST_TABLE = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT IN ('sqlite_sequence', 'android_metadata')"

        viewModelScope.launch {
            _tableNames.value= getListTable.invoke(LIST_TABLE)

            _tableNames.observeForever { item ->
                Log.d("List Table" , "$item")
            }
        }


    }

    fun getDataFromBd(
        tableName: String,
        selectedColumns: Map<String, String?>,
        listColumnsForReturn: List<String>
    ) {
        viewModelScope.launch {
            val data = getDataseCase.invoke(tableName,selectedColumns, listColumnsForReturn)
            data.observeForever { dataList ->
                _dataList.postValue(dataList)
                Log.d("MainViewModel", "Data list updated: $dataList")
            }
            data.observeForever { dataList ->
                dataList?.let { list ->
                    for (contentValues in list) {
                        Log.d("LiveDataContent", "ContentValues: $contentValues")
                    }
                }
            }
        }
    }

    fun deleteDataFromBd(tableName: String, selectedColumn: String?, selectedValue: String?){
        viewModelScope.launch {
            deleteDataUseCase.invoke(tableName, selectedColumn, selectedValue)
        }
    }

    fun createTable(context: Context, fileName: String){
        createTable.createTable(getJsonRequest(context, fileName))
    }


    fun getJsonRequest(context: Context, fileName: String):String{
        _request.value = getRequest.invoke(context, fileName)
        CREATE_TABLE = _request.value
        Log.d("CREATE_TABLE", CREATE_TABLE.toString())
        return _request.value.toString()
    }

    fun addColumnValue(column: String, value: String) {
        _selectedColumns[column] = null
        _selectedColumns[column] = value
    }

    fun removeColumnValue(column: String) {
        _selectedColumns[column] = null
        _selectedColumns.remove(column)
    }

    fun addColumnReturn(column: String) {
        if (!_selectedColumnsReturn.contains(column)) {
            _selectedColumnsReturn.add(column)
        }
    }

    fun removeColumnReturn(column: String?) {
        column?.let { _selectedColumnsReturn.remove(it) } ?: _selectedColumnsReturn.clear()

    }

    // кнопка в таблице
    fun fetchDataFromDb() {


        if(selectedTable != ""){
                getDataFromBd(selectedTable, _selectedColumns,_selectedColumnsReturn)
        }
    }
}


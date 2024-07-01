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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val selectedColumnsReturn: List<String> get()= _selectedColumnsReturn

    private val _tableNames = MutableLiveData<List<String>>()
    val tableNames: LiveData<List<String>>  get() = _tableNames

    private val _tableField = MutableLiveData <List<String>>()
    val tableField: LiveData<List<String>>  get() = _tableField

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isInsert = MutableStateFlow(false)
    val isInsert: StateFlow<Boolean> = _isInsert.asStateFlow()

    private val _valueLoading = MutableStateFlow(0)
    val valueLoading: StateFlow<Int> = _valueLoading.asStateFlow()

    fun updateIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun updateValueLoading(value: Int) {
        _valueLoading.value = value
    }
    fun setInsert(isLoading: Boolean) {
        _isInsert.value = isLoading
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    fun rotateScreen() {
        _orientation.value = if (_orientation.value == "Vertical") "Horizontal" else "Vertical"
    }

    fun getInfo(context: Context){
        Log.d("MainViewModel", "_selectedColumns: ${_isLoading.value} and ${isLoading.value}")
    }

    fun writeToBd(context: Context, fileName: String, viewModel: MainViewModel) {

        if (selectedTable.isNotEmpty()) {
            viewModel.setInsert(true)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val dataList = getDataFile.getXmlData(context, fileName)
                    insertUseCase.invoke(dataList, selectedTable, viewModel)
                }
            }
        }
    }

    fun getListTableField(){
        viewModelScope.launch {
            if (selectedTable != ""){
                _tableField.value = getListTableFieldsUseCase.invoke("$selectedTable")
                _tableField.observeForever { item ->
                    Log.d("List TableField" , "$item")
                }
            }
        }
    }

    fun getListOfTableBd(){
        viewModelScope.launch {
            _tableNames.value= getListTable.invoke()
            _tableNames.observeForever { item ->
                Log.d("List Table" , "$item")
            }
        }
    }

    fun getDataFromBd(
        tableName: String,
        selectedColumns: Map<String, String?>,
        listColumnsForReturn: List<String>,
        viewModel: MainViewModel
    ) {
        if(listColumnsForReturn.isNotEmpty()){

            viewModelScope.launch {
                val data = getDataseCase.invoke(tableName,selectedColumns, listColumnsForReturn, viewModel)
                data.observeForever { dataList ->
                    _dataList.postValue(dataList)
                    Log.d("MainViewModel", "Data list updated: $dataList")
                }

/*                data.observeForever { dataList ->
                    dataList?.let { list ->
                        for (contentValues in list) {
                            Log.d("LiveDataContent", "ContentValues: $contentValues")
                        }
                    }
                }*/
            }
        }

    }

    fun deleteDataFromBd(tableName: String, selectedColumn: String?, selectedValue: String?){
        viewModelScope.launch {
            deleteDataUseCase.invoke(tableName, selectedColumn, selectedValue)
        }
    }

    fun createTable(context: Context, fileName: String){
        var DELETE_Table = "DROP TABLE NS_MC"
        createTable.createTable(getJsonRequest(context, fileName))
        //createTable.createTable(DELETE_Table)
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
        if(column!= null){
            _selectedColumnsReturn.remove(column)
        } else
        {
            _selectedColumnsReturn.clear()
            getListTableField()
            _tableField.value?.let { fields ->
                _selectedColumnsReturn.addAll(fields)
            }
        }
    }


    fun fetchDataFromDb(viewModel: MainViewModel) {
        if(selectedTable != ""){
                getDataFromBd(selectedTable, _selectedColumns,_selectedColumnsReturn, viewModel)
        }
    }
}


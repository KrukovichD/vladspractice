package com.example.vladspractice.presentation

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.ParserRepository
import com.example.vladspractice.Domain.usecase.CreateTableUseCase
import com.example.vladspractice.Domain.usecase.DeleteDataUseCase
import com.example.vladspractice.Domain.usecase.GetDataFromFileUseCase
import com.example.vladspractice.Domain.usecase.GetDataUseCase
import com.example.vladspractice.Domain.usecase.GetRequestUseCase
import com.example.vladspractice.Domain.usecase.InsertDataUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DatabaseRepository,  private val fileRepository: ParserRepository) : ViewModel()  {


    private val insertUseCase = InsertDataUseCase(dataRepository)
    private val getAllDataseCase = GetDataUseCase(dataRepository)
    private val deleteAllNS_SEMKUseCase = DeleteDataUseCase(dataRepository)
    private val createTable = CreateTableUseCase(dataRepository)

    private val getDataFile = GetDataFromFileUseCase(fileRepository)
    private val getRequest = GetRequestUseCase(fileRepository)


    private val _orientation = MutableLiveData("Vertical")
    val orientation: LiveData<String> = _orientation

    private val _request = MutableLiveData("none")
    val request: LiveData<String> =  _request

    private val _ktaraList = MutableLiveData<List<String>>()
    val ktaraList: LiveData<List<String>> = _ktaraList

    private val _durationTime = MutableLiveData<Double>()
    val durationTime: LiveData<Double> = _durationTime

    private val _dataList = MutableLiveData<List<ContentValues>>()
    val dataList: LiveData<List<ContentValues>> get() = _dataList

    var CREATE_TABLE: String? = null

    private val _selectedColumns = mutableStateListOf<String>()
    val selectedColumns: List<String> = _selectedColumns

    private val _tableName = MutableLiveData<String>("")
    val tableName: LiveData<String> = _tableName

    fun rotateScreen() {
        _orientation.value = if (_orientation.value == "Vertical") "Horizontal" else "Vertical"
    }

    fun writeToBd(context: Context, fileName: String) {

        viewModelScope.launch {
            insertUseCase.invoke(getDataFile.getXmlData(context, fileName))
        }

    }

    fun getDataFromBd(
        tableName: String,
        selectedColumn: String?,
        selectedValue: String?,
        listColumnsForReturn: List<String>) {


        viewModelScope.launch {
            val data = getAllDataseCase.invoke(tableName, selectedColumn, selectedValue, listColumnsForReturn)
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
            deleteAllNS_SEMKUseCase.invoke(tableName, selectedColumn, selectedValue)
        }
    }

    fun createTable(context: Context, fileName: String){

        createTable.createTable(getJsonRequest(context, fileName))
    }

    fun updateDurationTime(newDuration: Double) {
        _durationTime.value = newDuration
    }

    fun getJsonRequest(context: Context, fileName: String):String{
        _request.value = getRequest.invoke(context, fileName)
        CREATE_TABLE = _request.value
        Log.d("CREATE_TABLE", CREATE_TABLE.toString())
        return _request.value.toString()
    }


    fun addColumn(column: String) {
        if (!_selectedColumns.contains(column)) {
            _selectedColumns.add(column)
        }
    }

    fun removeColumn(column: String) {
        _selectedColumns.remove(column)
    }

    fun fetchDataFromDb() {

        val tableName = "NS_SEMK"
        val selectedColumn = null
        val selectedValue = null
        val listColumnsForReturn = emptyList<String>()
        //val listColumnsForReturn = listOf("KMC", "KRK", "KT")
        getDataFromBd(tableName, selectedColumn, selectedValue,_selectedColumns)
    }
}


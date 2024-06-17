package com.example.vladspractice.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.ParserRepository
import com.example.vladspractice.Domain.usecase.DeleteDataUseCase
import com.example.vladspractice.Domain.usecase.GetDataFromFileUseCase
import com.example.vladspractice.Domain.usecase.GetDataUseCase
import com.example.vladspractice.Domain.usecase.InsertDataUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DatabaseRepository,  private val fileRepository: ParserRepository) : ViewModel()  {


    private val insertUseCase = InsertDataUseCase(dataRepository)
    private val getAllNS_SEMKUseCase = GetDataUseCase(dataRepository)
    private val deleteAllNS_SEMKUseCase = DeleteDataUseCase(dataRepository)
    private val getDataFile = GetDataFromFileUseCase(fileRepository)

    private val _orientation = MutableLiveData("Vertical")
    val orientation: LiveData<String> = _orientation

    private val _ktaraList = MutableLiveData<List<String>>()
    val ktaraList: LiveData<List<String>> = _ktaraList

    private val _durationTime = MutableLiveData<Double>()
    val durationTime: LiveData<Double> = _durationTime

    fun rotateScreen() {
        _orientation.value = if (_orientation.value == "Vertical") "Horizontal" else "Vertical"
    }



    fun writeToBd(context: Context, fileName: String) {
        viewModelScope.launch {
                insertUseCase.invoke(getDataFile.getXmlData(context, fileName))
        }
    }

    fun getDataFromBd() {
        getAllNS_SEMKUseCase.invoke().observeForever {
            _ktaraList.value = it.take(5).map { nsSemk -> nsSemk.KTARA ?: "" }
        }
    }

    fun deleteDataFromBd(context: Context){
        viewModelScope.launch {
            deleteAllNS_SEMKUseCase.invoke()
        }
    }


    fun updateDurationTime(newDuration: Double) {
        _durationTime.value = newDuration
    }
}


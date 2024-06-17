package com.example.first_app.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.first_app.Data.Repository.RepositoryParcer
import com.example.first_app.Data.models.Root
import com.example.first_app.Domain.repository.DatabaseRepository
import com.example.first_app.Domain.repository.StorageRepository
import com.example.first_app.Domain.usecase.DeleteData
import com.example.first_app.Domain.usecase.GetData
import com.example.first_app.Domain.usecase.InsertData
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DatabaseRepository) : ViewModel()  {


    private val insertUseCase = InsertData(dataRepository)
    private val getAllNS_SEMKUseCase = GetData(dataRepository)
    private val deleteAllNS_SEMKUseCase = DeleteData(dataRepository)

    private val _orientation = MutableLiveData("Vertical")
    val orientation: LiveData<String> = _orientation

    private val _ktaraList = MutableLiveData<List<String>>()
    val ktaraList: LiveData<List<String>> = _ktaraList

    private val _durationTime = MutableLiveData<Double>()
    val durationTime: LiveData<Double> = _durationTime

    fun rotateScreen() {
        _orientation.value = if (_orientation.value == "Vertical") "Horizontal" else "Vertical"
    }

    fun getDataFromFile(context: Context, fileName: String): Root {
        val repositoryParcer = RepositoryParcer()
        return repositoryParcer.parserXML(context, fileName)
    }


    fun writeToBd(context: Context, fileName: String) {
        viewModelScope.launch {
            val root = getDataFromFile(context, fileName)
            for (nsSemk in root.NS_SEMK) {
                insertUseCase.invoke(nsSemk)
            }
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


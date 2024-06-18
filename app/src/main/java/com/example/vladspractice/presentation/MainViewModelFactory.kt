package com.example.vladspractice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.ParserRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class MainViewModelFactory(
    private val dataRepository: DatabaseRepository,  private val fileRepository: ParserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataRepository, fileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
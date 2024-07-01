package com.example.vladspractice.Domain.usecase

import android.content.ContentValues
import android.content.Context
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.models.Root
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.StorageRepository
import com.example.vladspractice.presentation.MainViewModel

class InsertDataUseCase(private val dataRepository: DatabaseRepository) {
    suspend fun invoke(contentValuesList: List<ContentValues>, TABLE_NAME: String, viewModel: MainViewModel) {
        dataRepository.insertDataToDb(contentValuesList, TABLE_NAME, viewModel)
    }
}
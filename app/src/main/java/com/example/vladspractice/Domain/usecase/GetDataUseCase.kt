package com.example.vladspractice.Domain.usecase

import android.content.ContentValues
import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class GetDataUseCase(private val dataRepository: DatabaseRepository) {
    fun invoke(
        tableName: String,
        selectedColumn: String?,
        selectedValue: String?,
        listColumnsForReturn: List<String>): LiveData<List<ContentValues>> {
        return dataRepository.getDataDb(tableName, selectedColumn, selectedValue, listColumnsForReturn)
    }
}
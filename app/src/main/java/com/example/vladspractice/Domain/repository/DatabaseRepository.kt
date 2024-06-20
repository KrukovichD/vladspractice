package com.example.vladspractice.Domain.repository

import android.content.ContentValues
import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.presentation.MainViewModel

interface DatabaseRepository {
    suspend fun insertDataToDb(contentValuesList: List<ContentValues>, TABLE_NAME: String, viewModel: MainViewModel)
    fun getDataDb(
        tableName: String,
        selectedColumns: Map<String, String?>,
        listColumnsForReturn: List<String>,
        viewModel: MainViewModel
    ): LiveData<List<ContentValues>>

    suspend fun deleteDataDb(tableName: String, selectedColumn: String?, selectedValue: String?)

    suspend fun getListTable():List<String>

    fun createTable(CREATE_TABLE: String)
    suspend fun getListTableFields(tableName: String): List<String>
}
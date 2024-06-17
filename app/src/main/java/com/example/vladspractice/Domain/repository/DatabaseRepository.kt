package com.example.vladspractice.Domain.repository

import android.content.ContentValues
import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK

interface DatabaseRepository {
    suspend fun insertDataToDb(contentValuesList: List<ContentValues>)
    fun getDataDb(
        tableName: String,
        selectedColumn: String?,
        selectedValue: String?,
        listColumnsForReturn: List<String>): LiveData<List<ContentValues>>
    suspend fun deleteDataDb(tableName: String, selectedColumn: String?, selectedValue: String?)
    suspend fun getFirstItem(): NS_SEMK?
    fun createTable(CREATE_TABLE: String)
}
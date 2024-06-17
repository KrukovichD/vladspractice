package com.example.vladspractice.Domain.repository

import android.content.ContentValues
import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK

interface SqlRepository {
    suspend fun insertData(contentValuesList: List<ContentValues>)



    fun getAllData(
        tableName: String,
        selectedColumn: String?,
        selectedValue: String?,
        listColumnsForReturn: List<String>): LiveData<List<ContentValues>>

    suspend fun delete(tableName: String, selectedColumn: String?, selectedValue: String?)

    fun createTable(CREATE_TABLE: String)
}
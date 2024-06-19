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
    fun getListTable(LIST_TABLE: String):List<String>

    fun createTable(CREATE_TABLE: String)
    fun getListTableFields(tableName: String): List<String>
}
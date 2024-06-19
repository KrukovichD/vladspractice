package com.example.vladspractice.Data.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.SqlRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class DatabaseRepositoryImp(private val repository: StorageRepository, private val sqlRepository: SqlRepository): DatabaseRepository {

    override suspend fun insertDataToDb(contentValuesList: List<ContentValues>) {

        return sqlRepository.insertData(contentValuesList)
    }


    override fun getDataDb(
        tableName: String,
        selectedColumn: String?,
        selectedValue: String?,
        listColumnsForReturn: List<String>): LiveData<List<ContentValues>> {

        return sqlRepository.getData(tableName,selectedColumn, selectedValue,listColumnsForReturn)

    }

    override suspend fun deleteDataDb(tableName: String, selectedColumn: String?, selectedValue: String?) {
        return sqlRepository.delete(tableName, selectedColumn, selectedValue)
    }

    override fun getListTable(LIST_TABLE: String): List<String> {
        return sqlRepository.getListTable(LIST_TABLE)
    }


    override fun createTable(CREATE_TABLE: String) {
        sqlRepository.createTable(CREATE_TABLE)
    }

    override fun getListTableFields(tableName: String): List<String> {
        return sqlRepository.getListTableFields(tableName)
    }
}
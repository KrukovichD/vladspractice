package com.example.vladspractice.Data.Repository

import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.SqlRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class DatabaseRepositoryImp(private val repository: StorageRepository, private val sqlRepository: SqlRepository): DatabaseRepository {

    override suspend fun insertDataToDb(ns_semk:  List<NS_SEMK>) {
        return repository.insertData(ns_semk)
    }

    override fun getAllDataDb(): LiveData<List<NS_SEMK>> {
        return repository.getAllData()
    }

    override suspend fun deleteDataDb() {
        return repository.deleteAllData()
    }

    override suspend fun getFirstItem(): NS_SEMK? {
        return repository.getFirstItem()
    }
}
package com.example.first_app.Data.Repository

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.DatabaseRepository
import com.example.first_app.Domain.repository.StorageRepository

class DatabaseRepositoryImp(private val repository: StorageRepository): DatabaseRepository {

    override suspend fun insertDataToDb(ns_semk: NS_SEMK) {
        return repository.insertData(ns_semk)
    }

    override fun getAllDataDb(): LiveData<List<NS_SEMK>> {
        return repository.getAllData()
    }

    override suspend fun deleteDataDb() {
        return repository.deleteAllData()
    }
}
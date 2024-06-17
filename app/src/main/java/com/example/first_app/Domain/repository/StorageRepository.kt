package com.example.first_app.Domain.repository

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK

interface StorageRepository {
    suspend fun insertData(ns_semk: NS_SEMK)
    fun getAllData(): LiveData<List<NS_SEMK>>
    suspend fun deleteAllData()
}
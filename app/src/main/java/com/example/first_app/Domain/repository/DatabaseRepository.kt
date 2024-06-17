package com.example.first_app.Domain.repository

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK

interface DatabaseRepository {
    suspend fun insertDataToDb(ns_semk: NS_SEMK)
    fun getAllDataDb(): LiveData<List<NS_SEMK>>
    suspend fun deleteDataDb()
}
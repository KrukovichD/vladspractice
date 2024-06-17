package com.example.vladspractice.Domain.repository

import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK

interface SqlRepository {
    suspend fun insertData(ns_semk:  List<NS_SEMK>)
    fun getAllData(): LiveData<List<NS_SEMK>>
    suspend fun deleteAllData()
}
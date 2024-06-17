package com.example.vladspractice.Domain.repository

import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK

interface DatabaseRepository {
    suspend fun insertDataToDb(ns_semk:  List<NS_SEMK>)
    fun getAllDataDb(): LiveData<List<NS_SEMK>>
    suspend fun deleteDataDb()
    suspend fun getFirstItem(): NS_SEMK?
}
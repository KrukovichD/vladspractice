package com.example.first_app.Domain.repository

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK

interface NS_SEMKRepository {
    suspend fun insertNS_SEMK(nsSemk: NS_SEMK)
    fun getAllNS_SEMK(): LiveData<List<NS_SEMK>>
    suspend fun deleteNS_SEMK()
}
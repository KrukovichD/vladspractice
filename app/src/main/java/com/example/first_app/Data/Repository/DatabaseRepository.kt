package com.example.first_app.Data.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.first_app.Data.DB.AppDatabase
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.NS_SEMKRepository

class DatabaseRepository (context: Context) : NS_SEMKRepository {
    private val db = AppDatabase.getDatabase(context)
    private val nsSemkDao = db.nsSemkDao()


    override suspend fun insertNS_SEMK(ns_semk: NS_SEMK) {
        nsSemkDao.insertNS_SEMK(ns_semk)
    }

    override fun getAllNS_SEMK(): LiveData<List<NS_SEMK>> {
        return nsSemkDao.getAllNS_SEMK()
    }

    override suspend fun deleteNS_SEMK() {
        nsSemkDao.deleteAllNS_SEMK()
    }
}
package com.example.first_app.Data.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.first_app.Data.DB.AppDatabase
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.StorageRepository

class StorageRepositoryImp (context: Context): StorageRepository {

    private val db = AppDatabase.getDatabase(context)
    private val nsSemkDao = db.nsSemkDao()

    override suspend fun insertData(ns_semk: NS_SEMK) {
        nsSemkDao.insertNS_SEMK(ns_semk)
    }

    override fun getAllData(): LiveData<List<NS_SEMK>> {
        return nsSemkDao.getAllNS_SEMK()
    }

    override suspend fun deleteAllData() {
        nsSemkDao.deleteAllNS_SEMK()
    }
}
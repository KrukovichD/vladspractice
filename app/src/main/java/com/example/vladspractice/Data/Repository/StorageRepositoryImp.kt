package com.example.vladspractice.Data.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.vladspractice.Data.DB.Room.AppDatabase
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.StorageRepository

class StorageRepositoryImp (context: Context): StorageRepository {

    private val db = AppDatabase.getDatabase(context)
    private val nsSemkDao = db.nsSemkDao()

    override suspend fun insertData(ns_semk:  List<NS_SEMK>) {
        nsSemkDao.insertAllNS_SEMK(ns_semk)
    }

    override fun getAllData(): LiveData<List<NS_SEMK>> {
        return nsSemkDao.getAllNS_SEMK()
    }

    override suspend fun deleteAllData() {
        nsSemkDao.deleteAllNS_SEMK()
    }

    override suspend fun getFirstItem(): NS_SEMK? {
        return nsSemkDao.getFirstNS_SEMK()
    }
}
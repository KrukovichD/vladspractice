package com.example.vladspractice.Data.DB.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.first_app.Data.models.garbage.MyNameSQLite
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.SqlRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SqlRepositoryImp(val context: Context): SqlRepository {

    private val dbHelper = SqlHelper(context)
    var db: SQLiteDatabase? = null
    private var openCounter = 0


    override suspend fun insertData(ns_semkList: List<NS_SEMK>) {
        openDB()

        CoroutineScope(Dispatchers.IO).launch {
            val jobs = ns_semkList.map { nsSemk ->
                launch {
                    val values = ContentValues().apply {
                        put(MyNameSQLite.COLUMN_KMC, nsSemk.KMC)
                        put(MyNameSQLite.COLUMN_KRK, nsSemk.KRK)
                        put(MyNameSQLite.COLUMN_KT, nsSemk.KT)
                        put(MyNameSQLite.COLUMN_EMK, nsSemk.EMK)
                        put(MyNameSQLite.COLUMN_PR, nsSemk.PR)
                        put(MyNameSQLite.COLUMN_KTARA, nsSemk.KTARA)
                        put(MyNameSQLite.COLUMN_GTIN, nsSemk.GTIN)
                        put(MyNameSQLite.COLUMN_EMKPOD, nsSemk.EMKPOD)
                    }
                    Log.d("XMLparser", "Inserting into database: $nsSemk")

                    db?.beginTransaction()
                    try {
                        db?.insert(MyNameSQLite.TABLE_NAME, null, values)
                        db?.setTransactionSuccessful()
                    } finally {
                        db?.endTransaction()
                    }
                }
            }
            jobs.forEach { it.join() }
            closeDb()
        }
    }

    override fun getAllData(): LiveData<List<NS_SEMK>> {
        val liveData = MutableLiveData<List<NS_SEMK>>()
        openDB()

        CoroutineScope(Dispatchers.IO).launch {
            val dataList = ArrayList<NS_SEMK>()
            val cursor = dbHelper.readableDatabase.query(
                MyNameSQLite.TABLE_NAME, null, null, null, null, null, null
            )

            while (cursor.moveToNext()) {
                val nsSemk = NS_SEMK(
                    KMC = cursor.getColumnIndex(MyNameSQLite.COLUMN_KMC).let { if (it != -1) cursor.getString(it) else null },
                    KRK = cursor.getColumnIndex(MyNameSQLite.COLUMN_KRK).let { if (it != -1) cursor.getString(it) else null },
                    KT = cursor.getColumnIndex(MyNameSQLite.COLUMN_KT).let { if (it != -1) cursor.getString(it) else null },
                    EMK = cursor.getColumnIndex(MyNameSQLite.COLUMN_EMK).let { if (it != -1) cursor.getString(it) else "" },
                    PR = cursor.getColumnIndex(MyNameSQLite.COLUMN_PR).let { if (it != -1) cursor.getString(it) else null },
                    KTARA = cursor.getColumnIndex(MyNameSQLite.COLUMN_KTARA).let { if (it != -1) cursor.getString(it) else "" },
                    GTIN = cursor.getColumnIndex(MyNameSQLite.COLUMN_GTIN).let { if (it != -1) cursor.getString(it) else null },
                    EMKPOD = cursor.getColumnIndex(MyNameSQLite.COLUMN_EMKPOD).let { if (it != -1) cursor.getString(it) else null }
                )
                dataList.add(nsSemk)
            }
            cursor.close()
            closeDb()

            withContext(Dispatchers.Main) {
                liveData.value = dataList
            }
        }
        return liveData
    }


    override suspend fun deleteAllData() {
        openDB()
        CoroutineScope(Dispatchers.IO).launch {
            db?.beginTransaction()
            try {
                db?.execSQL("DELETE FROM ${MyNameSQLite.TABLE_NAME}")
                db?.setTransactionSuccessful()
            } catch (e: Exception) {
                Log.e("DatabaseError", "Error while trying to delete all data", e)
            } finally {
                db?.endTransaction()
                closeDb()
            }
        }
    }

    fun openDB(){

        db = dbHelper.writableDatabase
        openCounter++
    }
    fun closeDb(){
        openCounter--
        if (openCounter==0){
            dbHelper.close()
        }
    }
}
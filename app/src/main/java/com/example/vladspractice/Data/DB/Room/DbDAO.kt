package com.example.vladspractice.Data.DB.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vladspractice.Domain.models.NS_SEMK

@Dao
interface DbDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNS_SEMK(ns_semk:  List<NS_SEMK>)

    @Query("SELECT * FROM ns_semk")
    fun getAllNS_SEMK(): LiveData<List<NS_SEMK>>

    @Query("DELETE FROM ns_semk")
    suspend fun deleteAllNS_SEMK()

    @Query("SELECT * FROM ns_semk LIMIT 1")
    fun getFirstNS_SEMK(): NS_SEMK?

}
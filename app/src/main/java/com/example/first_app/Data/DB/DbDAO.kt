package com.example.first_app.Data.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.first_app.Data.models.NS_SEMK

@Dao
interface DbDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNS_SEMK(ns_semk: NS_SEMK)

    @Query("SELECT * FROM ns_semk")
    fun getAllNS_SEMK(): LiveData<List<NS_SEMK>>

    @Query("DELETE FROM ns_semk")
    suspend fun deleteAllNS_SEMK()


    @Query("SELECT * FROM ns_semk")
    fun getAll(): LiveData<List<NS_SEMK>>
}
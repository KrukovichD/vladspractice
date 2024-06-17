package com.example.vladspractice.Data.DB.Room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.vladspractice.Domain.models.NS_SEMK

@Database(entities = [NS_SEMK::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nsSemkDao(): DbDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

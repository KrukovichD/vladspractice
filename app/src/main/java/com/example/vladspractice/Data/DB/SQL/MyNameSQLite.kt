package com.example.first_app.Data.models.garbage

import android.provider.BaseColumns

object MyNameSQLite{
    const val TABLE_NAME = "NS_SEMK"
    const val COLUMN_KMC = "KMC"
    const val COLUMN_KRK = "KRK"
    const val COLUMN_KT = "KT"
    const val COLUMN_EMK = "EMK"
    const val COLUMN_PR = "PR"
    const val COLUMN_KTARA = "KTARA"
    const val COLUMN_GTIN = "GTIN"
    const val COLUMN_EMKPOD = "EMKPOD"


    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "app_database.db"

    const val CREATE_TABLE = " CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_KMC TEXT, $COLUMN_KRK TEXT," +
            "$COLUMN_KT TEXT, $COLUMN_EMK TEXT, $COLUMN_PR TEXT, $COLUMN_KTARA TEXT, $COLUMN_GTIN TEXT, $COLUMN_EMKPOD TEXT )"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
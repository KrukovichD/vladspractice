package com.example.vladspractice.Domain.repository

import android.content.ContentValues
import android.content.Context
import com.example.vladspractice.Domain.models.DataSQL
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.models.Root

interface ParserRepository {
    fun parserXML(context: Context, fileName: String):  List<ContentValues>
    fun readParserFile(context: Context,fileName: String ): String?
    fun parseJSON(context: Context, fileName: String):  List<NS_SEMK>?
    fun readRequest(context: Context, fileName: String): DataSQL?
}
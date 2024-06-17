package com.example.vladspractice.Domain.repository

import android.content.Context
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.models.Root

interface ParserRepository {
    fun parserXML(context: Context, fileName: String):  List<NS_SEMK>
    fun readParserFile(context: Context,fileName: String ): String?
    fun parseJSON(context: Context, fileName: String):  List<NS_SEMK>?
}
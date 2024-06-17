package com.example.vladspractice.Data.Repository

import android.content.ContentValues
import android.content.Context
import com.example.vladspractice.Data.util.Helper
import com.example.vladspractice.Domain.models.DataSQL
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.ParserRepository

class ParserRepositoryImp(): ParserRepository {

    override fun parserXML(context: Context, fileName: String):  List<ContentValues>  {
        var helper = Helper()
        return helper.parserXML(context, fileName)
    }

    override fun readParserFile(context: Context, fileName: String): String? {
        var helper = Helper()
        return helper.readFile(context, fileName)
    }

    override fun parseJSON(context: Context, fileName: String):  List<NS_SEMK>? {
        var helper = Helper()
        return helper.parseJSON(readParserFile(context, fileName)!!)
    }

    override fun readRequest(context: Context, fileName: String): DataSQL? {
        val helper = Helper()
        return helper.parseJsonRequest(readParserFile(context, fileName)!!)
    }

}
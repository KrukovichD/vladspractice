package com.example.vladspractice.Data.Repository

import android.content.Context
import com.example.vladspractice.Data.util.Helper
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.ParserRepository

class ParserRepositoryImp(): ParserRepository {

    override fun parserXML(context: Context, fileName: String):  List<NS_SEMK> {
        var helper = Helper()
        return helper.parserXML(readParserFile(context, fileName)!!)
    }

    override fun readParserFile(context: Context, fileName: String): String? {
        var helper = Helper()
        return helper.readFile(context, fileName)
    }

    override fun parseJSON(context: Context, fileName: String):  List<NS_SEMK>? {
        var helper = Helper()
        return helper.parseJSON(readParserFile(context, fileName)!!)
    }

}
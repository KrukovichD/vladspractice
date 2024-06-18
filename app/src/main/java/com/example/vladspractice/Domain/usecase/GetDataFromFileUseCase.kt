package com.example.vladspractice.Domain.usecase

import android.content.Context
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.models.Root
import com.example.vladspractice.Domain.repository.ParserRepository

class GetDataFromFileUseCase(private val repository: ParserRepository) {
    fun getXmlData(context: Context, fileName: String):  List<NS_SEMK> {
        return repository.parserXML(context, fileName)
    }
    fun getJsonData(context: Context, fileName: String):  List<NS_SEMK> {
        return repository.parserXML(context, fileName)
    }
}
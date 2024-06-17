package com.example.vladspractice.Domain.usecase

import android.content.Context
import com.example.vladspractice.Domain.repository.ParserRepository

class GetRequestUseCase (private val repository: ParserRepository){
    fun invoke(context: Context, fileName: String): String {
        return repository.readRequest(context, fileName)?.DATA ?: "error"
    }

}
package com.example.vladspractice.Domain.usecase

import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.DatabaseRepository

class GetFirstItemUseCase(private val dataRepository: DatabaseRepository) {
    suspend fun invoke(): NS_SEMK?{
        return dataRepository.getFirstItem()
    }
}
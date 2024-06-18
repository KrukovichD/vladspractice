package com.example.vladspractice.Domain.usecase

import androidx.lifecycle.LiveData
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class GetDataUseCase(private val dataRepository: DatabaseRepository) {
    fun invoke(): LiveData<List<NS_SEMK>> {
        return dataRepository.getAllDataDb()
    }
}
package com.example.vladspractice.Domain.usecase

import android.content.Context
import com.example.vladspractice.Domain.models.NS_SEMK
import com.example.vladspractice.Domain.models.Root
import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.StorageRepository

class InsertDataUseCase(private val dataRepository: DatabaseRepository) {
    suspend fun invoke(nsSemk: List<NS_SEMK>) {
        dataRepository.insertDataToDb(nsSemk)
    }


}
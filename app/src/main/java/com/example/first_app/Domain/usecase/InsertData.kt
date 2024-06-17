package com.example.first_app.Domain.usecase

import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.DatabaseRepository
import com.example.first_app.Domain.repository.StorageRepository

class InsertData(private val dataRepository: DatabaseRepository) {
    suspend fun invoke(nsSemk: NS_SEMK) {
        dataRepository.insertDataToDb(nsSemk)
    }


}
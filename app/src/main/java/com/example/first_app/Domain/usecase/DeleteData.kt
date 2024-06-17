package com.example.first_app.Domain.usecase

import com.example.first_app.Domain.repository.DatabaseRepository
import com.example.first_app.Domain.repository.StorageRepository

class DeleteData(private val dataRepository: DatabaseRepository) {
    suspend fun invoke() {
        dataRepository.deleteDataDb()
    }
}
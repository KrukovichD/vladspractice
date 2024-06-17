package com.example.vladspractice.Domain.usecase

import com.example.vladspractice.Domain.repository.DatabaseRepository

class DeleteDataUseCase(private val dataRepository: DatabaseRepository) {
    suspend fun invoke() {
        dataRepository.deleteDataDb()
    }
}
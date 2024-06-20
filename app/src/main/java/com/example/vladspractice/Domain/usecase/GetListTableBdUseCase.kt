package com.example.vladspractice.Domain.usecase

import com.example.vladspractice.Domain.repository.DatabaseRepository

class GetListTableBdUseCase(private val dataRepository: DatabaseRepository) {
    suspend fun invoke(): List<String>{
        return dataRepository.getListTable()
    }
}
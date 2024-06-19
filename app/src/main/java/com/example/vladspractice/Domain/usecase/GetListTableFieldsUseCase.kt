package com.example.vladspractice.Domain.usecase

import com.example.vladspractice.Domain.repository.DatabaseRepository

class GetListTableFieldsUseCase(private val dataRepository: DatabaseRepository) {
    fun invoke(tableName: String): List<String>{
        return dataRepository.getListTableFields(tableName)
    }
}
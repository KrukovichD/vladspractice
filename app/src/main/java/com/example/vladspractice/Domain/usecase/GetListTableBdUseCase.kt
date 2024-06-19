package com.example.vladspractice.Domain.usecase

import com.example.vladspractice.Domain.repository.DatabaseRepository

class GetListTableBdUseCase(private val dataRepository: DatabaseRepository) {
    fun invoke(LIST_TABLE: String): List<String>{
        return dataRepository.getListTable(LIST_TABLE)
    }
}
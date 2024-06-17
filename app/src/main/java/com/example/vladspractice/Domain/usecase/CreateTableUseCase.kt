package com.example.vladspractice.Domain.usecase

import com.example.vladspractice.Domain.repository.DatabaseRepository
import com.example.vladspractice.Domain.repository.SqlRepository

class CreateTableUseCase(private val dataRepository: DatabaseRepository) {
    fun createTable(CREATE_TABLE: String){
        dataRepository.createTable(CREATE_TABLE)
    }

}
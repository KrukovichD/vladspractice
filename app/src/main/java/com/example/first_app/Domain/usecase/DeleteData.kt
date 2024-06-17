package com.example.first_app.Domain.usecase

import com.example.first_app.Domain.repository.NS_SEMKRepository

class DeleteData(private val repository: NS_SEMKRepository) {
    suspend fun invoke() {
        repository.deleteNS_SEMK()
    }
}
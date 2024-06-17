package com.example.first_app.Domain.usecase

import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.NS_SEMKRepository

class InsertData(private val repository: NS_SEMKRepository) {
    suspend fun invoke(nsSemk: NS_SEMK) {
        repository.insertNS_SEMK(nsSemk)
    }


}
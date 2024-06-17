package com.example.first_app.Domain.usecase

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.DatabaseRepository
import com.example.first_app.Domain.repository.StorageRepository

class GetData(private val dataRepository: DatabaseRepository) {
    fun invoke(): LiveData<List<NS_SEMK>> {
        return dataRepository.getAllDataDb()
    }
}
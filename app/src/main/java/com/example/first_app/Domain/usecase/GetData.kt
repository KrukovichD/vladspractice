package com.example.first_app.Domain.usecase

import androidx.lifecycle.LiveData
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Domain.repository.NS_SEMKRepository

class GetData(private val repository: NS_SEMKRepository) {
    fun invoke(): LiveData<List<NS_SEMK>> {
        return repository.getAllNS_SEMK()
    }
}
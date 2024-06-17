package com.example.vladspractice.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.vladspractice.Data.DB.SQL.SqlRepositoryImp
import com.example.vladspractice.Data.Repository.DatabaseRepositoryImp
import com.example.vladspractice.Data.Repository.ParserRepositoryImp
import com.example.vladspractice.Data.Repository.StorageRepositoryImp
import com.example.vladspractice.Domain.repository.SqlRepository
import com.example.vladspractice.ui.theme.First_appTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fileRepository = ParserRepositoryImp()
        val repositorySQL = SqlRepositoryImp(context = applicationContext)
        val repositoryStorage = StorageRepositoryImp(context = applicationContext)
        val dataRepository = DatabaseRepositoryImp(repositoryStorage, repositorySQL)
        val factory = MainViewModelFactory(dataRepository, fileRepository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        setContent {
            First_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        activity = this,
                    )
                }
            }
        }
    }

}


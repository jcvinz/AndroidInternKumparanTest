package com.calvin.internkumparantest.di

import DataRepository
import com.calvin.internkumparantest.data.ApiConfig

object Injection {

    fun provideDataRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository.getInstance(apiService)
    }
}
package ru.nikitazar.data.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nikitazar.data.api.ApiService
import ru.nikitazar.data.api.loggingInterceptor
import ru.nikitazar.data.api.okhttp
import ru.nikitazar.data.api.retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return retrofit(okhttp(loggingInterceptor()))
            .create(ApiService::class.java)
    }
}
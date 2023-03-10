package ru.nikitazar.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nikitazar.data.repository.ImageRepositoryImpl
import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDrugsRepository(imp: ImageRepositoryImpl): ImageRepository
}
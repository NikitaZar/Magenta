package ru.nikitazar.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nikitazar.data.repository.FavoriteRepositoryImpl
import ru.nikitazar.domain.repository.FavoriteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(imp: FavoriteRepositoryImpl): FavoriteRepository
}
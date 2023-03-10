package ru.nikitazar.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nikitazar.data.db.AppDb
import ru.nikitazar.data.db.dao.ImageDao

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideImageDao(db: AppDb): ImageDao = db.imageDao()
}
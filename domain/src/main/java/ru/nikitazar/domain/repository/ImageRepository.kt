package ru.nikitazar.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.nikitazar.domain.models.Image

interface ImageRepository {
    suspend fun getPage(): Flow<PagingData<Image>>
    suspend fun getFavorite(): Flow<List<Image>>
    suspend fun updateFavoriteById(id: Int, isFavorite: Boolean)
}
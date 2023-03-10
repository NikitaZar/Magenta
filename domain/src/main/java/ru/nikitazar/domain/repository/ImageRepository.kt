package ru.nikitazar.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.nikitazar.domain.models.Image

interface ImageRepository {
    suspend fun getPage(): Flow<PagingData<Image>>
    suspend fun getFavorite(): List<Image>
    suspend fun like(id: Int)
    suspend fun dislike(id: Int)
}
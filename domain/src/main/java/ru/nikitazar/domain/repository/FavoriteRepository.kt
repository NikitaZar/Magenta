package ru.nikitazar.domain.repository

interface FavoriteRepository {
    suspend fun removeById(id: Int)
    suspend fun insertById(id: Int)
}
package ru.nikitazar.data.repository

import ru.nikitazar.data.db.dao.FavoriteDao
import ru.nikitazar.data.db.entities.FavoriteEntity
import ru.nikitazar.domain.repository.FavoriteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
) : FavoriteRepository {
    override suspend fun removeById(id: Int) = dao.removeById(id)
    override suspend fun insertById(id: Int) = dao.insert(FavoriteEntity(id = id))
}
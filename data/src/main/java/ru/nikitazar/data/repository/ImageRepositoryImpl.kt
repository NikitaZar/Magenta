package ru.nikitazar.data.repository

import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.nikitazar.data.db.dao.ImageDao
import ru.nikitazar.data.db.entities.ImageEntity
import ru.nikitazar.domain.models.Image
import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Inject
import javax.inject.Singleton

const val PAGE_SIZE = 5

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val dao: ImageDao,
    private val mediator: ImageRepositoryMediator
) : ImageRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPage(): Flow<PagingData<Image>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = mediator,
        pagingSourceFactory = { dao.getAll() }
    ).flow
        .map { pagingData -> pagingData.map(ImageEntity::toDto) }
        .flowOn(Dispatchers.IO)

    override suspend fun getFavorite() = dao.getFavorite()
        .map { favorite -> favorite.map { it.toDto() } }
        .asFlow()
        .flowOn(Dispatchers.IO)

    override suspend fun updateFavoriteById(id: Int, isFavorite: Boolean) = dao.updateFavoriteById(id, isFavorite)
}
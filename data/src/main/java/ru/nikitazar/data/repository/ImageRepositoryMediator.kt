package ru.nikitazar.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.nikitazar.data.api.ApiService
import ru.nikitazar.data.db.dao.ImageDao
import ru.nikitazar.data.db.dao.ImageRemoteKeyDao
import ru.nikitazar.data.db.entities.ImageEntity
import ru.nikitazar.data.db.entities.ImageRemoteKeyEntity
import ru.nikitazar.data.db.entities.toEntity
import ru.nikitazar.data.errors.NetworkError
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryMediator @Inject constructor(
    private val api: ApiService,
    private val imageDao: ImageDao,
    private val remoteKeyDao: ImageRemoteKeyDao,
) : RemoteMediator<Int, ImageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {

        val limit = state.config.pageSize
        val lastIndex = remoteKeyDao.max() ?: 0
        val pageIndex = getPageIndex(
            loadType = loadType,
            lastIndex = lastIndex
        ) ?: return MediatorResult.Success(true)

        return try {
            val list = loadPage(pageIndex, limit)
            when (loadType) {
                LoadType.REFRESH -> {
                    imageDao.refresh(list)
                    if (remoteKeyDao.isEmpty()) {
                        remoteKeyDao.insert(
                            ImageRemoteKeyEntity(
                                type = ImageRemoteKeyEntity.KeyType.AFTER,
                                index = pageIndex
                            )
                        )
                    }
                }
                else -> {
                    imageDao.insert(list)
                    remoteKeyDao.insert(
                        ImageRemoteKeyEntity(
                            type = ImageRemoteKeyEntity.KeyType.AFTER,
                            index = pageIndex
                        )
                    )
                }
            }

            MediatorResult.Success(list.size < limit)
        } catch (e: Exception) {
            Log.e("Mediator error", e.message.toString())
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType, lastIndex: Int): Int? =
        when (loadType) {
            LoadType.REFRESH -> lastIndex
            LoadType.PREPEND -> null
            LoadType.APPEND -> lastIndex + 1
        }

    private suspend fun loadPage(
        page: Int,
        limit: Int
    ): List<ImageEntity> {
        val response = api.getPage(page, limit)
        val body = response.body() ?: throw NetworkError
        return body.map { it.toDomain() }.toEntity()
    }

}
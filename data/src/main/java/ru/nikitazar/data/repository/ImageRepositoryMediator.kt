package ru.nikitazar.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.nikitazar.data.api.ApiService
import ru.nikitazar.data.db.dao.ImageDao
import ru.nikitazar.data.db.entities.ImageEntity
import ru.nikitazar.data.db.entities.toEntity
import ru.nikitazar.data.errors.ApiError
import ru.nikitazar.data.errors.NetworkError
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryMediator @Inject constructor(
    private val api: ApiService,
    private val dao: ImageDao,
) : RemoteMediator<Int, ImageEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(true)
        val limit = state.config.pageSize
        val page = pageIndex * limit

        return try {
            val list = loadPage(page, limit)
            if (loadType == LoadType.REFRESH) {
                dao.refresh(list)
            } else {
                dao.insert(list)
            }

            MediatorResult.Success(list.size < limit)
        } catch (e: Exception) {
            Log.e("Mediator error", e.message.toString())
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? =
        when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> null
            LoadType.APPEND -> ++pageIndex
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
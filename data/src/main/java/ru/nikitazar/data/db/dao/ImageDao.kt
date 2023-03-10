package ru.nikitazar.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.nikitazar.data.db.entities.ImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<ImageEntity>)

    @Query("SELECT * FROM ImageEntity")
    fun getAll(): PagingSource<Int, ImageEntity>

    @Transaction
    suspend fun refresh(list: List<ImageEntity>) {
        removeAll()
        insert(list)
    }

    @Query("DELETE FROM ImageEntity")
    suspend fun removeAll()

    @Query("SELECT * FROM ImageEntity WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavorite(): List<ImageEntity>

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getById(id: Int): ImageEntity?
}
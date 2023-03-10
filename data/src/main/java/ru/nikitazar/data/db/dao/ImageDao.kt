package ru.nikitazar.data.db.dao

import androidx.lifecycle.LiveData
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

    @Query("DELETE FROM ImageEntity")
    suspend fun removeAll()

    @Query("SELECT * FROM ImageEntity WHERE isFavorite = 1")
    fun getFavorite(): LiveData<List<ImageEntity>>

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getById(id: Int): ImageEntity?

    @Query("UPDATE ImageEntity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteById(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM ImageEntity")
    fun getList(): List<ImageEntity>
}
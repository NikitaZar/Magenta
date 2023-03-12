package ru.nikitazar.data.db.dao

import androidx.room.*
import ru.nikitazar.data.db.entities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAll(): List<FavoriteEntity>

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    suspend fun removeById(id: Int)

}
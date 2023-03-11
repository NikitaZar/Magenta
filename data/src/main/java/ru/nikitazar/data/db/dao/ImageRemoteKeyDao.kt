package ru.nikitazar.data.db.dao

import androidx.room.*
import ru.nikitazar.data.db.entities.ImageRemoteKeyEntity

@Dao
interface ImageRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: ImageRemoteKeyEntity)

    @Query("SELECT MAX(`index`) FROM ImageRemoteKeyEntity")
    suspend fun max(): Int?

    @Query("SELECT COUNT(*) == 0 FROM ImageRemoteKeyEntity")
    suspend fun isEmpty(): Boolean
}
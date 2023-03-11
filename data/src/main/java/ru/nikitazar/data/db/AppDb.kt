package ru.nikitazar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikitazar.data.db.dao.ImageDao
import ru.nikitazar.data.db.dao.ImageRemoteKeyDao
import ru.nikitazar.data.db.entities.ImageEntity
import ru.nikitazar.data.db.entities.ImageRemoteKeyEntity

@Database(
    entities = [
        ImageEntity::class,
        ImageRemoteKeyEntity::class
    ],
    version = 2
)

abstract class AppDb : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun remoteKeyDao(): ImageRemoteKeyDao
}
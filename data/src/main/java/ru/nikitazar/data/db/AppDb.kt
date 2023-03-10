package ru.nikitazar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikitazar.data.db.dao.ImageDao
import ru.nikitazar.data.db.entities.ImageEntity

@Database(
    entities = [
        ImageEntity::class,
    ],
    version = 1
)

abstract class AppDb : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
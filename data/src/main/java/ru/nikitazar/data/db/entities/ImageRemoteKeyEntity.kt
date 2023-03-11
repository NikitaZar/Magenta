package ru.nikitazar.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageRemoteKeyEntity(
    @PrimaryKey
    val type: KeyType,
    val index: Int
) {

    enum class KeyType {
        AFTER, BEFORE
    }
}
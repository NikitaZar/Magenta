package ru.nikitazar.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nikitazar.domain.models.Image

@Entity
data class ImageEntity(
    @PrimaryKey
    val id: Int = -1,
    val author: String = "",
    val downloadUrl: String = "",
    val isFavorite: Boolean = false
) {
    fun toDto() = Image(
        id = id,
        author = author,
        downloadUrl = downloadUrl,
        isFavorite = isFavorite,
    )

    companion object {
        fun fromDto(dto: Image) = ImageEntity(
            id = dto.id,
            author = dto.author,
            downloadUrl = dto.downloadUrl,
            isFavorite = dto.isFavorite,
        )
    }
}

fun List<ImageEntity>.toDto() = map { it.toDto() }

fun List<Image>.toEntity() = map { ImageEntity.fromDto(it) }
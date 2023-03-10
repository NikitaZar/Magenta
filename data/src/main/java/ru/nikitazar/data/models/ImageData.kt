package ru.nikitazar.data.models

import com.google.gson.annotations.SerializedName
import ru.nikitazar.domain.models.Image

data class ImageData(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("author") val author: String = "",
    @SerializedName("with") val with: Int = 0,
    @SerializedName("height") val height: Int = 0,
    @SerializedName("url") val url: String = "",
    @SerializedName("download_url") val downloadUrl: String = "",
    @Transient val isFavorite: Boolean = false
) {
    fun toDomain() = Image(
        id = id,
        author = author,
        downloadUrl = downloadUrl,
        isFavorite = isFavorite
    )
}
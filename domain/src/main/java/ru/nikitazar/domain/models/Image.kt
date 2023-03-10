package ru.nikitazar.domain.models

data class Image(
    val id: Int = -1,
    val author: String = "",
    val downloadUrl: String = "",
    val isFavorite: Boolean = false
)

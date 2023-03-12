package ru.nikitazar.domain.useCases

import ru.nikitazar.domain.repository.FavoriteRepository
import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Inject

class DislikeUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend fun execute(id: Int) {
        imageRepository.updateFavoriteById(id, false)
        favoriteRepository.removeById(id)
    }
}
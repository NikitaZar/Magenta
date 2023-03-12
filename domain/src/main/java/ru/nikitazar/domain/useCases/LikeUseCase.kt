package ru.nikitazar.domain.useCases

import ru.nikitazar.domain.repository.FavoriteRepository
import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Inject

class LikeUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend fun execute(id: Int) {
        imageRepository.updateFavoriteById(id, true)
        favoriteRepository.insertById(id)
    }
}
package ru.nikitazar.domain.useCases

import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Inject

class DislikeUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend fun execute(id: Int) = repository.dislike(id)
}
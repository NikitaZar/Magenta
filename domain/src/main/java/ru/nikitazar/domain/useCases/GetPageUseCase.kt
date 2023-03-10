package ru.nikitazar.domain.useCases

import ru.nikitazar.domain.repository.ImageRepository
import javax.inject.Inject

class GetPageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend fun execute() = repository.getPage()
}
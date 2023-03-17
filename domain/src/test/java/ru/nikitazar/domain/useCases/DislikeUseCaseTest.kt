package ru.nikitazar.domain.useCases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.nikitazar.domain.repository.FavoriteRepository
import ru.nikitazar.domain.repository.ImageRepository

@OptIn(ExperimentalCoroutinesApi::class)
class DislikeUseCaseTest {
    private val fakeImageRepository = mock<ImageRepository>()
    private val fakeFavoriteRepository = mock<FavoriteRepository>()

    private val useCase = DislikeUseCase(
        imageRepository = fakeImageRepository,
        favoriteRepository = fakeFavoriteRepository
    )
    private val id = 0

    @Test
    fun `should call updateFavoriteById from ImageRepository`(): Unit = runTest {
        val isFavorite = false
        useCase.execute(id)
        verify(fakeImageRepository, times(1)).updateFavoriteById(id, isFavorite)
    }

    @Test
    fun `should call removeById from FavoriteRepository`(): Unit = runTest {
        useCase.execute(id)
        verify(fakeFavoriteRepository, times(1)).removeById(id)
    }
}
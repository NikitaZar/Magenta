package ru.nikitazar.domain.useCases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.nikitazar.domain.models.Image
import ru.nikitazar.domain.repository.ImageRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavoriteUseCaseTest {
    private val fakeImageRepository = mock<ImageRepository>()
    private val useCase = GetFavoriteUseCase(fakeImageRepository)

    @Test
    fun `should get favorite flow`(): Unit = runTest {

        val expected = listOf(
            Image(id = 0, isFavorite = true),
            Image(id = 1, isFavorite = true),
            Image(id = 2, isFavorite = true),
            Image(id = 3, isFavorite = true),
        )

        val flowListImages = flow {
            emit(expected)
        }

        Mockito.`when`(fakeImageRepository.getFavorite())
            .thenReturn(flowListImages)

        val actual = useCase.execute().toList().first()

        assertEquals(expected, actual)
    }

}
package ru.nikitazar.domain.useCases

import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.nikitazar.domain.models.Image
import ru.nikitazar.domain.repository.ImageRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetPageUseCaseTest {
    private val fakeImageRepository = mock<ImageRepository>()
    private val useCase = GetPageUseCase(fakeImageRepository)

    @Test
    fun `should get flow`() = runTest {

        val expected = PagingData.from(
            listOf(
                Image(id = 0, isFavorite = true),
                Image(id = 1, isFavorite = true),
                Image(id = 2, isFavorite = true),
                Image(id = 3, isFavorite = true),
            )
        )

        val imageFlow = flow {
            emit(expected)
        }


        Mockito.`when`(fakeImageRepository.getPage())
            .thenReturn(imageFlow)

        val actual = useCase.execute().first()

        assertEquals(expected, actual)
    }
}
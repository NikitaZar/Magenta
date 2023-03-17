package ru.nikitazar.magenta.viewModel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.nikitazar.domain.models.Image
import ru.nikitazar.domain.repository.*
import ru.nikitazar.domain.useCases.*

@OptIn(ExperimentalCoroutinesApi::class)
class ImageListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ImageListViewModel

    private val dislikeUseCase = mock<DislikeUseCase>()
    private val likeUseCase = mock<LikeUseCase>()
    private val getPageUseCase = mock<GetPageUseCase>()
    private val getFavoriteUseCase = mock<GetFavoriteUseCase>()

    private val id = 0

    @BeforeEach
    fun beforeEach() {
        mockGetMainLooper()
        Dispatchers.setMain(dispatcher)

        viewModel = ImageListViewModel(
            getPageUseCase = getPageUseCase,
            likeUseCase = likeUseCase,
            dislikeUseCase = dislikeUseCase,
            getFavoriteUseCase = getFavoriteUseCase
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
        Mockito.reset(likeUseCase)
        Mockito.reset(dislikeUseCase)
        Mockito.reset(getPageUseCase)
        Mockito.reset(getFavoriteUseCase)
    }

    @Test
    fun `should call likeUseCase`() = runTest {
        viewModel.like(id).join()
        verify(likeUseCase, times(1)).execute(id)
    }

    @Test
    fun `should call dislikeUseCase`() = runTest {
        viewModel.dislike(id).join()
        verify(dislikeUseCase, times(1)).execute(id)
    }

    //TODO: test failed
    @Test
    fun `test pageDataFlow`() = runTest {
        val expected = PagingData.from(
            listOf(
                Image(id = 0),
                Image(id = 1),
                Image(id = 2),
                Image(id = 3),
            )
        )
        val imageFlow = flow {
            emit(expected)
        }
        Mockito.`when`(getPageUseCase.execute()).thenReturn(imageFlow)

        viewModel.pageDataFlow.collect { actual ->
            assertEquals(expected, actual)
        }
    }

    //TODO: Actual is null
    @Test
    fun `test favoriteData`() = runTest {
        val expected = listOf(
            Image(id = 0),
            Image(id = 1),
            Image(id = 2),
            Image(id = 3),
        )
        val imageFlow = flow {
            emit(expected)
        }
        Mockito.`when`(getFavoriteUseCase.execute()).thenReturn(imageFlow)
        val actual = viewModel.favoriteData.value
        assertEquals(expected, actual)
    }
}

private fun mockGetMainLooper() {
    ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
        override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
        override fun postToMainThread(runnable: Runnable) = runnable.run()
        override fun isMainThread() = true
    })
}
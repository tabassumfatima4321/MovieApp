package com.example.testapplication.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapplication.core.RequestResponse
import com.example.testapplication.services.movies.MoviesService
import com.example.testapplication.services.movies.repository.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    lateinit var moviesService: MoviesService
    lateinit var viewModel: HomeViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        moviesService = Mockito.mock()
        viewModel = HomeViewModel(
            moviesService = moviesService
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //This test is only for behaviour testing
    @Test
    fun should_return_movies_list() = runBlocking {

        val results = mock<Result>()
        whenever(results.adult).thenReturn(false)
        whenever(results.backdrop_path).thenReturn("")
        whenever(results.id).thenReturn(123)
        whenever(results.original_language).thenReturn("en")
        whenever(results.original_title).thenReturn("hello")
        whenever(results.overview).thenReturn("Movie overview")
        whenever(results.popularity).thenReturn(5.5)
        whenever(results.poster_path).thenReturn("")
        whenever(results.release_date).thenReturn("22/02/23")
        whenever(results.title).thenReturn("movie title")

        whenever(moviesService.getMovies()).thenReturn(
            MoviesResponse(
                page = 0,
                results = listOf(results),
                total_pages = 1,
                total_results = 1,
            )
        )

        viewModel.getMovies().observeForever {
            when (it) {
                is RequestResponse.Error -> {}
                is RequestResponse.Loading -> {}
                is RequestResponse.Success -> {
                    assert(it.data?.results?.isNotEmpty()!!)
                }
            }
        }
    }


}
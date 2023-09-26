package com.example.testapplication.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapplication.core.RequestResponse
import com.example.testapplication.services.movies.MoviesService
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse
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
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class MovieDescriptionViewModelTest {

    lateinit var moviesService: MoviesService
    lateinit var viewModel: MovieDescriptionViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        moviesService = Mockito.mock()
        viewModel = MovieDescriptionViewModel(
            moviesService = moviesService
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //This test is only for behaviour testing
    @Test
    fun should_return_selected_movies() = runBlocking {
        whenever(moviesService.getMoviesDescription(movieId = 123)).thenReturn(
            MovieDescriptionResponse(
                adult = false,
                backdrop_path = "",
                budget = 25,
                id = 123,
                original_language = "en",
                original_title = "hello",
                overview = "Movie overview",
                popularity = 5.5,
                poster_path = "",
                release_date = "22/02/23"
            )
        )

        viewModel.getMoviesDescription(123).observeForever {
            when(it){
                is RequestResponse.Success -> {
                    assert(it.data?.id == 123)
                }
                is RequestResponse.Loading -> {}
                is RequestResponse.Error -> {}
            }
        }
    }


}
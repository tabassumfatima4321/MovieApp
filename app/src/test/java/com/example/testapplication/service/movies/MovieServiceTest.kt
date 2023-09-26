package com.example.testapplication.service.movies

import com.example.testapplication.services.movies.MoviesService
import com.example.testapplication.services.movies.repository.MoviesRepository
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse
import com.example.testapplication.services.movies.repository.models.MoviesResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class MovieServiceTest {


    @Test
    fun should_return_selected_movie_if_movie_id_is_correct() = runBlocking {
        val repository = mock<MoviesRepository>()

        val service = MoviesService(repository = repository)
        val model = mock<MovieDescriptionResponse>()
        whenever(model.backdrop_path).thenReturn("")
        whenever(model.budget).thenReturn(2000)
        whenever(model.id).thenReturn(123)
        whenever(model.original_language).thenReturn("en")
        whenever(model.original_title).thenReturn("hello")
        whenever(model.overview).thenReturn("Movie overview")
        whenever(model.popularity).thenReturn(5.5)
        whenever(model.poster_path).thenReturn("")
        whenever(model.release_date).thenReturn("22/02/23")

        whenever(repository.getMoviesDescription(123)).thenReturn(model)

        assert(!service.getMoviesDescription(123).adult)

    }

    @Test
    fun should_return_list_of_movies() = runBlocking {
        val repository = mock<MoviesRepository>()
        val service = MoviesService(repository = repository)

        val results = mock<com.example.testapplication.services.movies.repository.models.Result>()
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


        val model = mock<MoviesResponse>()
        whenever(model.page).thenReturn(0)
        whenever(model.results).thenReturn(
            listOf(
                results, results, results
            )
        )
        whenever(model.total_pages).thenReturn(2)
        whenever(model.total_results).thenReturn(3)

        whenever(repository.getMovies()).thenReturn(model)

        assert(service.getMovies().results.size == 3)
    }


}
package com.example.testapplication.services.movies

import com.example.testapplication.services.movies.models.MoviesException
import com.example.testapplication.services.movies.repository.MoviesRepository
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse
import com.example.testapplication.services.movies.repository.models.MoviesResponse
import javax.inject.Inject

class MoviesService @Inject constructor(private val repository: MoviesRepository) {
    @Throws(MoviesException::class)
    suspend fun getMovies(): MoviesResponse {
        return repository.getMovies()
    }

    @Throws(MoviesException::class)
    suspend fun getMoviesDescription(movieId : Int): MovieDescriptionResponse {
        return repository.getMoviesDescription(movieID = movieId)
    }
}
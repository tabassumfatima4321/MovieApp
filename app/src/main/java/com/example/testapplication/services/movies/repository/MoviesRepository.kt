package com.example.testapplication.services.movies.repository

import com.example.testapplication.services.movies.models.MoviesException
import com.example.testapplication.services.movies.models.MoviesExceptionType
import com.example.testapplication.services.movies.repository.api.Constants
import com.example.testapplication.services.movies.repository.api.MoviesApi
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse
import com.example.testapplication.services.movies.repository.models.MoviesResponse
import java.io.IOException
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {

@Throws(MoviesException::class)
suspend fun getMovies(): MoviesResponse {
    try {
        val response = moviesApi.getMovies(Constants.getAPiKey())

        if (!response.isSuccessful) {
            throw MoviesException(MoviesExceptionType.UNKNOWN)
        }
        if (response.body() == null) throw MoviesException(MoviesExceptionType.UNKNOWN)

        return response.body()!!
    } catch (e: IOException) {
        throw MoviesException(MoviesExceptionType.NETWORK_ERROR)
    }
}

    @Throws(MoviesException::class)
    suspend fun getMoviesDescription(movieID: Int): MovieDescriptionResponse {
        try {
            val response = moviesApi.getMoviesDescription(movieId = movieID, apiKey = Constants.getAPiKey())

            if (!response.isSuccessful) {
                throw MoviesException(MoviesExceptionType.UNKNOWN)
            }
            if (response.body() == null) throw MoviesException(MoviesExceptionType.UNKNOWN)

            return response.body()!!
        } catch (e: IOException) {
            throw MoviesException(MoviesExceptionType.NETWORK_ERROR)
        }
    }

}
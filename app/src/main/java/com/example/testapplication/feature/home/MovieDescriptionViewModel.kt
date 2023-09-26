package com.example.testapplication.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testapplication.core.RequestResponse
import com.example.testapplication.services.movies.MoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDescriptionViewModel @Inject constructor(private val moviesService: MoviesService) : ViewModel() {

    fun getMoviesDescription(movieId : Int) = liveData {
        emit(RequestResponse.Loading())
        try {
            val movies = moviesService.getMoviesDescription(movieId = movieId)
            emit(RequestResponse.Success(movies))
            return@liveData
        } catch (exception: Exception) {
            emit(RequestResponse.Error(exception.message ?: ""))
        }
    }
}
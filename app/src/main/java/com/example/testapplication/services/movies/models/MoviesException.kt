package com.example.testapplication.services.movies.models

enum class MoviesExceptionType{
    UNKNOWN,
    NETWORK_ERROR
}

class MoviesException(private val exception:MoviesExceptionType): Exception(exception.name) {
}
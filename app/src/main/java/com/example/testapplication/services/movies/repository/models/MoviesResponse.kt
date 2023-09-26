package com.example.testapplication.services.movies.repository.models

data class MoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
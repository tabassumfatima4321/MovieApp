package com.example.testapplication.services.movies.repository.models

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>? = null,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)
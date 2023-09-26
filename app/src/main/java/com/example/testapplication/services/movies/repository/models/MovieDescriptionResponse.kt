package com.example.testapplication.services.movies.repository.models

data class MovieDescriptionResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection? = null,
    val budget: Int,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int,
    val imdb_id: String? = null,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>? = null,
    val production_countries: List<ProductionCountry>? = null,
    val release_date: String,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val spoken_languages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)
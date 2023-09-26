package com.example.testapplication.services.movies.repository.api

object Constants {
    const val getMoviesEndpoint = "movie/popular"
    const val getMoviesDescriptionEndPoint = "movie/{movie_id}"

    fun getBaseURL():String{
        return "https://api.themoviedb.org/3/"
    }

    fun getAPiKey() : String{
        return "a2963ead43b0230ab46ae9ab5eb37b62"
    }
}
package com.example.testapplication.services.movies.repository.api

import com.example.testapplication.clients.network.AuthenticatedNetworkClient
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse
import com.example.testapplication.services.movies.repository.models.MoviesResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MoviesApiModule{

    @Provides
    @Singleton
    fun providesMoviesApi(client: AuthenticatedNetworkClient): MoviesApi {
        return Retrofit.Builder()
            .client(client.getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.getBaseURL())
            .build()
            .create(MoviesApi::class.java)
    }
}

interface MoviesApi {

    @GET(Constants.getMoviesEndpoint)
    suspend fun getMovies(
        @Query(value = "api_key") apiKey: String,
    ): Response<MoviesResponse>

    @GET(Constants.getMoviesDescriptionEndPoint)
    suspend fun getMoviesDescription(
        @Path("movie_id") movieId: Int,
        @Query(value = "api_key") apiKey: String,
    ): Response<MovieDescriptionResponse>
}

package com.example.testapplication.clients.network

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object NetworkClientModule {
    @Provides
    fun providesAuthenticatedNetworkClient(application: Application): AuthenticatedNetworkClient {
        return AuthenticatedNetworkClient(application)
    }
}
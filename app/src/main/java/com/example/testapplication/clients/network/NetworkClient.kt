package com.example.testapplication.clients.network

import android.app.Application
import com.example.testapplication.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit


interface INetworkClient {
    fun getClient(): OkHttpClient
}

class AuthenticatedNetworkClient(private val application: Application) : INetworkClient {

    override fun getClient(): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024
        val cacheDirectory = File(application.cacheDir, "http-cache")

        val cache = Cache(cacheDirectory, cacheSize.toLong())

        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Cache-Control", "public, max-age=86400")
                    .addHeader("Platform", "Android")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("App-Version", BuildConfig.VERSION_NAME)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

}

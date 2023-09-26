package com.example.testapplication.core

sealed class RequestResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val customMessage: Boolean? = false
) {
    class Success<T>(data: T) : RequestResponse<T>(data)
    class Loading<T>(data: T? = null) : RequestResponse<T>(data)
    class Error<T>(message: String, data: T? = null, customMessage: Boolean? = false) :
        RequestResponse<T>(data, message, customMessage)
}
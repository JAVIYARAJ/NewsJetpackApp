package com.example.newsjetpackapp.remote.model

import java.lang.Exception

sealed class ApiResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : ApiResult<T>()

    data class Error<out T : Any>(val exception: Exception) : ApiResult<T>()
}

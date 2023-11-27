package com.example.newsjetpackapp.remote

import com.example.newsjetpackapp.remote.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers("Content-Type:application/json")
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String, @Query("apiKey") apiKey: String
    ): Response<NewsModel>
}
package com.example.newsjetpackapp.remote.data

import com.example.newsjetpackapp.remote.model.Article

data class NewsState(
    val isLoading: Boolean = false,
    val news: List<Article> = emptyList(),
    val selectedArticle: Article? = null,
)

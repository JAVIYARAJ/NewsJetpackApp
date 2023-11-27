package com.example.newsjetpackapp.remote

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsjetpackapp.remote.model.Article
import kotlinx.coroutines.launch


class NewsViewModel() : ViewModel() {

    val news = mutableStateListOf<Article>()

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article> = _selectedArticle

    var isLoading = mutableStateOf(true)

    init {
        isLoading = mutableStateOf(true)
        getNews("coding", ApiClient.API_KEY)
    }

    private fun getNews(query: String, apiKey: String) {

        viewModelScope.launch {
            isLoading = mutableStateOf(false)
            val result = ApiClient.getApiClient().getNews(query, apiKey)
            if (result.isSuccessful) {
                news.addAll(result.body()!!.articles)
            } else {

            }
        }
    }

    fun selectedArticle(article: Article) {
        _selectedArticle.postValue(article)
    }

}
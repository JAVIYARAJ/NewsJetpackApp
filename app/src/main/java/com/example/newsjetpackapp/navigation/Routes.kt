package com.example.newsjetpackapp.navigation

sealed class Routes(val route: String) {

    object HomeRoute : Routes("home_route")
    object ArticleRoute : Routes("article_route")
}

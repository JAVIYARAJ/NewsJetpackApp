package com.example.newsjetpackapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsjetpackapp.screens.ArticleScreen
import com.example.newsjetpackapp.screens.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.HomeRoute.route) {
        composable(Routes.HomeRoute.route) {
            HomeScreen(navController)
        }

        composable(
            Routes.ArticleRoute.route
        ) {
            ArticleScreen()
        }
    }
}
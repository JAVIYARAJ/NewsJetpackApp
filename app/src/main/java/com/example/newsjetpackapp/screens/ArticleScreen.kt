package com.example.newsjetpackapp.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun ArticleScreen() {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {

                    }
                }
                loadUrl(
                    "https://paulallies.medium.com/jetpack-compose-api-data-to-list-view-35cb5ea66a95"
                        ?: ""
                )
            }
        })
    }
}
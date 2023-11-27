package com.example.newsjetpackapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsjetpackapp.R
import com.example.newsjetpackapp.navigation.Routes
import com.example.newsjetpackapp.remote.NewsViewModel
import com.example.newsjetpackapp.remote.model.Article
import com.example.newsjetpackapp.util.Util
import com.example.newsjetpackapp.widgets.TopAppBarWidget

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(controller: NavHostController) {

    val newsModel = NewsViewModel()

    val selectedArti by newsModel.selectedArticle.observeAsState()

    var modalSheetVisible by remember {
        mutableStateOf(false)
    }

    val sheetState =
        androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)

    LaunchedEffect(modalSheetVisible) {
        if (bottomSheetState.isVisible) {
            sheetState.hide()
        } else {
            sheetState.show()
        }
    }

    Scaffold(topBar = { TopAppBarWidget(title = "News") }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (newsModel.isLoading.value) {
                CircularProgressIndicator(color = Color.Blue)
            } else {
                LazyColumn() {
                    items(newsModel.news.size) {
                        NewsCard(model = newsModel.news[it], onTap = {
                            //newsModel.selectedArticle(newsModel.news[it])
                            controller.navigate(Routes.ArticleRoute.route)
                        })
                    }
                }
            }

            selectedArti?.let {
                ModalBottomSheet(onDismissRequest = { modalSheetVisible = !modalSheetVisible },
                    sheetState = sheetState,
                    content = {
                        NewsModelDesign(it)
                    })
            }

        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsCard(model: Article, onTap: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 2.5.dp)
            .clickable {
                onTap()
            }, shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.errorContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ImageHolder(
                imageUrl = if (model.urlToImage != null) {
                    model.urlToImage
                } else "https://pioneer-technical.com/wp-content/uploads/2016/12/news-placeholder.png",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = model.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (model.author != null) model.author else "",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = Util.getDateAndTime(model.publishedAt),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Composable
fun ImageHolder(
    imageUrl: String?, modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
        contentDescription = "Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        placeholder = painterResource(R.drawable.ic_news_placeholder_icon),
        error = painterResource(R.drawable.ic_news_placeholder_icon)
    )
}


@Composable
fun NewsModelDesign(model: Article) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = model.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            ImageHolder(
                imageUrl = model.urlToImage, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = model.description,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Text(text = "Read More", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
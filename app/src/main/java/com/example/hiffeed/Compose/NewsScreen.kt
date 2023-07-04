package com.example.hiffeed.Compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.hiffeed.database.NewsItem
import com.example.hiffeed.database.ViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


    @Composable
    fun NewsScreen(viewModel : ViewModel) {
        val news: List<NewsItem> by viewModel.news.observeAsState(emptyList())
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() },
        ) {
            if (isRefreshing) {
                LazyColumn {
                    items(10) { index ->
                        LoadingItem()
                    }
                }
            } else {
                LazyColumn {
                    items(news.size) { index ->
                        newsItem(news[index])
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun newsItem(newsItem: NewsItem) {
        val mContext = LocalContext.current
        Card(elevation = 10.dp, modifier = Modifier.padding(9.dp), onClick ={ handleClick(newsItem.link,mContext)} ){
            Row(

                modifier = Modifier.fillMaxWidth(),

                ) {
                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),

                ) {
                    Text(text = newsItem.header,
                        style = MaterialTheme.typography.h6,
                        fontSize = 17.sp

                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(text = newsItem.text,
                        style = MaterialTheme.typography.caption,
                        fontSize = 13.sp,
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = newsItem.src,
                            style = MaterialTheme.typography.caption,
                            fontSize = 11.sp,
                            modifier= Modifier.fillMaxWidth().weight(0.9F)

                        )


                    }



                }

            }
        }
    }
@Composable

fun LoadingItem() {
    Card(elevation = 10.dp, modifier = Modifier.padding(9.dp)) {
        Row(

            modifier = Modifier.fillMaxWidth(),

            ) {
            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),

                ) {
                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .shimmerEffect()


                )
                Spacer(modifier = Modifier.height(9.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(25.dp)
                        .shimmerEffect()

                )
                Spacer(modifier = Modifier.height(9.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(15.dp)
                            .size(15.dp)
                            .shimmerEffect()

                    )

                }
            }
        }
    }
}
    fun handleClick(src: String, mContext: Context) {
        Log.e("main",src)
        mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(src)))
    }

    @Composable
    fun image(src :String) {
        Image(
            painter = rememberAsyncImagePainter(src),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .width(198.dp),
            contentScale = ContentScale.Fit,

            )
    }

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFEE0E0E),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}





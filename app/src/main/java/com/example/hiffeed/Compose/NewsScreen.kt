package com.example.hiffeed.Compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import com.example.hiffeed.database.MessageAndNews.News.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
    fun NewsScreen(newsViewModel : NewsViewModel) {
        val news: List<NewsItem> by newsViewModel.news.observeAsState(emptyList())
        val isRefreshing by newsViewModel.isRefreshing.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { newsViewModel.refresh() },
        ) {
            if (isRefreshing) {
                LoadingItem()
            } else {
                LazyColumn {
                    items(news.size) { index ->
                        newsItem(news[index])
                    }
                }
            }

        }
    }

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun newsItem(newsItem: NewsItem) {
        val mContext = LocalContext.current
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface,),
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),  modifier = Modifier.padding(15.dp), onClick ={ handleClick(newsItem.link,mContext)} ){
            Row(

                modifier = Modifier.fillMaxWidth(),

                ) {
                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(17.dp),

                ) {
                    Text(text = newsItem.header,
                        style = MaterialTheme.typography.h6,
                        fontSize = 20.sp

                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = newsItem.text,
                        style = MaterialTheme.typography.caption,
                        fontSize = 15.sp,
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
    Row(

        modifier = Modifier.fillMaxWidth(),

        ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),

            ) {
            Text(text = "Loading...",
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp)
        }
    }
}
    fun handleClick(src: String, mContext: Context) {
        mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(src)))
    }








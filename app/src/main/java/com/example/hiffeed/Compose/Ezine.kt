package com.example.hiffeed.Compose


import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiffeed.database.MessageAndNews.Message.ForumViewModel
import com.example.hiffeed.database.MessageAndNews.Message.MessageItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@Composable
fun ezine(forumViewModel : ForumViewModel) {

    val messages: List<MessageItem> by forumViewModel.messages.observeAsState(emptyList())
    val isRefreshing by forumViewModel.isRefreshing.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { forumViewModel.refresh() },
    ) {
        if (isRefreshing) {
            LoadingItem()
        } else {

            LazyColumn {
                items(messages.size) { index ->
                    messageItem(messages[index])
                }
            }
        }
    }
}

fun friendlyUrl(url: String): String {
    var transformedUrl = url
    val prefixesToRemove = listOf("http://", "https://", "www.")
    for (prefix in prefixesToRemove) {
        if (transformedUrl.contains(prefix))
            transformedUrl = transformedUrl.replace(prefix, "")
    }
    return transformedUrl

}

@Composable
fun messageItem(messageItem: MessageItem) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface,),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground), modifier = Modifier.padding(15.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),

                ) {
                Text(
                    text = messageItem.NicName,
                    style = MaterialTheme.typography.h6,
                    fontSize = 20.sp

                )
                Quoats(messageItem.Quotes)
                Text(
                    text = messageItem.TextBody,
                    style = MaterialTheme.typography.body1,
                    fontSize = 17.sp
                    )
                if(messageItem.Link.isNotEmpty()) {
                    Text(
                        text =friendlyUrl(messageItem.Link),
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = Color.Cyan,
                        fontSize = 9.sp,
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { handleClick(messageItem.Link, context) })
                }
        }
    }
}
@Composable
fun Quoats(quotesPair: List<Pair<String, String>>) {
    if (quotesPair.isNotEmpty()) {
        for (index in quotesPair.indices) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) ){
                    var isExpanded by remember { mutableStateOf(false) }
                        Column( modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { isExpanded = !isExpanded }) {
                            Text(
                                text = quotesPair[index].first,
                                style = MaterialTheme.typography.h6,
                                fontSize = 17.sp

                            )
                            if (isExpanded) {
                                Text(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    text = quotesPair[index].second.trim(),
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 15.sp

                                )
                            }
                            else {
                                Text(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    text = quotesPair[index].second.trim(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 15.sp

                                )
                            }
                            Divider(color = MaterialTheme.colors.onBackground)


                        }
                    }
                }
            }

    }










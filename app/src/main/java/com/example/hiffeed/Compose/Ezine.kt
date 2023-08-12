package com.example.hiffeed.Compose

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ezine() {
    val url = "https://www.svenskafans.com/fotboll/hif/forum"
    val background = MaterialTheme.colors.background
    val foreground = MaterialTheme.colors.onBackground
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    injectCSS(background, foreground)
                    coroutineScope.launch {
                        delay(2000)
                        isLoading = false
                    }
                }
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    if (!url.contains("https://www.svenskafans.com/")) {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        return true
                    }
                    return false
                }
            }

            loadUrl(url)
        }
    })


    if (isLoading) {
        // Show loading screen while WebView is loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


}
private fun WebView.injectCSS(background: Color, foreground: Color) {
    val backgroundCss = "rgb(${(background.red * 255).toInt()},${(background.green * 255).toInt()},${(background.blue * 255).toInt()});"
    val foregroundCss = "rgb(${(foreground.red * 255).toInt()},${(foreground.green * 255).toInt()},${(foreground.blue * 255).toInt()});"

    val cssStyles = """
         
html, body, :not([style*="background-color:"]):not(iframe) {
  background-color: $backgroundCss !important;
}
:not([style*="border-color:"]), ::before, ::after {
  border-color: rgb(80, 115, 134) !important;
}
html, body, :not([style*="color:"]), .sr-reader :not([class*="sr-pivot"]) {
  color: $foregroundCss !important;
}
* {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.ijjjNG::before {
    display: none !important;
}



.forum__next-game,
.ad-slot,
.forum__info,
header,
footer,
.error,
.forum__login,
.puff,
.widget,
form,
.forum__heading,
font,b {
	background-color: $backgroundCss;
	display: none; 
}
 """.trimIndent()


    evaluateJavascript(
        """
            (function() {
                var style = document.createElement('style');
                style.innerHTML = `${cssStyles}`;
                document.head.appendChild(style);
                var elements= getElementsByClassName("forum__message")
                elements[0].scrollIntoView();
             
            })();
            """.trimIndent(),
        null
    )
}




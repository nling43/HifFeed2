package com.example.hiffeed.Compose.Stats

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable

fun Home(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(MaterialTheme.colors.background)

        ) {
        Text(
            text = isSystemInDarkTheme().toString(),
            style = MaterialTheme.typography.h6,
            fontSize = 20.sp

        )
    }
}
package com.aneury1.ourchat.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aneury1.ourchat.R


@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image
        contentDescription = "Avatar",
        modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.Gray)
    )
}

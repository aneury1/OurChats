package com.aneury1.ourchat.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aneury1.ourchat.Data.ChatMessage


@Composable
fun ChatScreenWithList(messages: List<ChatMessage>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECE5DD)),
        reverseLayout = true,
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(messages.reversed()) { message ->
            ChatBubble(message)
        }
    }
}
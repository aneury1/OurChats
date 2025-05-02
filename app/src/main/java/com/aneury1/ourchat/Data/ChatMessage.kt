package com.aneury1.ourchat.Data

data class ChatMessage(
    val id: Int,
    val text: String,
    val isFromUser: Boolean
)
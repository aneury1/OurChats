package com.aneury1.ourchat

import android.os.Bundle
import android.view.textclassifier.ConversationActions.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aneury1.ourchat.Data.ChatMessage
import com.aneury1.ourchat.Screen.ChatBubble
import com.aneury1.ourchat.Screen.ChatScreenWithList
import com.aneury1.ourchat.ui.theme.OurChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OurChatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val sampleMessages = listOf(
        ChatMessage(1,
            "" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    "Hey, how are you?" +
                    ""

            , false),
        ChatMessage(2, "I'm good! You?", true),
        ChatMessage(3, "Doing great, thanks for asking.", false),
        ChatMessage(4, "Let's catch up later.", true),
        ChatMessage(1, "Hey, how are you?", false),
        ChatMessage(2, "I'm good! You?", true),
        ChatMessage(3, "Doing great, thanks for asking.", false),
        ChatMessage(4, "Let's catch up later.", true),
        ChatMessage(1, "Hey, how are you?", false),
        ChatMessage(2, "I'm good! You?", true),
        ChatMessage(3, "Doing great, thanks for asking.", false),
        ChatMessage(4, "Let's catch up later.", true)
    )
    ChatScreenWithList(sampleMessages)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OurChatTheme {
        Greeting("Android")
    }
}
package com.aneury1.ourchat.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aneury1.ourchat.Data.ChatMessage

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isFromUser) Color(0xFFE1FFC7) else Color.White
    val alignment = if (message.isFromUser) Alignment.End else Alignment.Start
    val shape = if (message.isFromUser)
        RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp)
    else
        RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement =  if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .background(color = bubbleColor, shape = shape)
                .padding(12.dp)
        ) {
            Column{

               Row{
                   if(message.isFromUser or true){
                       Avatar()
                   }
                   Text(
                       "USERNAME",
                       modifier = Modifier.padding(10.dp),
                       fontWeight = FontWeight.Black,
                       color=Color(0xFF0b7327))
               }




                Text(
                    text = message.text,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = message.timestamp,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp)
                )
            }


        }
    }
}

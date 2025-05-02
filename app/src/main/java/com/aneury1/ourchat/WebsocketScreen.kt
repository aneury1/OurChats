package com.aneury1.ourchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
/*
@Composable
fun WebSocketScreen() {
    val socketClient = remember { ComposeWebSocket() }
    var input by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }




    LaunchedEffect(Unit) {
        socketClient.connect("ws://192.168.1.139:9003") {
            messages = messages + it
        }
    }
    Column(modifier = Modifier
        .fillMaxSize())
    {

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.80f)
                .background(Color.Yellow)
        ) {

            items(count = messages.size){
                Box(
                    modifier=Modifier.height(100.dp)
                        .fillParentMaxWidth()
                        .background(Color.Green)
                ){
                    Text("TEXT:"+messages[it])
                }
            }

           // messages.forEach {
           //     Text("🔁 $it")
           // }

        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.20f)
                .background(Color.Red)
        ) {
          Row(){
              TextField(value=input, {
                  input = it })
              Button({
                  socketClient.send(input)
              }) {
                  Text("ENVIAR")
              }
          }
        }
/ *
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Red)
        ) {

            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Button(onClick = {
                socketClient.send(input+"HOLA CARAabllao")
                input = ""
            }) {
                Text("Send")
            }
        }

        Text("WebSocket Chat", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        messages.forEach {
            Text("🔁 $it")
        }

        Spacer(Modifier.height(16.dp))

        * /
    }

    DisposableEffect(Unit) {
        onDispose {
            socketClient.close()
        }
    }
}*/

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip



@Composable
fun ChatScreen() {
    // var messages = remember { mutableStateListOf<String>() }
    var currentMessage by remember { mutableStateOf("") }
    var currentUser by remember { mutableStateOf("") }
    val socketClient = remember { ComposeWebSocket() }
    var messages by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        socketClient.connect("ws://192.168.1.139:9002") {
            messages = messages + it
        }
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Spacer(modifier=Modifier.height(100.dp))
        Text("Escribe To nickname opcional")
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = currentUser,
                onValueChange = { currentUser = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe tu nick") },
                maxLines = 2
            )
            IconButton(
                onClick = {
                    if (currentUser.isNotBlank()) {
                        socketClient.send("user-register:$currentUser")
                        currentUser = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
        Spacer(modifier=Modifier.height(10.dp))
        Text("Escribe mensajes")
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                maxLines = 2
            )
            IconButton(
                onClick = {
                    if (currentMessage.isNotBlank()) {
                        socketClient.send(currentMessage)
                        //messages.add(currentMessage.trim())
                        currentMessage = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                Text(
                    text = message,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    color = Color.Black
                )
            }
        }

    }
    DisposableEffect(Unit) {
        onDispose {
            socketClient.close()
        }
    }
}

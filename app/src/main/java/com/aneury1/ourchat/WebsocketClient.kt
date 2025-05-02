package com.aneury1.ourchat
import okhttp3.*
import okio.ByteString


class WebsocketClient{
    private val client = OkHttpClient()

    private lateinit var webSocket: WebSocket

    fun start(/*url: String*/) {
        val request = Request.Builder()
            .url("192.168.1.139:9002") // e.g., "ws://echo.websocket.org" or your server
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("✅ Connected to server")
                webSocket.send("Hello from Android!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("📩 Received text: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                println("📩 Received binary: $bytes")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("⚠️ Closing: $code / $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("❌ Error: ${t.message}")
            }
        })
    }

    fun sendMessage(message: String) {
        if (::webSocket.isInitialized) {
            webSocket.send(message)
        }
    }

    fun close() {
        if (::webSocket.isInitialized) {
            webSocket.close(1000, "Bye!")
        }
    }
}

class ComposeWebSocket {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var listener: ((String) -> Unit)? = null

    fun connect(url: String, onMessage: (String) -> Unit) {
        listener = onMessage
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                println("✅ Connected")
               /// ws.send("Hello WebSocket from Compose")
            }

            override fun onMessage(ws: WebSocket, text: String) {
                listener?.invoke(text)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                println("❌ Failed: ${t.message}")
            }
        })
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Bye")
    }
}
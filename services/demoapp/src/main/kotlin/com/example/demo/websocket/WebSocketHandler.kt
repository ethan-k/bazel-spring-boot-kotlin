package com.example.demo.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap


@Component
class WebSocketHandler : TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions[session.id] = session

        // Start sending messages in a separate thread
        Thread {
            try {
                repeat(10) {
                    if (session.isOpen) {
                        session.sendMessage(TextMessage("Hello ${System.currentTimeMillis()}"))
                        Thread.sleep(1000)
                    }
                }
            } catch (e: Exception) {
                session.close(CloseStatus.SERVER_ERROR)
            }
        }.start()
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val receivedMessage = message.payload
        session.sendMessage(TextMessage("Received: $receivedMessage"))
    }
}
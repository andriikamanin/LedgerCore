package it.volta.ts.kamaninandrii.peertopeer.network;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class P2PWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Логика обработки сообщений
        System.out.println("Received message: " + message.getPayload());

        // Ответ на клиентское сообщение (можно также отправлять данные о блоках или транзакциях)
        try {
            session.sendMessage(new TextMessage("Message received"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Логика после установления соединения
        System.out.println("Connection established with: " + session.getId());
    }
}
package it.volta.ts.kamaninandrii.peertopeer;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketService {

    public void sendBlockToPeers(String blockData) {
        // Логика отправки блока всем подключённым узлам
        System.out.println("Sending block data: " + blockData);
    }

    public void handleIncomingBlock(WebSocketSession session, String blockData) {
        // Логика обработки полученных блоков
        System.out.println("Received block data: " + blockData);
        // Синхронизация блоков с локальным блокчейном
    }
}
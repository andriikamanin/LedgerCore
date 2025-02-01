package it.volta.ts.kamaninandrii.peertopeer;


import org.springframework.stereotype.Component;

@Component
public class P2PManager {

    private final WebSocketService webSocketService;

    public P2PManager(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }



    public void broadcastBlock(String blockData) {
        // Логика для передачи блоков всем узлам
        webSocketService.sendBlockToPeers(blockData);
    }

    public void syncBlockchain() {
        // Логика синхронизации блокчейна между узлами
    }
}

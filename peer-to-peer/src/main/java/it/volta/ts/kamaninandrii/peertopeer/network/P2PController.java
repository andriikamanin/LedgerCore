package it.volta.ts.kamaninandrii.peertopeer.network;


import it.volta.ts.kamaninandrii.peertopeer.P2PManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class P2PController {

    private final P2PManager p2PManager;

    public P2PController(P2PManager p2PManager) {
        this.p2PManager = p2PManager;
    }

    @MessageMapping("/newBlock")
    public void handleNewBlock(String blockData) {
        // Обработка нового блока, например, синхронизация с локальной копией
        p2PManager.broadcastBlock(blockData);
    }
}
package it.volta.ts.kamaninandrii.peertopeer.config;

import it.volta.ts.kamaninandrii.peertopeer.P2PManager;
import it.volta.ts.kamaninandrii.peertopeer.WebSocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P2PConfig {

    private final WebSocketService webSocketService;

    public P2PConfig(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Bean
    public P2PManager p2PManager() {
        return new P2PManager(webSocketService);  // Передаем WebSocketService в конструктор P2PManager
    }
}
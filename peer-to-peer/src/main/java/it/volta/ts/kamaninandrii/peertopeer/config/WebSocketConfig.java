package it.volta.ts.kamaninandrii.peertopeer.config;

import it.volta.ts.kamaninandrii.peertopeer.network.P2PWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // Убираем конструктор, Spring сам инжектирует бин через @Bean

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(p2PWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }

    // Регистрация P2PWebSocketHandler как @Bean
    @Bean
    public P2PWebSocketHandler p2PWebSocketHandler() {
        return new P2PWebSocketHandler();
    }
}
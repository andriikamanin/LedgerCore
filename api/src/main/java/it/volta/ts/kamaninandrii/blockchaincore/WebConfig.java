package it.volta.ts.kamaninandrii.blockchaincore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить запросы на все эндпоинты
                .allowedOrigins("*") // Разрешить запросы с любого IP/домена
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Указать разрешенные HTTP методы
                .allowedHeaders("*") // Разрешить любые заголовки
                .allowCredentials(false) // Без учетных данных
                .maxAge(3600); // Кешировать preflight запросы на 1 час
    }
}

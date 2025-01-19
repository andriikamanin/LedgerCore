package it.volta.ts.kamaninandrii.blockchaincore;

import it.volta.ts.kamaninandrii.blockchain.BlockchainManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BlockchainManager blockchainManager() {
        return new BlockchainManager();
    }
}

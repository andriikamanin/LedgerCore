package it.volta.ts.kamaninandrii.blockchaincore;

import it.volta.ts.kamaninandrii.blockchain.Blockchain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        Blockchain blockchain = new Blockchain();

        // Запуск автоматического майнинга
        blockchain.startAutoMine();

        // Добавление транзакций (в пул)
        blockchain.addTransaction("system", "user25", 200.0);
        blockchain.addTransaction("system", "user25", 300.0);
        blockchain.addTransaction("system", "user25", 150.0);
        blockchain.addTransaction("system", "user25", 100.0);
        blockchain.addTransaction("system", "user25", 50.0);
    }
}

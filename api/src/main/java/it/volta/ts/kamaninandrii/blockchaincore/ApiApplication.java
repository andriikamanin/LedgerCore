package it.volta.ts.kamaninandrii.blockchaincore;

import it.volta.ts.kamaninandrii.blockchain.Blockchain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = "it.volta.ts.kamaninandrii")
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        Blockchain blockchain = new Blockchain();

        System.out.println("API IS RUNNING");
    }
}

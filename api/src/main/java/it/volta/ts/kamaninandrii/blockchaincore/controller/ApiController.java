package it.volta.ts.kamaninandrii.blockchaincore.controller;

import it.volta.ts.kamaninandrii.blockchaincore.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Service is running");
    }

    // Изменяем на @RequestBody для передачи транзакции в JSON
    @PostMapping("/transaction")
    public String addTransaction(@RequestBody TransactionRequest transactionRequest) {
        return blockchainService.transfer(
                transactionRequest.getSender(),
                transactionRequest.getReceiver(),
                transactionRequest.getAmount()
        );
    }

    // Создание блока
    @PostMapping("/block")
    public String createBlock() {
        return blockchainService.createBlock();
    }

    // Получение баланса пользователя
    @GetMapping("/balance")
    public String getBalance(@RequestParam String user) {
        return "Balance of " + user + ": " + blockchainService.getBalance(user);
    }

    // Проверка валидности блокчейна
    @GetMapping("/validate")
    public String validateBlockchain() {
        return blockchainService.validateBlockchain() ? "Blockchain is valid." : "Blockchain is invalid!";
    }

    // Получение состояния блокчейна
    @GetMapping("/chain")
    public String getBlockchain() {
        return blockchainService.getBlockchain();
    }

    // Пополнение баланса
    @PostMapping("/deposit")
    public String deposit(@RequestBody DepositRequest depositRequest) {
        return blockchainService.deposit(depositRequest.getUser(), depositRequest.getAmount());
    }


    // Создание нового пользователя
    @PostMapping("/createUser")
    public String createUser(@RequestBody CreateUserRequest createUserRequest) {
        blockchainService.createUser(createUserRequest.getAddress());
        return "User " + createUserRequest.getAddress() + " created!";
    }
}
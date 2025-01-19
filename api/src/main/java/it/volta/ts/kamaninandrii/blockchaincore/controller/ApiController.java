package it.volta.ts.kamaninandrii.blockchaincore.controller;

import it.volta.ts.kamaninandrii.blockchain.util.BlockchainParser;
import it.volta.ts.kamaninandrii.blockchaincore.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class ApiController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Service is running");
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        if (transactionRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        String response = blockchainService.transfer(
                transactionRequest.getSender(),
                transactionRequest.getReceiver(),
                transactionRequest.getAmount()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/block")
    public ResponseEntity<String> createBlock() {
        String response = blockchainService.createBlock();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String user) {
        String response = blockchainService.getBalance(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateBlockchain() {
        boolean isValid = blockchainService.validateBlockchain();
        return ResponseEntity.ok(isValid ? "Blockchain is valid." : "Blockchain is invalid!");
    }

    @GetMapping("/chain")
    public ResponseEntity<String> getBlockchain() {
        return ResponseEntity.ok(blockchainService.getBlockchain());
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionRequest transactionRequest) {
        if (transactionRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Deposit amount must be greater than zero.");
        }

        String response = blockchainService.transfer(
                transactionRequest.getSender(),
                transactionRequest.getReceiver(),
                transactionRequest.getAmount()
        );

        return ResponseEntity.ok(response);
    } //346

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        String response = blockchainService.createUser(createUserRequest.getAddress());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<BlockchainParser.Transaction>> getTransactions() {
        String rawBlockchainData = blockchainService.getBlockchain();
        List<BlockchainParser.Transaction> transactions = BlockchainParser.parseBlockchainData(rawBlockchainData);
        return ResponseEntity.ok(transactions);
    }
}
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


    @PostMapping("/transaction")
    public String addTransaction(@RequestParam String sender, @RequestParam String receiver, @RequestParam double amount) {
        return blockchainService.addTransaction(sender, receiver, amount);
    }

    @PostMapping("/block")
    public String createBlock() {
        return blockchainService.createBlock();
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam String user) {
        return "Balance of " + user + ": " + blockchainService.getBalance(user);
    }

    @GetMapping("/validate")
    public String validateBlockchain() {
        return blockchainService.validateBlockchain() ? "Blockchain is valid." : "Blockchain is invalid!";
    }

    @GetMapping("/chain")
    public String getBlockchain() {
        return blockchainService.getBlockchain();
    }
}

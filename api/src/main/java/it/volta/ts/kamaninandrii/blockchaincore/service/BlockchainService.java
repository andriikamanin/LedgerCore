package it.volta.ts.kamaninandrii.blockchaincore.service;



import it.volta.ts.kamaninandrii.blockchain.BlockchainManager;

import org.springframework.stereotype.Service;

@Service
public class BlockchainService {

    private final BlockchainManager blockchainManager;

    public BlockchainService() {
        this.blockchainManager = new BlockchainManager();
    }

    public String addTransaction(String sender, String receiver, double amount) {
        boolean success = blockchainManager.addTransaction(sender, receiver, amount);
        return success ? "Transaction added successfully!" : "Transaction failed: insufficient funds.";
    }

    public String createBlock() {
        blockchainManager.createBlock();
        return "Block created!";
    }

    public double getBalance(String user) {
        return blockchainManager.getBalance(user);
    }

    public boolean validateBlockchain() {
        return blockchainManager.isBlockchainValid();
    }

    public String getBlockchain() {
        return blockchainManager.toString();
    }
}
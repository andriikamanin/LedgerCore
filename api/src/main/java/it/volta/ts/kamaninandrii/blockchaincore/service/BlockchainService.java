package it.volta.ts.kamaninandrii.blockchaincore.service;

import it.volta.ts.kamaninandrii.blockchain.BlockchainManager;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;
import it.volta.ts.kamaninandrii.blockchain.bean.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class BlockchainService {

    private final BlockchainManager blockchainManager;

    public BlockchainService() {
        this.blockchainManager = new BlockchainManager();
    }

    public String createBlock() {
        blockchainManager.createBlock();
        return "Block successfully created!";
    }

    public boolean validateBlockchain() {
        return blockchainManager.isBlockchainValid();
    }

    public String getBlockchain() {
        return blockchainManager.toString();
    }

    public String getBalance(String userAddress) {
        User user = blockchainManager.getUser(userAddress);
        return user != null
                ? "Balance for " + userAddress + ": " + user.getBalance()
                : "User " + userAddress + " not found.";
    }

    public String createUser(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Address cannot be empty!";
        }

        blockchainManager.createUser(address);
        return "User " + address + " successfully created!";
    }

    public String transfer(String senderAddress, String receiverAddress, double amount) {
        if (amount <= 0) {
            return "Transaction amount must be greater than zero.";
        }

        User sender = blockchainManager.getUser(senderAddress);
        if (sender == null) {
            return "Sender " + senderAddress + " not found.";
        }

        User receiver = blockchainManager.getUser(receiverAddress);
        if (receiver == null) {
            return "Receiver " + receiverAddress + " not found.";
        }

        if (blockchainManager.getBalanceWithPending(senderAddress) < amount) {
            return "Insufficient balance for this transaction.";
        }

        Transaction transaction = new Transaction(senderAddress, receiverAddress, amount);
        blockchainManager.addTransaction(transaction);

        return "Transaction successfully added: " + transaction;
    }
}
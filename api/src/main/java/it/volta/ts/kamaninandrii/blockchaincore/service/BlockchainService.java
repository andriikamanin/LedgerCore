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
        return "Block created!";
    }



    public boolean validateBlockchain() {
        return blockchainManager.isBlockchainValid();
    }

    public String getBlockchain() {
        return blockchainManager.toString();
    }







    // Метод для вывода баланса
    public String getBalance(String userAddress) {
        User user = blockchainManager.getUser(userAddress);
        if (user == null) {
            return "User " + userAddress + " not found!";
        }
        return "User balance: " + user.getBalance();
    }

    // Метод для создания нового пользователя
    public String createUser(String address) {
        blockchainManager.createUser(address);  // Используем метод BlockchainManager для создания пользователя
        return "User " + address + " created!";
    }


    public String transfer(String senderAddress, String receiverAddress, double amount) {
        if (amount <= 0) {
            return "Transfer amount must be greater than zero!";
        }

        User sender = blockchainManager.getUser(senderAddress);
        if (sender == null) {
            return "Sender " + senderAddress + " not found!";
        }

        User receiver = blockchainManager.getUser(receiverAddress);
        if (receiver == null) {
            return "Receiver " + receiverAddress + " not found!";
        }

        if (blockchainManager.getBalanceWithPending(senderAddress) < amount) {
            return "Insufficient funds for transfer!";
        }

        Transaction transaction = new Transaction(senderAddress, receiverAddress, amount);
        blockchainManager.addTransaction(transaction); // Используем новый метод добавления

        return "Transaction added: " + transaction;
    }
}
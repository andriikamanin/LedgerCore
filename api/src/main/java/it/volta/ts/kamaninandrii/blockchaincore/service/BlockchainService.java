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



    public String deposit(String userAddress, double amount) {
        if (amount <= 0) {
            return "Deposit amount must be greater than zero!";
        }

        // Проверка существования пользователя
        User user = blockchainManager.getUser(userAddress);
        if (user == null) {
            return "User " + userAddress + " not found!";
        }

        // Создаем транзакцию "system" -> "user" на указанную сумму
        Transaction transaction = new Transaction("system", userAddress, amount);

        // Обрабатываем транзакцию через метод processTransaction
        blockchainManager.processTransaction(transaction);

        // Возвращаем сообщение об успешном депозите
        return "Deposit successful. Balance updated: " + user.getBalance();
    }

    public String transfer(String senderAddress, String receiverAddress, double amount) {
        if (amount <= 0) {
            return "Transfer amount must be greater than zero!";
        }

        // Проверка существования отправителя
        User sender = blockchainManager.getUser(senderAddress);
        if (sender == null) {
            return "Sender " + senderAddress + " not found!";
        }

        // Проверка существования получателя
        User receiver = blockchainManager.getUser(receiverAddress);
        if (receiver == null) {
            return "Receiver " + receiverAddress + " not found!";
        }

        // Проверка достаточности средств у отправителя
        if (sender.getBalance() < amount) {
            return "Insufficient funds for transfer!";
        }

        // Создаем транзакцию "sender" -> "receiver" на указанную сумму
        Transaction transaction = new Transaction(senderAddress, receiverAddress, amount);

        // Обрабатываем транзакцию через метод processTransaction
        blockchainManager.processTransaction(transaction);

        // Возвращаем сообщение об успешном переводе
        return "Transfer successful. Sender's new balance: " + sender.getBalance() + ", Receiver's new balance: " + receiver.getBalance();
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

}
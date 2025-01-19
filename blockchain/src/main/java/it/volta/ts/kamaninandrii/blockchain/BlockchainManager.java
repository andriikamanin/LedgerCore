package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;
import it.volta.ts.kamaninandrii.blockchain.bean.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("singleton")
public class BlockchainManager {

    private final Blockchain blockchain;
    private final List<Transaction> pendingTransactions;
    private List<Transaction> transactions;
    private final Map<String, User> users;
    private static final int MAX_TRANSACTIONS = 5;
    private static final double MINING_REWARD = 50.0;

    public BlockchainManager() {
        this.blockchain = new Blockchain();
        this.pendingTransactions = new ArrayList<>();
        this.transactions = new ArrayList<>(); // Инициализация списка
        this.users = new HashMap<>();
        this.users.put("system", new User("system", 100000.0)); // Системный пользователь
    }
    public void createUser(String address) {
        if (users.containsKey(address)) {
            System.out.println("Пользователь с таким адресом уже существует.");
        } else {
            users.put(address, new User(address, 0.0));
            System.out.println("Создан пользователь: " + address);
        }
    }

    public User getUser(String address) {
        return users.get(address);
    }

    public Map<String, User> getAllUsers() {
        return Collections.unmodifiableMap(users); // Возвращаем неизменяемую карту
    }

    public void createBlock() {
        if (pendingTransactions.size() < MAX_TRANSACTIONS) {
            System.out.println("Недостаточно транзакций для создания блока.");
            return;
        }

        List<Transaction> transactionsToAdd = new ArrayList<>(pendingTransactions.subList(0, MAX_TRANSACTIONS));
        pendingTransactions.subList(0, MAX_TRANSACTIONS).clear();

        Block lastBlock = blockchain.getLastBlock();
        Block newBlock = new Block(
                lastBlock.getIndex() + 1,
                lastBlock.getHash(),
                transactionsToAdd
        );

        newBlock.getTransactions().add(new Transaction("system", "miner", MINING_REWARD));
        newBlock.setHash(newBlock.calculateHash());

        blockchain.addBlock(newBlock);
        updateBalances(newBlock.getTransactions());
        System.out.println("Новый блок создан: " + newBlock);
    }

    private void updateBalances(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            String sender = transaction.getSender();
            String receiver = transaction.getReceiver();
            double amount = transaction.getAmount();

            if (users.containsKey(sender)) {
                User senderUser = users.get(sender);
                senderUser.setBalance(senderUser.getBalance() - amount);
            }

            if (users.containsKey(receiver)) {
                User receiverUser = users.get(receiver);
                receiverUser.setBalance(receiverUser.getBalance() + amount);
            }
        }
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            System.out.println("Ошибка: пустая транзакция.");
            return;
        }

        pendingTransactions.add(transaction);
        System.out.println("Добавлена транзакция: " + transaction);

        if (pendingTransactions.size() >= MAX_TRANSACTIONS) {
            createBlock();
        }
    }

    public double getBalance(String user) {
        User foundUser = users.get(user);
        return foundUser != null ? foundUser.getBalance() : 0.0;
    }

    public double getBalanceWithPending(String user) {
        double balance = getBalance(user);
        for (Transaction transaction : pendingTransactions) {
            if (transaction.getSender().equals(user)) {
                balance -= transaction.getAmount();
            }
            if (transaction.getReceiver().equals(user)) {
                balance += transaction.getAmount();
            }
        }
        return balance;
    }

    public boolean isBlockchainValid() {
        return blockchain.isValid();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователи:\n");
        users.forEach((key, value) -> sb.append(key).append(": ").append(value.getBalance()).append("\n"));

        sb.append("\nНеподтвержденные транзакции:\n");
        pendingTransactions.forEach(transaction -> sb.append(transaction).append("\n"));

        sb.append("\nЦепочка блоков:\n");
        sb.append(blockchain.getBlockchainInfo());
        return sb.toString();
    }
}
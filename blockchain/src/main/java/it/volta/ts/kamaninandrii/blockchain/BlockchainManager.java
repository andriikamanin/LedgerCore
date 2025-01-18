package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;
import it.volta.ts.kamaninandrii.blockchain.bean.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Scope("singleton")
public class BlockchainManager {
    private final Blockchain blockchain;
    private final ArrayList<Transaction> pendingTransactions;
    private final HashMap<String, User> users;
    private final int MAX_TRANSACTIONS = 10; // Настраиваемый лимит транзакций в блоке

    public BlockchainManager() {
        blockchain = new Blockchain();
        pendingTransactions = new ArrayList<>();
        users = new HashMap<>();
        users.put("system", new User("system", 100000.0));
    }

    public void createUser(String address) {
        if (!users.containsKey(address)) {
            users.put(address, new User(address, 0.0));
            System.out.println("Пользователь " + address + " создан.");
        } else {
            System.out.println("Пользователь с таким адресом уже существует.");
        }
        System.out.println("Текущие пользователи: " + users.keySet());
    }

    public User getUser(String address) {
        return users.get(address);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

    public void createBlock() {
        if (pendingTransactions.isEmpty()) {
            System.out.println("Нет неподтвержденных транзакций для включения в блок.");
            return;
        }

        // Получаем последний блок из блокчейна
        Block lastBlock = blockchain.getLastBlock();

        // Создаем новый блок
        Block newBlock = new Block(
                lastBlock != null ? lastBlock.getIndex() + 1 : 0,
                lastBlock != null ? lastBlock.getHash() : "0"
        );

        // Добавляем все неподтвержденные транзакции в блок
        for (Transaction transaction : pendingTransactions) {
            newBlock.addTransaction(transaction); // Добавляем транзакции
        }

        // Очищаем пул неподтвержденных транзакций
        pendingTransactions.clear();

        // Добавляем блок в цепочку
        blockchain.addBlock(newBlock);

        System.out.println("Создан и добавлен новый блок: " + newBlock);
    }

    public void addTransactionToBlock(String senderAddress, String receiverAddress, double amount) {
        User sender = users.get(senderAddress);
        if (sender == null || sender.getBalance() < amount) {
            System.out.println("Ошибка: недостаточно средств или пользователь не найден.");
            return;
        }

        Transaction transaction = new Transaction(senderAddress, receiverAddress, amount);
        pendingTransactions.add(transaction);

        if (pendingTransactions.size() >= MAX_TRANSACTIONS) {
            createBlock();
        }
    }

    public void processTransaction(Transaction transaction) {
        String sender = transaction.getSender();
        String receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        User senderUser = users.get(sender);
        User receiverUser = users.get(receiver);

        if (senderUser == null || receiverUser == null) {
            System.out.println("Ошибка: один из пользователей не существует.");
            return;
        }

        if (senderUser.getBalance() < amount) {
            System.out.println("Ошибка: недостаточно средств.");
            return;
        }

        senderUser.subtractBalance(amount);
        receiverUser.addBalance(amount);

        System.out.println("Транзакция успешна: " + sender + " отправил " + amount + " получателю " + receiver);
    }

    public double getBalance(String user) {
        User u = users.get(user);
        return u != null ? u.getBalance() : 0.0;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Баланс пользователей:\n");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue().getBalance()).append("\n");
        }

        sb.append("\nНеподтвержденные транзакции:\n");
        for (Transaction transaction : pendingTransactions) {
            sb.append(transaction).append("\n");
        }

        sb.append("\nИнформация о блоках:\n");
        /*for (Block block : blockchain.getChain()) {
            sb.append(block).append("\n");
        }*/

        sb.append(getAllBlocks()).append("\n");

        return sb.toString();
    }

    public String getAllBlocks() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о блоках:\n");
        for (Block block : blockchain.getChain()) { // Проходим по каждому блоку в цепочке
            sb.append("Блок ").append(block.getIndex()).append(":\n");

            sb.append("Previous Hash: ").append(block.getPreviousHash()).append("\n");
            sb.append("Hash: ").append(block.getHash()).append("\n");
            sb.append("Транзакции:\n");
            for (Transaction transaction : block.getTransactions()) { // Проходим по каждой транзакции в блоке
                sb.append(transaction).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
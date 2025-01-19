package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Blockchain {

    private final List<Block> chain;
    private final List<Transaction> pendingTransactions;
    private final double miningReward; // Вознаграждение майнеру
    private static final int MAX_TRANSACTIONS = 5; // Создавать блок после 5 транзакций

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.miningReward = 50.0; // Установленное вознаграждение
        // Генезис-блок
        chain.add(new Block(0, "0", new ArrayList<>()));
    }

    // Запуск автоматического майнинга
    public void startAutoMine() {
        System.out.println("Запуск автоматического майнинга...");
        Timer timer = new Timer(true); // Фоновый процесс
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (pendingTransactions.size() >= MAX_TRANSACTIONS) {
                    mineBlock();
                }
            }
        }, 0, 1000); // Проверка каждые 1 секунду
    }

    // Создание нового блока
    public void mineBlock() {
        if (pendingTransactions.size() < MAX_TRANSACTIONS) {
            return; // Недостаточно транзакций для создания блока
        }

        Block lastBlock = getLastBlock();
        List<Transaction> transactionsToAdd = new ArrayList<>(pendingTransactions.subList(0, MAX_TRANSACTIONS));

        // Удаляем транзакции из пула
        pendingTransactions.subList(0, MAX_TRANSACTIONS).clear();

        // Добавляем транзакцию с вознаграждением
        transactionsToAdd.add(new Transaction("system", "miner", miningReward));

        // Создаем новый блок
        Block newBlock = new Block(lastBlock.getIndex() + 1, lastBlock.getHash(), transactionsToAdd);
        addBlock(newBlock);

        System.out.println("Создан новый блок: " + newBlock);
    }

    // Получить последний блок
    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    // Добавить блок в цепь
    public void addBlock(Block block) {
        chain.add(block);
    }

    // Добавить транзакцию в пул
    public void addTransaction(String sender, String receiver, double amount) {
        if (sender == null || receiver == null || amount <= 0) {
            throw new IllegalArgumentException("Неверные данные транзакции");
        }
        Transaction transaction = new Transaction(sender, receiver, amount);
        pendingTransactions.add(transaction);
        System.out.println("Транзакция добавлена: " + transaction);
    }

    // Получить информацию о всей цепочке блоков
    public String getBlockchainInfo() {
        StringBuilder info = new StringBuilder();
        for (Block block : chain) {
            info.append(block).append("\n");
        }
        return info.toString();
    }

    public List<Block> getChain() {
        return new ArrayList<>(chain); // Возвращаем копию для безопасности
    }

    public List<Transaction> getPendingTransactions() {
        return new ArrayList<>(pendingTransactions); // Возвращаем копию для безопасности
    }

    // Проверка валидности цепочки блоков
    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getHash().equals(current.calculateHash())) {
                return false; // Хеш текущего блока не совпадает
            }

            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false; // Хеш предыдущего блока не совпадает
            }
        }
        return true;
    }
}
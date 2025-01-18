package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Blockchain {

    private ArrayList<Block> chain;
    private ArrayList<Transaction> pendingTransactions;
    private double miningReward = 50.0;  // Вознаграждение майнеру
    private static final int MAX_TRANSACTIONS = 5;  // Создавать блок после 5 транзакций

    public Blockchain() {
        chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        // Создаем генезис-блок
        chain.add(new Block(0, "0", new ArrayList<>()));
    }

    // Метод для старта автоматического майнинга блоков
    public void startAutoMine() {
        System.out.println("Starting auto-mine ");

        // Периодическая проверка на наличие транзакций в пуле
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (pendingTransactions.size() >= MAX_TRANSACTIONS) {
                    mineBlock();
                }
            }
        }, 0, 1000);  // Проверка каждые 1 секунду
    }

    // Метод для создания блока с транзакциями
    public void mineBlock() {
        if (pendingTransactions.size() < MAX_TRANSACTIONS) {
            return; // Если недостаточно транзакций, блок не создаем
        }

        Block lastBlock = getLastBlock();
        ArrayList<Transaction> transactionsToAdd = new ArrayList<>();

        // Берем первые MAX_TRANSACTIONS транзакций из пула
        for (int i = 0; i < MAX_TRANSACTIONS; i++) {
            transactionsToAdd.add(pendingTransactions.remove(0));
        }

        // Добавляем транзакцию майнинга (вознаграждение)
        transactionsToAdd.add(new Transaction("system", "miner", miningReward));

        // Создаем новый блок с этими транзакциями
        Block newBlock = new Block(lastBlock.getIndex() + 1, lastBlock.getHash(), transactionsToAdd);

        // Добавляем новый блок в цепь
        addBlock(newBlock);

        System.out.println("Новый блок создан: " + newBlock);
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
        Transaction transaction = new Transaction(sender, receiver, amount);
        pendingTransactions.add(transaction);
        System.out.println("Транзакция добавлена в пул: " + transaction);
    }

    // Информация о блоках
    public String getBlockchainInfo() {
        StringBuilder blockchainInfo = new StringBuilder();
        for (Block block : chain) {
            blockchainInfo.append(block).append("\n");
        }
        return blockchainInfo.toString();
    }

    public ArrayList<Block> getChain() {
        return chain;
    }

    public ArrayList<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    // Проверка валидности цепочки блоков
    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // Проверка текущего хеша
            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }

            // Проверка предыдущего хеша
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }
}
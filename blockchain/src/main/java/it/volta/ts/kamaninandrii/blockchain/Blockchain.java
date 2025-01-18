package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;

import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> chain;
    private ArrayList<Transaction> pendingTransactions;

    public Blockchain() {
        chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        // Создаем генезис-блок (самый первый блок в цепи)
        chain.add(new Block(0, "0"));
    }

    // Получение последнего блока
    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    // Добавление блока в цепь
    public void addBlock(Block block) {
        // Добавляем все неподтвержденные транзакции в блок
        for (Transaction transaction : pendingTransactions) {
            block.addTransaction(transaction);
        }

        // Очищаем пул неподтвержденных транзакций
        pendingTransactions.clear();

        // Добавляем блок в цепочку
        chain.add(block);
    }

    // Добавление транзакции в пул
    public void addTransaction(String sender, String receiver, double amount) {
        Transaction transaction = new Transaction(sender, receiver, amount);
        pendingTransactions.add(transaction);
        System.out.println("Транзакция добавлена в пул: " + transaction);
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

    // Вывод цепочки блоков в строковом представлении
    @Override
    public String toString() {
        return chain.toString();
    }

    public ArrayList<Block> getChain() {
        return chain;
    }
}
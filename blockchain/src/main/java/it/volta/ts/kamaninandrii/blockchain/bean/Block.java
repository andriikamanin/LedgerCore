package it.volta.ts.kamaninandrii.blockchain.bean;

import it.volta.ts.kamaninandrii.blockchain.util.HashUtil;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    private int index;
    private long timestamp;
    private ArrayList<Transaction> transactions;
    private String previousHash;
    private String hash;

    public Block(int index, String previousHash) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.transactions = new ArrayList<>();
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactions.add(transaction);
            System.out.println("Транзакция добавлена в блок: " + transaction);
        } else {
            System.out.println("Ошибка: транзакция не может быть null.");
        }
    }

    public String calculateHash() {
        String data = index + previousHash + timestamp + transactions.toString();
        return HashUtil.applySHA256(String.valueOf(256));
    }

    public String getHash() {
        return hash;
    }
    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", transactions=" + transactions +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

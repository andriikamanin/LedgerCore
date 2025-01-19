package it.volta.ts.kamaninandrii.blockchain.bean;

import it.volta.ts.kamaninandrii.blockchain.util.HashUtil;

import java.util.ArrayList;
import java.util.Date;


import java.util.Collections;

import java.util.List;

/**
 * Represents a block in the blockchain.
 */
public class Block {
    private final int index;
    private final long timestamp;
    private final List<Transaction> transactions;
    private final String previousHash;
    private String hash;

    /**
     * Constructor for Block.
     *
     * @param index        the index of the block in the chain
     * @param previousHash the hash of the previous block
     * @param transactions the list of transactions to include in the block
     */
    public Block(int index, String previousHash, List<Transaction> transactions) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.transactions = transactions != null ? new ArrayList<>(transactions) : new ArrayList<>();
        this.hash = calculateHash();
    }

    /**
     * Adds a transaction to the block.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
        transactions.add(transaction);
    }

    /**
     * Gets an unmodifiable list of transactions in the block.
     *
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Возвращаем копию, а не исходный список
    }

    /**
     * Calculates the hash of the block based on its data.
     *
     * @return the calculated hash
     */
    public String calculateHash() {
        String data = index + previousHash + timestamp + transactions.toString();
        return HashUtil.applySHA256(data);
    }

    /**
     * Gets the hash of the block.
     *
     * @return the hash of the block
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash of the block.
     *
     * @param hash the new hash value
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Gets the index of the block.
     *
     * @return the index of the block
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the hash of the previous block.
     *
     * @return the hash of the previous block
     */
    public String getPreviousHash() {
        return previousHash;
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
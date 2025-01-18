package it.volta.ts.kamaninandrii.blockchain.bean;

import java.util.Date;

public class Transaction {
    private String sender;
    private String receiver;
    private double amount;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    // Геттеры и сеттеры
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{sender='" + sender + "', receiver='" + receiver + "', amount=" + amount + "}";
    }
}
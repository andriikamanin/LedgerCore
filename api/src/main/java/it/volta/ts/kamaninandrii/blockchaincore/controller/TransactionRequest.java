package it.volta.ts.kamaninandrii.blockchaincore.controller;

public class TransactionRequest {

    private String sender;
    private String receiver;
    private double amount;

    // Конструкторы
    public TransactionRequest() {}

    public TransactionRequest(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    // Геттеры и сеттеры
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
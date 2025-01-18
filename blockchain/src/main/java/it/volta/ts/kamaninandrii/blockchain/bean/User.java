package it.volta.ts.kamaninandrii.blockchain.bean;

public class User {
    private String address;
    private double balance;

    public User(String address, double initialBalance) {
        this.address = address;
        this.balance = initialBalance;
    }

    public String getAddress() {
        return address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            System.out.println("Error: Deposit amount must be positive.");
        }
    }

    public void subtractBalance(double amount) {
        System.out.println("Trying to subtract " + amount + " from balance: " + this.balance);
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Successfully subtracted " + amount + ". New balance: " + this.balance);
        } else {
            System.out.println("Error: Insufficient balance or invalid amount.");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
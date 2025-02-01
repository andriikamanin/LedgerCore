package it.volta.ts.kamaninandrii.blockchaincore.requests;

public class DepositRequest {

    private String user;
    private double amount;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
package it.volta.ts.kamaninandrii.blockchain.bean;

/**
 * Represents a user in the blockchain system.
 */
public class User {
    private final String address;
    private double balance;

    /**
     * Constructor for User.
     *
     * @param address       the user's address
     * @param initialBalance the initial balance of the user
     */
    public User(String address, double initialBalance) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.address = address;
        this.balance = initialBalance;
    }

    /**
     * Gets the user's address.
     *
     * @return the user's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the user's balance.
     *
     * @return the user's balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the user's balance.
     *
     * @param balance the new balance
     */
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    /**
     * Adds an amount to the user's balance.
     *
     * @param amount the amount to add
     */
    public void addBalance(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    /**
     * Subtracts an amount from the user's balance.
     *
     * @param amount the amount to subtract
     */
    public void subtractBalance(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to subtract must be positive.");
        }
        if (this.balance < amount) {
            throw new IllegalStateException("Insufficient balance.");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
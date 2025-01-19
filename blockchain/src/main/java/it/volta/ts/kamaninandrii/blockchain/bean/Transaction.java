package it.volta.ts.kamaninandrii.blockchain.bean;

/**
 * Represents a transaction in the blockchain.
 */
public class Transaction {
    private final String sender;
    private final String receiver;
    private final double amount;

    /**
     * Constructor for Transaction.
     *
     * @param sender   the sender's address
     * @param receiver the receiver's address
     * @param amount   the amount being transferred
     */
    public Transaction(String sender, String receiver, double amount) {
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    /**
     * Gets the sender's address.
     *
     * @return the sender's address
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the receiver's address.
     *
     * @return the receiver's address
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the transaction amount.
     *
     * @return the transaction amount
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                '}';
    }
}

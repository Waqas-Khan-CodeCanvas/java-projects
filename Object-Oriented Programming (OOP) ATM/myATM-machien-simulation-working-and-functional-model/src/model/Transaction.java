package model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private TransactionType type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount, double balanceAfter) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Transaction[ID: %s, Type: %s, Amount: $%.2f, Balance: $%.2f, Time: %s]",
                transactionId, type, amount, balanceAfter, timestamp);
    }
}
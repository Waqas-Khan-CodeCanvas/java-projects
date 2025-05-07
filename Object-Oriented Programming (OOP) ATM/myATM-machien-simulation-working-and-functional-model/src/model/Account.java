package model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountName;
    private double balance;
    private String accountPurpose;
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountName, double initialBalance, String accountPurpose) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = initialBalance;
        this.accountPurpose = accountPurpose;
        this.transactions = new ArrayList<>();
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountPurpose() {
        return accountPurpose;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
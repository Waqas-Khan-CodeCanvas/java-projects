package manager;

import interfaces.IAccountOperations;
import model.Account;
import model.Transaction;
import model.TransactionType;

public class AccountManager implements IAccountOperations {
    @Override
    public boolean deposit(Account account, double amount) {
        if (amount <= 0) {
            return false;
        }
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, newBalance);
        addTransaction(account, transaction);
        return true;
    }

    @Override
    public boolean withdraw(Account account, double amount) {
        if (amount <= 0 || amount > account.getBalance()) {
            return false;
        }
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, newBalance);
        addTransaction(account, transaction);
        return true;
    }

    @Override
    public double getBalance(Account account) {
        return account.getBalance();
    }

    @Override
    public void addTransaction(Account account, Transaction transaction) {
        account.addTransaction(transaction);
    }
}
package interfaces;

import model.Account;
import model.Transaction;

public interface IAccountOperations {
    boolean deposit(Account account, double amount);

    boolean withdraw(Account account, double amount);

    double getBalance(Account account);

    void addTransaction(Account account, Transaction transaction);
}
package interfaces;

import model.User;
import model.Account;

public interface IDatabaseOperations {
    boolean saveUser(User user);

    User loadUser(String username);

    boolean updateUser(User user);

    boolean deleteUser(String username);

    boolean saveAccount(Account account);

    Account loadAccount(String accountNumber);

    boolean updateAccount(Account account);
}
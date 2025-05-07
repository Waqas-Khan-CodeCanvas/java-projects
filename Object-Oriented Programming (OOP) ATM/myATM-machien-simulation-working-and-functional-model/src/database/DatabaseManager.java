package database;

import interfaces.IDatabaseOperations;
import model.User;
import model.Account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager implements IDatabaseOperations {
    private static final String BASE_DIR = "src/Accounts_db";
    private Map<String, User> userCache;
    private Map<String, Account> accountCache;

    public DatabaseManager() {
        this.userCache = new HashMap<>();
        this.accountCache = new HashMap<>();
        createBaseDirectory();
    }

    private void createBaseDirectory() {
        File baseDir = new File(BASE_DIR);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
    }

    @Override
    public boolean saveUser(User user) {
        try {
            File userDir = new File(BASE_DIR + "/" + user.getUsername());
            if (!userDir.exists()) {
                userDir.mkdir();
            }

            // Save user info
            FileWriter userInfoFile = new FileWriter(userDir + "/" + user.getUsername() + "_info.txt");
            userInfoFile.write(String.format("%s:%s:%s:%s:%s:%s:%s:%s",
                    user.getFullName(),
                    user.getAge(),
                    user.getContact(),
                    user.getId(),
                    user.getEmail(),
                    user.getHomeAddress(),
                    user.getAccount().getAccountPurpose(),
                    user.getCity()));
            userInfoFile.close();

            // Save security info
            FileWriter securityFile = new FileWriter(userDir + "/" + user.getUsername() + "_Security.txt");
            securityFile.write(String.format("%s:%s:%.2f",
                    user.getUsername(),
                    user.getPassword(),
                    user.getAccount().getBalance()));
            securityFile.close();

            userCache.put(user.getUsername(), user);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User loadUser(String username) {
        if (userCache.containsKey(username)) {
            return userCache.get(username);
        }

        try {
            File userDir = new File(BASE_DIR + "/" + username);
            if (!userDir.exists()) {
                return null;
            }

            // Load user info
            File infoFile = new File(userDir + "/" + username + "_info.txt");
            File securityFile = new File(userDir + "/" + username + "_Security.txt");

            if (!infoFile.exists() || !securityFile.exists()) {
                return null;
            }

            // Read security info
            BufferedReader securityReader = new BufferedReader(new FileReader(securityFile));
            String[] securityInfo = securityReader.readLine().split(":");
            securityReader.close();

            // Read user info
            BufferedReader infoReader = new BufferedReader(new FileReader(infoFile));
            String[] userInfo = infoReader.readLine().split(":");
            infoReader.close();

            // Create User object
            User user = new User(
                    securityInfo[0],
                    securityInfo[1],
                    userInfo[0],
                    userInfo[1],
                    userInfo[2],
                    userInfo[3],
                    userInfo[4],
                    userInfo[7],
                    userInfo[5]);

            // Create Account object
            Account account = new Account(
                    userInfo[3],
                    userInfo[0],
                    Double.parseDouble(securityInfo[2]),
                    userInfo[6]);

            user.setAccount(account);
            userCache.put(username, user);
            return user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        return saveUser(user);
    }

    @Override
    public boolean deleteUser(String username) {
        File userDir = new File(BASE_DIR + "/" + username);
        if (userDir.exists()) {
            File[] files = userDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            userDir.delete();
            userCache.remove(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean saveAccount(Account account) {
        accountCache.put(account.getAccountNumber(), account);
        return true;
    }

    @Override
    public Account loadAccount(String accountNumber) {
        return accountCache.get(accountNumber);
    }

    @Override
    public boolean updateAccount(Account account) {
        return saveAccount(account);
    }
}
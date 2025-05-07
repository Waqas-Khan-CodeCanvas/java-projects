import database.DatabaseManager;
import interfaces.IDatabaseOperations;
import interfaces.IAccountOperations;
import manager.AccountManager;
import model.User;
import model.Account;
import model.TransactionType;

import java.util.Scanner;

public class ATM {
    private final IDatabaseOperations databaseManager;
    private final IAccountOperations accountManager;
    private User currentUser;
    private final Scanner scanner;

    public ATM() {
        this.databaseManager = new DatabaseManager();
        this.accountManager = new AccountManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayWelcomeMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayWelcomeMenu() {
        System.out.println("\n********** WELCOME TO ATM PROGRAM ***********");
        System.out.println("\t1. Login Account");
        System.out.println("\t2. Create Account");
        System.out.println("\t3. Exit");
        System.out.print("Please Enter Your Option: ");
    }

    private void login() {
        System.out.print("\tEnter username: ");
        String username = scanner.nextLine().toLowerCase().trim();
        System.out.print("\tEnter password: ");
        String password = scanner.nextLine().toLowerCase().trim();

        currentUser = databaseManager.loadUser(username);
        if (currentUser != null && currentUser.getPassword().equals(password)) {
            System.out.println("\nLogin successful! Welcome back, " + currentUser.getFullName());
            showMainMenu();
        } else {
            System.out.println("\nError: Authentication failed. Please check your username and password.");
        }
    }

    private void createAccount() {
        System.out.println("\nPlease provide all the information needed for an account");

        System.out.print("Enter your Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter Your Account Name: ");
        String accountName = scanner.nextLine();

        System.out.print("Enter your Age: ");
        String age = scanner.nextLine();

        System.out.print("Enter your Contact: ");
        String contact = scanner.nextLine();

        System.out.print("Enter your ID Card No: ");
        String id = scanner.nextLine();

        System.out.print("Create your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Your City Name: ");
        String city = scanner.nextLine();

        System.out.print("Enter your Email Address: ");
        String email = scanner.nextLine();

        System.out.print("Enter your Home Address: ");
        String homeAddress = scanner.nextLine();

        System.out.print("Enter your purpose of your account: ");
        String accountPurpose = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Create account and user objects
        Account account = new Account(id, accountName, initialBalance, accountPurpose);
        User user = new User(accountName.toLowerCase(), password, fullName, age, contact, id, email, city, homeAddress);
        user.setAccount(account);

        if (databaseManager.saveUser(user)) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Error: Failed to create account. Please try again.");
        }
    }

    private void showMainMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n********** ATM Menu *********");
            System.out.println("\t1. Check Balance");
            System.out.println("\t2. Deposit Money");
            System.out.println("\t3. Withdraw Money");
            System.out.println("\t4. Show Profile");
            System.out.println("\t5. Reset Password");
            System.out.println("\t6. Transaction History");
            System.out.println("\t7. Logout");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    showProfile();
                    break;
                case 5:
                    resetPassword();
                    break;
                case 6:
                    showTransactionHistory();
                    break;
                case 7:
                    loggedIn = false;
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void checkBalance() {
        double balance = accountManager.getBalance(currentUser.getAccount());
        System.out.println("\n!============ CHECKING BALANCE ==============!");
        System.out.println("Account User Name: " + currentUser.getFullName());
        System.out.println("Account Number: " + currentUser.getAccount().getAccountNumber());
        System.out.println("Current Balance: $" + balance);
    }

    private void depositMoney() {
        System.out.print("Enter deposit amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (accountManager.deposit(currentUser.getAccount(), amount)) {
            System.out.println("Deposit successful!");
            databaseManager.updateUser(currentUser);
        } else {
            System.out.println("Error: Invalid deposit amount.");
        }
    }

    private void withdrawMoney() {
        System.out.print("Enter withdrawal amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (accountManager.withdraw(currentUser.getAccount(), amount)) {
            System.out.println("Withdrawal successful!");
            databaseManager.updateUser(currentUser);
        } else {
            System.out.println("Error: Invalid withdrawal amount or insufficient funds.");
        }
    }

    private void showProfile() {
        System.out.println("\n********************************* ATM ACCOUNT ************************************");
        System.out.println("***************************** USER PROFILE DETAILED ****************************** \n");
        System.out.println("ACCOUNT USER NAME: " + currentUser.getFullName().toUpperCase());
        System.out.println("ACCOUNT TYPE: ATM");
        System.out.println("ACCOUNT USER AGE: " + currentUser.getAge() + " YEARS");
        System.out.println("ACCOUNT ID: " + currentUser.getAccount().getAccountNumber());
        System.out.println("ACCOUNT USER CONTACT: " + currentUser.getContact());
        System.out.println("ACCOUNT USER CNIC: " + currentUser.getId());
        System.out.println("ACCOUNT USER CITY: " + currentUser.getCity());
        System.out.println("ACCOUNT USER EMAIL ADDRESS: " + currentUser.getEmail());
        System.out.println("ACCOUNT CREATION PURPOSE: " + currentUser.getAccount().getAccountPurpose().toUpperCase());
        System.out.println("ACCOUNT USER HOME ADDRESS: " + currentUser.getHomeAddress().toUpperCase());
        System.out.println("ACCOUNT CURRENT BALANCE: $" + currentUser.getAccount().getBalance());
    }

    private void resetPassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        if (databaseManager.updateUser(currentUser)) {
            System.out.println("Password has been reset successfully.");
        } else {
            System.out.println("Error: Failed to reset password.");
        }
    }

    private void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (model.Transaction transaction : currentUser.getAccount().getTransactions()) {
            System.out.println(transaction);
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
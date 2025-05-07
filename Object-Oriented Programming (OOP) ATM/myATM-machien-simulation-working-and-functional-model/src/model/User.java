package model;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String age;
    private String contact;
    private String id;
    private String email;
    private String city;
    private String homeAddress;
    private Account account;

    public User(String username, String password, String fullName, String age, String contact,
            String id, String email, String city, String homeAddress) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.contact = contact;
        this.id = id;
        this.email = email;
        this.city = city;
        this.homeAddress = homeAddress;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
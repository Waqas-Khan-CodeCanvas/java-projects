import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class myATM {
    private static String storedName;
    private static String storedAccountName;
    private static String storedPassword;
    private static String storedCity;
    private static String storedContact;
    private static String storedEmail;
    private static String storedAge;
    private static String storedHomeAddress;
    private static String storedId;
    private static String storedAccountPurpose;
    private static int storedBalance;

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        boolean showDisplay=false;
        while (!showDisplay){
            handelUsers();
            int userInput=scanner.nextInt();
            //if(userInput==1) loginAccount(); else createAccount();
            showDisplay = userInput == 1 ? loginAccount() : createAccount();
            if (!showDisplay) {
                System.out.println("Authentication failed. Please try again.");
            }
        }

        int userChoice;
        do {
            displayMenu();
            updateUserAccount();
            userChoice=scanner.nextInt();
            switch (userChoice){
                case 1:
                    checkBalance();
                    break;
                case 2:
                    System.out.print("Enter you deposit amount here :");
                    int depositAmount=scanner.nextInt();
                    deopsitMoney(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter you withdraw amount here :");
                    int withdrawAmount=scanner.nextInt();
                    withdrawMoney(withdrawAmount);
                    break;
                case 4:
                    showProfile();
                    break;
                case 5:
                    restPassword();
                    break;
                case 6:
                    transactionsRecord();
                    break;
            }

        }while (userChoice!=7);
    }
    public static void handelUsers(){
        System.out.println("\n********** WELCOME TO ATM PROGRAME ***********");
        System.out.println("\t 1 : Login Account ");
        System.out.println("\t 2 : Create Account ");
//        System.out.println("\t 3 : Exite ATM.. ");
        System.out.print("Please Enter Your Option Here : ");

    }
    public static boolean createAccount(){
        System.out.println("Please provide all the information needed for an account");
        Scanner scanner=new Scanner(System.in);
        System.out.print("\t Enter your Name : ");
        String userName=scanner.nextLine();
        System.out.print("\t Enter Your Account Name : ");
        String userAccountName=scanner.nextLine();
        System.out.print("\t Enter your Age : ");
        String userAge=scanner.nextLine();
        System.out.print("\t Enter your Contact  : ");
        String userContact=scanner.nextLine();
        System.out.print("\t Enter your ID Card No : ");
        String userId=scanner.nextLine();
        System.out.print("\t Create  your password : ");
        String userPass=scanner.nextLine();
        System.out.print("\t Enter Your City Name : ");
        String userCity=scanner.nextLine();
        System.out.print("\t Enter your Email Address : ");
        String userEmail=scanner.nextLine();
        System.out.print("\t Enter your Home Address : ");
        String userHomeAdress=scanner.nextLine();
        System.out.print("\t Enter your purpose of your account: ");
        String accountPurpose=scanner.nextLine();
        System.out.print("\t Deposit money to your to activate the account : ");
        String activationBalance=scanner.nextLine();

        File userFolder=new File("Accounts_db/"+userName);
        userFolder.mkdir();

        boolean displayAccount=false;
        try {
            FileWriter userInfoFile=new FileWriter("Accounts_db/"+userName+"/"+userName+"_info.txt");
            userInfoFile.write(userAccountName + ":" + userAge + ":" + userContact + ":" + userId + ":"+ userEmail + ":" + userHomeAdress + ":" + accountPurpose+":"+userCity);
            FileWriter userSecurity=new FileWriter("Accounts_db/"+userName+"/"+userName+"_Security.txt");
            userSecurity.write(userName + ":" + userPass + ":" + activationBalance);
            userInfoFile.close();
            userSecurity.close();
            displayAccount=true;
            System.out.println("Account created successfully!");

        } catch (IOException e) {
            System.out.println("Error : Please Try again we are facing some internal issues while creating your account.");

        }
        return displayAccount;
    }
    public static boolean loginAccount(){
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine().toLowerCase().trim();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine().toLowerCase().trim();

        //Authentication
        File securityFile=new File("Accounts_db/"+inputUsername+"/"+inputUsername+"_Security.txt");
        boolean displayAccount=false;
        try {
            Scanner fileScanner=new Scanner(securityFile);
            if (fileScanner.hasNextLine()){
                String[] userSecurity=fileScanner.nextLine().split(":");
                storedName=userSecurity[0];
                storedPassword=userSecurity[1];
                storedBalance=Integer.parseInt(userSecurity[2]);

                //user Authentication
                if (storedName.equals(userSecurity[0]) && storedPassword.equals(inputPassword)) {
                    System.out.println("\nLogin successful! Welcome Back to the ATM. "+storedName);
                    displayAccount=true;
                } else {
                    System.out.println("\n Error : Authentication failed. Please check your username and password.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("\n Error : Sorry User Account Not Found please Create Account.");
        }

        //load the user Info file data to the varailbles
        File infoFile = new File("Accounts_db/"+inputUsername+"/"+inputUsername+"_info.txt");
        try {
            Scanner fileScanner = new Scanner(infoFile);
            if (fileScanner.hasNextLine()){
                String[] userInfo =fileScanner.nextLine().split(":");
                storedAccountName= userInfo[0];
                storedAge= userInfo[1];
                storedContact= userInfo[2];
                storedId= userInfo[3];
                storedEmail= userInfo[4];
                storedHomeAddress= userInfo[5];
                storedAccountPurpose= userInfo[6];
                storedCity= userInfo[7];
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nError : Sorry User information not found!. please try again ");
        }
        return displayAccount;
    }
    public static void displayMenu(){
        System.out.println("\n********** ATM Menu is displayed *********");
        System.out.println("\t1. Check Balance");
        System.out.println("\t2. Deposit Money");
        System.out.println("\t3. Withdraw Money");
        System.out.println("\t4. Show Profile");
        System.out.println("\t5. Rest Password");
        System.out.println("\t6. Transaction History");
        System.out.println("\t7 Exit");
        System.out.print("Please select an option: ");
    }
    public static void checkBalance(){
        System.out.println("\n!============ CHECKING BALANCE ==============!");
        System.out.println("\tAccount User Name is : "+storedName);
        System.out.println("\tAccount Number is : "+storedId);
        System.out.println("\tAccount Current Balance is : "+storedBalance);
    }
    public static void deopsitMoney(int depositAmount){
        System.out.println("\n!============ USER DEPOSIT MONEY ==============!");
            int initialAmount=storedBalance;
        if (depositAmount>0){
            storedBalance+=depositAmount;
            System.out.println("You have deposit $"+depositAmount+" in your account ");
            System.out.println("Now your new balance is  $"+storedBalance);
        }
        try {
            FileWriter depositFile=new FileWriter("Accounts_db/"+storedName+"/"+storedName+"_deposit.txt",true);
            depositFile.write(initialAmount+":"+depositAmount+":");
            depositFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void withdrawMoney(int withdrawAmount){
        System.out.println("\n!============ USER WITHDRAW MONEY ==============!");
        if (withdrawAmount>0){
            storedBalance-=withdrawAmount;
            System.out.println("You have Withdraw $"+withdrawAmount+" from your account ");
            System.out.println("Now your new balance is  $"+storedBalance);
        }
        try {
            FileWriter depositFile=new FileWriter("Accounts_db/"+storedName+"/"+storedName+"_withdraw.txt",true);
            depositFile.write(withdrawAmount+":"+storedBalance+"\n");
            depositFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showProfile(){
        System.out.println("\n********************************* ATM ACOUNT ************************************");
        System.out.println("***************************** USER PROFILE DETAILED  ****************************** \n");
        System.out.println("\tACOUNT USER NAME : "+storedName.toUpperCase()+"\t\t\t\t\t\t\t ACOUNT TYPE :  ATM " );
        System.out.println("\tACOUNT USER AGE: "+storedAge+" YEARS"+"\t\t\t\t\t\t\t ACOUNT ID  :  1141 " );
        System.out.println("\tACOUNT USER CONTACT : " +storedContact);
        System.out.println("\tACOUTN USER CNIC : "+storedId);
        System.out.println("\tACOUTN USER City : "+storedCity);
        System.out.println("\tACOUNT USER EMAIL ADDRESS : "+storedEmail);
        System.out.println("\tACOUNT CREATION PURPOSE : "+storedAccountPurpose.toUpperCase());
        System.out.println("\tACOUTN USER HOME ADDRESS : "+storedHomeAddress.toUpperCase());
        System.out.println("\n!***************************** USER ACOUNT DETAILS  ****************************!\n");
        System.out.println("\tACOUNT NAME : "+storedAccountName.toUpperCase()+"\t\t\t\t\t\tACOUNT TYPE :  ATM " );
        System.out.println("\tACOUTN ID: "+storedId+"1141\t\t\t\tACOUNT BRANCH ID : 34567890-78" );
        System.out.println("\tACOUNT BRANCH ADDRESS: "+storedCity.toUpperCase());
        System.out.println("\tACOUNT CURRENT BALANCE : $"+storedBalance);
        System.out.println("\n**********************************  THANK YOU  *************************************");
        System.out.println("************************************************************************************");

    }
    public static void restPassword(){
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        storedPassword = newPassword;
        System.out.println("Password has been reset successfully.");
        updateUserAccount();
    }
    private static void updateUserAccount() {
        try {
            FileWriter updateFile = new FileWriter("Accounts_db/"+storedName+"/"+storedName+"_Security.txt");
            updateFile.write(storedName + ":" + storedPassword+ ":" +storedBalance);
            updateFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the credentials.");
        }
    }
    private static void transactionsRecord(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("\n!============ WITHDRAW AND DEPOSITE HISTORY ==============!");
        System.out.println("\t1: Show My Deposit History.");
        System.out.println("\t2: Show My Withdraw History.");
        System.out.print("Please Enter you choice here : ");
        int sc=scanner.nextInt();
        String userChoic =sc==1?"_deposit.txt":"_withdraw.txt";
        String transactionName =sc==1?"Deposite":"Withdraw";
        System.out.println("your all "+ transactionName);

        File historyFile=new File("Accounts_db/"+storedName+"/"+storedName+userChoic);
        try {
            Scanner readHistoryFile=new Scanner(historyFile);
            if (readHistoryFile.hasNextLine()){
                String[] fileReport=readHistoryFile.nextLine().split(":");
                System.out.println("\n********************************* ATM ACOUNT ************************************");
                System.out.println("*****************************  YOUR "+transactionName.toUpperCase()+" HISTORY   ****************************** \n");
                System.out.println("\tACOUNT USER NAME : "+storedName.toUpperCase()+"\t\t\t\t\t\t\t ACOUNT TYPE :  ATM " );
                System.out.println("\tACOUNT ID: "+storedId+"\t\t\t\t\t\t\t\t ACOUNT ID  :  1141 " );
                System.out.println(transactionName.toUpperCase()+" DETAILS " );
                System.out.println("INITIAL AMOUNT\t DEPOSITAMOUNT\t  TOTAL\t\tDATE  TIME \t    TRANSACTION ID");
                for (int i = 0; i < fileReport.length-2; i+=2) {
                    System.out.println("");
                    System.out.println(fileReport[i]+" "+fileReport[i+1]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

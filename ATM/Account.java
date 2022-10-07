package ATM;

public class Account {

    double balance;
    String firstName;
    String lastName;
    String password;

    public Account(String firstName, String lastName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;

        this.balance = 0.0;
    }
    
}

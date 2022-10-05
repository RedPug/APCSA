package ATM;

public class Account {

    double balance;
    String firstName;
    String lastName;

    public Account(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;

        this.balance = 0.0;
    }
    
}

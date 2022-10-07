package ATM;

import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    static Scanner sc = new Scanner(System.in);

    static HashMap<Integer, Account> map = new HashMap<Integer, Account>(5);
    public static void main(String[] args) throws InterruptedException{

        while(true){
            System.out.println("If you would like to withdraw, press 1. If you would like to deposit, press 2. If you would like to create an account, press 3");

            int input = Integer.parseInt(sc.nextLine());

            switch(input){
                case 1: //withdraw
                    Account account = getAccount();

                    boolean login = logInAccount(account);

                    if(!login){break;}

                    System.out.println("Please enter the quantity you would like to windraw.");
                    double q = -1;
                    while(q >= 0){
                        if(q > account.balance){
                            System.out.print("Your balance is $"+account.balance+". ");
                        }
                        System.out.print("Please enter a valid quanity.");
                        q = Double.parseDouble(sc.nextLine());
                    }
                    System.out.println("You have withdrawn: $"+q);
                    account.balance -= q;
                    break;
                case 2: //depsoit
                    break;
                case 3: //create account
                    System.out.println("Begin account making process:");

                    System.out.println("Please enter your first name:");
                    String firstName = sc.nextLine();

                    System.out.println("Please enter your Last name:");
                    String lastName = sc.nextLine();

                    System.out.println("Please enter a password:");
                    String pass = sc.nextLine();

                    int key = -1;
                    while(map.containsKey(key) || key == -1){
                        key = (int)(Math.random()*1000);
                    }
                    map.put(key, new Account(firstName, lastName, pass));

                    System.out.println("Your account pin is: "+key);
                    break;
            }
        }
    }

    public static Account getAccount(){
        System.out.println("Please enter your account pin.");
        int pin = -1;
        while(!map.containsKey(pin)){
            System.out.println("That pin could not be found. Please try again.");
            String scan = sc.nextLine();
            if(scan.equals("exit")){return null;}
            try {
                pin = Integer.parseInt(scan);
            } catch (Exception e) {
                continue;
            }
        }
        return map.get(pin);
    }

    public static boolean logInAccount(Account account){
        System.out.print("Please enter your password:");
        String pass = "";
        while(!pass.equals(account.password)){
            System.out.println("Sorry, that was incorrect. Please try again. To cancel, type \"exit\"");
            pass = sc.nextLine();
            if(pass.equals("exit")){
                return false;
            }
        }
        return true;
    }
}

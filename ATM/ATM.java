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
                    do{
                        Account account = getAccount();
                        boolean login = logInAccount(account);
    
                        if(!login){break;}
    
                        System.out.println("Please enter the quantity you would like to windraw.");
                        double q = Double.parseDouble(sc.nextLine());;
                        while(q < 0 || q > account.balance){
                            if(q > account.balance){
                                System.out.print("Your balance is $"+account.balance+". ");
                            }
                            System.out.print("Please enter a valid quanity.");
                            q = Double.parseDouble(sc.nextLine());
                        }
                        System.out.println("You have withdrawn: $"+q);
                        account.balance -= q;
                    } while(false);
                    break;
                case 2: //depsoit
                    do{
                        Account account = getAccount();
                        boolean login = logInAccount(account);
                        
                        if(!login){break;}
    
                        System.out.println("Please enter the quantity you would like to deposit.");
                        double q = Double.parseDouble(sc.nextLine());;
                        while(q < 0){
                            System.out.print("Please enter a valid quanity.");
                            q = Double.parseDouble(sc.nextLine());
                        }
                        System.out.println("You have deposited: $"+q);
                        account.balance += q;
                    } while (false);

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
        while(true){
            String scan = sc.nextLine();
            if(scan.equals("exit")){return null;}
            try {
                pin = Integer.parseInt(scan);
            } catch (Exception e) {
                continue;
            }
            if(map.containsKey(pin)){break;}
            System.out.println("That pin could not be found. Please try again.");
        }
        return map.get(pin);
    }

    public static boolean logInAccount(Account account){
        System.out.print("Please enter your password:");
        String pass = sc.nextLine();
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

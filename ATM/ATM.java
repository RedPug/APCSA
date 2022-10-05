package ATM;

import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        HashMap<Integer, Account> map = new HashMap<Integer, Account>(5);

        while(true){
            

            System.out.println("If you would like to withdraw, press 1. If you would like to deposit, press 2. If you would like to create an account, press 3");

            int input = sc.nextInt();

            switch(input){
                case 1:
                    System.out.println("Please enter your account pin.");
                    int pin = -1;
                    while(!map.containsKey(pin)){
                        System.out.println("That pin could not be found. Please try again.");
                        pin = sc.nextInt();
                    }
                    

                    break;
                case 2:
                    break;
                case 3:
                    System.out.print("Please enter your first name:");
                    String firstName = sc.nextLine();
                    String lastName = sc.nextLine();

                    int key = -1;
                    while(!map.containsKey(key)){
                        key = (int)(Math.random()*1000);
                    }
                    map.put(key, new Account(firstName, lastName));
                    break;
            }


        }
    }
}

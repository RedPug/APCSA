package choose_your_path;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException{

        while(true){
            System.out.println("restarting...");
            Thread.sleep(1000);
            System.out.println("You want to sleep. How tired are you?");
            int ch0 = getChoice(new String[]{"not","mildly","very"});
    
            if(ch0 == 1){
                System.out.println("You contemplate sleep");
                int ch1 = getChoice(new String[]{"sleep","stay up later"});
                if(ch1 == 1){
                    continue;
                }else if(ch1 == 2){
                    System.out.println("You end up staying up till 5 AM, 3 minutes before your wake up alarm. You died.");
                }
            }else if(ch0 == 2){
                System.out.println("You have a LOT of homework due tomorrow during 5th period. This means you have the WHOLE day to get it done.");
                int ch1 = getChoice(new String[]{
                    "Do it now and suffer in the morning",
                    "Do it during school but suffer now because you're stressing out about it being due and not being done."
                });

                if(ch1 == 1){
                    System.out.println("Congratz. You did your homework! Now, ");
                }else if(ch1 == 2){
                    System.out.println("You can't stop thinking about all the homework you haven't done and need to do. You died.");
                }
            }else {
                System.out.println("What was that sound!!??");
                int ch1 = getChoice(new String[]{"Do nothing","Investiage"});
                if(ch1 == 1){
                    System.out.println("Is there a monster under my bed?");
                    int ch2 = getChoice(new String[]{"Sleep and take the risk","Investigate"});
                    if(ch2 == 1){
                        int rand = (int)(Math.random()*2);
                        if(rand == 0){
                            System.out.println("The monster brutally and slowly tears your flesh off. You have died.");
                        }else{
                            System.out.print("You have woken up from your dream...");
                            Thread.sleep(1000);
                            System.out.println(" but you have died.");
                        }
                    }else{
                        int ch3 = getChoice(new String[]{});
                    }
                }else{
                    System.out.println("What's that figure!!??");
                    Thread.sleep(1000);
                    System.out.println("...");
                    Thread.sleep(1000);
                    System.out.print("Oh... it's just ");
                    int rand = (int)(Math.random()*2);
                    if(rand == 0){
                        System.out.println("some clothing... you win! go straight to bed. don't pass go. don't collect $200");
                    }else{
                        System.out.println("frakenstein's monster! It wants to eat you. You die :(");
                    }
                }
            }
        }
    }

    public static int getChoice(String[] options){
        int input = -1;
        while(input < 1 || input > options.length){
            for(int i = 0; i < options.length; i++){
                System.out.println("For {"+options[i]+"}, enter "+(i+1)+".");
            }
            input = Integer.parseInt(sc.nextLine());
        }
        return input;
    }
}

import java.util.Random;
import java.util.Scanner;

/**
 * 1.2.10 MadLibs
 * Rowan Richards, Harper Hakim
 * 21 september, 2022
 * 
 * Picks a random riddle from a list and prompts the user
 * to fill it outwithout knowing the original statement
 * 
 * Preconditions:
 * Riddle to fill in, user input through the console
 * 
 * Postconditions: a printed statement of the riddle filled in with user inputs
 */

public class MadLibs {
    public static void main(String[] args){
        String[] riddles = { //define all of the riddle choices
            "I went to the animal <noun>, the <plural_noun> and the <number> beasts were there.",
            "Once upon a time in a <adjective> land, a <noun> <adverb> grew.",
            "The <adjective> <noun> is delicious.",
            "If not for the <noun>, the <adjective> and <adjective> elephant would go extinct.",
            "As you <verb> up to the <adjective> house, you knock on the door to hear a <otomatopoeia/sound>.",
            "<number> * <number> does not equal <noun> nor <prime number>"
        };

        Random rand = new Random(); //for getting random numbers

        Scanner sc = new Scanner(System.in); //for getting user inputs


        //in order to keep promting new MadLibs
        boolean running = true;

        while(running){
            String riddle = riddles[(int)(rand.nextInt(riddles.length))]; //pick a random riddle from the list

            //while a <> section exists, keep asking for something
            while(riddle.indexOf('<') != -1){
                int i = riddle.indexOf('<');//index of the start of the first "word"
                int j = riddle.indexOf('>');//index of the end of the first "word"
    
                if(i > j) break; //if the open bracket comes after the closed bracket, stop running.
                //running with i > j will result in the substring method throwing an exception
    
                String thing = riddle.substring(i + 1, j);//the stuff inside <>, what to ask for
    
                //asks for the thing, using "an" if needed
                if(thing.startsWith("a") || thing.startsWith("e") || thing.startsWith("o") || thing.startsWith("i") || thing.startsWith("u")){
                    System.out.print("Enter an " + thing + ": ");
                }else{
                    System.out.print("Enter a " + thing + ": ");
                }
        
                String input = sc.nextLine();//gets a user input
                if(input.equals("end")){running = false; break;}
                
                //replaces the <...> of the riddle with the user input
                riddle = riddle.substring(0,i)+input+riddle.substring(j+1);
            }
            System.out.println(riddle); 
        }
        sc.close();
    }
}

import java.util.Random;
import java.util.Scanner;

/**
 * MadLibs project
 */

public class MadLibs {
    public static void main(String[] args){
        String[] riddles = { //define all of the riddle choices
            "I went to the animal <noun>, the <plural_noun> and the <number> beasts were there.",
            "Once upon a time in a <adjective> land, a <noun> <adverb> grew.",
            "Make up your own three-blank Mad Lib to include other parts of speech such as verbs and proper nouns.",
            "The <adjective> <noun> is delicious.",
            "If not for the <noun>, the <adjective> and <adjective> elephant would go extinct.",
            "As you <verb> up to the <adjective> house, you knock on the door to hear a <otomatopoeia/sound>."
        };
        Random rand = new Random(); //for getting random numbers

        String riddle = riddles[(int)(rand.nextInt(riddles.length))]; //pick a random riddle from the list

        Scanner sc = new Scanner(System.in);

        //while a <> section exists, repeat
        while(riddle.indexOf('<') != -1){
            int i = riddle.indexOf('<');//index of the start of the first "word"
            int j = riddle.indexOf('>');//index of the end of the first "word"

            //if(i > j)break;
            String thing = riddle.substring(i + 1, j);//the stuff inside <>

            //asks for the thing, using an if needed
            if(thing.startsWith("a") || thing.startsWith("e") || thing.startsWith("o") || thing.startsWith("i") || thing.startsWith("u")){
                System.out.println("Enter an " + thing);
            }else{
                System.out.println("Enter a " + thing);
            }
    
            String input = sc.nextLine();//gets a user input
            
            //generates new text with the user input replacing the <> and stuff inside it, and the rest of the riddle remaining
            String new_riddle = riddle.substring(0,i)+input+riddle.substring(j+1);
            
            //sets the current riddle being iterated on to the new riddle
            riddle = new_riddle;
        }

        System.out.println(riddle);
    }
}

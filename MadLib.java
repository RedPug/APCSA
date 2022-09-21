import java.util.Random;
import java.util.Scanner;

public class MadLib {
    public static void main(String[] args){
        String[] riddles = {
            "I went to the animal <noun>, the <plural_noun> and the <number> beasts were there.",
            "Once upon a time in a <adjective> land, a <noun> <adverb> grew.",
            "Make up your own three-blank Mad Lib to include other parts of speech such as verbs and proper nouns.",
            "the <adjective> <noun> is good."
        };
        Random rand = new Random();
        String riddle = riddles[(int)(rand.nextInt(riddles.length))];

        Scanner sc = new Scanner(System.in);

        while(riddle.indexOf('<') != -1){
            int i = riddle.indexOf('<');
            int j = riddle.indexOf('>');

            //if(i > j)break;
            String thing = riddle.substring(i + 1, j);
            if(thing.startsWith("a") || thing.startsWith("e") || thing.startsWith("o") || thing.startsWith("i") || thing.startsWith("u")){
                System.out.println("Enter an " + thing);
            }else{
                System.out.println("Enter a " + thing);
            }
    
            String input = sc.nextLine();
    
            String new_riddle = riddle.substring(0,i)+input+riddle.substring(j+1);
    
            riddle = new_riddle;
        }

        System.out.println(riddle);
    }
}

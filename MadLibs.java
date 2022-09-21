import java.util.Scanner;

public class MadLibs{
    public static void main(String[] args){
        String[] riddles = {
            "I went to the animal <noun>, the <plural_noun> and the <number> beasts were there."
        };

        String riddle = riddles[(int)(Math.random()*(riddles.length-1))];

        Scanner sc = new Scanner(System.in);

        while(riddle.indexOf('<') != -1){
            int i = riddle.indexOf('<');
            int j = riddle.indexOf('>');
            if(i > j) break;

            String str = riddle.substring(i+1, j-1);
            System.out.println("Please type a "+str);
            String in = sc.nextLine();
        }


    }
}
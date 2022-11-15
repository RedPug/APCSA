package PhraseSolverGame;

/*
 * Activity 2.5.2
 * 
 * A Player class the PhraseSolverGame
 */
import java.util.Scanner;

public class Player
{
  /* your code here - attributes */
  private String name;
  private double score;

  /* your code here - constructor(s) */ 
  public Player() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Hello, [insert cool name here], what is your name!!??");

    String name = sc.nextLine();

    this.name = name;

    System.out.println("Hello, {"+name+"}, you are bad at this. Have a fun time playing this miserable game!");

    //sc.close();

    this.score = 0;
  }

  public Player(String name) {

    this.name = name;

    this.score = 0;

    System.out.println("Hello, {"+this.name+"}, you are bad at this. Have a fun time playing this miserable game!");
  }

  /* your code here - accessor(s) */ 

  public double getPoints(){
    return this.score;
  }

  public String getName(){
    return this.name;
  }

  /* your code here - mutator(s) */ 

  public void addPoints(double amount){
    this.score += amount;
  }

  public void setName(String name){
    this.name = name;
  }
}
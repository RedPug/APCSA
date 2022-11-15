package PhraseSolverGame;

/*
 * Activity 2.5.2
 *
 *  The PhraseSolver class the PhraseSolverGame
 */
import java.util.Scanner;
  
public class PhraseSolver
{
  /* your code here - attributes */
  private Player player1;
  private Player player2;
  private Board game;
  public boolean solved;

  /* your code here - constructor(s) */ 
  public PhraseSolver(){
    player1 = new Player();
    player2 = new Player();

    game = new Board();

    solved = false;

    
  }

  /* your code here - accessor(s) */
  
  /* your code here - mutator(s)  */

  public void play()
  {
    boolean solved = false;
    int currentPlayer = 1;

    Scanner input = new Scanner(System.in);
    
    boolean correct = true;
    while (!solved) 
    {
      
      /* your code here - game logic */
      game.setLetterVale();

      System.out.println();
      
      System.out.println(
        player1.getName()+": "+player1.getPoints()+" points, "
      + player2.getName() + ": " + player2.getPoints()+" points."
      + " Next guess: " + game.getPointsPool() + " points."
      );
      System.out.println("Solved phrase: "+game.getSolvedPhrase());

      if(currentPlayer == 1){
        System.out.print(player1.getName());
      }else {System.out.print(player2.getName());}
      System.out.println(", Please enter your guess.");

      String guess = input.nextLine();

      if(guess.length() == 1){
        correct = game.guessLetter(guess);

        if(correct){
          if(currentPlayer == 1){
            player1.addPoints(game.getPointsPool());
          }else {
            player2.addPoints(game.getPointsPool());
          }
        }

        solved = game.getSolvedPhrase().indexOf("_") == -1;
      }else{
        solved = game.isSolved(guess);
        if(solved){
          (currentPlayer == 1 ? player1 : player2).addPoints(100000000);
        }
        
      }

      /* your code here - determine how game ends */
      currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

    if(player1.getPoints() > player2.getPoints()){
      System.out.println(player1.getName()+" wins with "+player1.getPoints()+" points.");
    }else{
      System.out.println(player2.getName()+" wins with "+player2.getPoints()+" points.");
    }
   
  }
  
}
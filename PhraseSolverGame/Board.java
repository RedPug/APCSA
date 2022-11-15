package PhraseSolverGame;

/*
 * Activity 2.5.2
 *
 * A Board class the PhraseSolverGame
 */
import java.util.Scanner;
import java.io.File;

public class  Board
{
  private String solvedPhrase;
  private String phrase;
  private int currentLetterValue; 

  /* your code here - constructor(s) */ 
  public Board(){
    this.solvedPhrase = "";
    this.phrase = loadPhrase();
    this.currentLetterValue = 0;

    //System.out.println("Phrase: " + phrase); //temp test code

  }

  /* your code here - accessor(s) */

  public String getPhrase(){
    return this.phrase;
  }

  public String getSolvedPhrase(){
    return this.solvedPhrase;
  }

  public int getPointsPool(){
    return this.currentLetterValue;
  }
  
  /* your code here - mutator(s)  */


  /* ---------- provided code, do not modify ---------- */
  public void setLetterVale()
  {
    int randomInt = (int) ((Math.random() * 10) + 1) * 100;    
    currentLetterValue = randomInt;
  }

  public boolean isSolved(String guess)
  {
    if (phrase.equals(guess))
    {
      return true;
    }
    return false;
  }

  private String loadPhrase()
  {
    String tempPhrase = "";
    
    int numOfLines = 0;
    try 
    {
      Scanner sc = new Scanner(new File("PhraseSolverGame/phrases.txt"));
      while (sc.hasNextLine())
      {
        tempPhrase = sc.nextLine().trim();
        numOfLines++;
      }
    } catch(Exception e) { System.out.println("Error reading or parsing phrases.txt"); }
    
		int randomInt = (int) ((Math.random() * numOfLines) + 1);
    
    try 
    {
      int count = 0;
      Scanner sc = new Scanner(new File("PhraseSolverGame/phrases.txt"));
      while (sc.hasNextLine())
      {
        count++;
        String temp = sc.nextLine().trim();
        if (count == randomInt)
        {
          tempPhrase = temp;
        }
      }
    } catch (Exception e) { System.out.println("Error reading or parsing phrases.txt"); }
    
    for (int i = 0; i < tempPhrase.length(); i++)
    {
      if (tempPhrase.substring(i, i + 1).equals(" "))
      {
        solvedPhrase += "  ";
      }  
      else
      {
        solvedPhrase += "_ ";
      }
    }  
    
    return tempPhrase;
  }  

  /**
   * Solves the phrase given a character guess.
   * The function returns whether or not the character was found within the phrase
   * <p>
   * Preconditions: guess is a String, specifically a String of length 1
   * <p>
   * Postconditions: returns a boolean which indicates if the guess was correct
   */
  public boolean guessLetter(String guess){
    boolean foundLetter = false; //used purely as a return value, defaulting to false.
    String newSolvedPhrase = "";

    //solvedPhrase in format of "_ a _ a _ a _ _", where phrase would equal "bananas!"
    
    //iterate through each character of the phrase
    for (int i = 0; i < phrase.length(); i++)
    {
      //if the current selected letter in the phrase equals the guess...
      if (phrase.substring(i, i + 1).equals(guess))
      {
        //add the character of the phrase to the stored string
        newSolvedPhrase += guess + " ";
        //mark that the letter has been found
        foundLetter = true;
      }
      else
      {
        //adds the character from the current solved phrase with the stored progress to the new phrase
        //for example, if the letter hasn't been found, the substring will be "_ ",
        //if the letter was found and it was an "a", the substring would be "a "
        newSolvedPhrase += solvedPhrase.substring(i * 2, i * 2 + 1) + " ";
      }
    }

    //update the phrase to be iterated on in the future
    solvedPhrase = newSolvedPhrase;

    return foundLetter;
  }
}




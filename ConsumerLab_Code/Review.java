package ConsumerLab_Code;

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";

  public static void main(String[] args){
    //System.out.println(totalSentiment("ConsumerLab_Code/something.txt"));
    //System.out.println(starRating("ConsumerLab_Code/something.txt"));
    System.out.println(fakeReview("ConsumerLab_Code/something_else.txt"));
  }

  public static int starRating(String fileName){
    double d = totalSentiment(fileName);
    
    int rating = (int)((d+2.92)/(29.05+2.92)*3+1 + 0.5);

    return Math.min(Math.max(rating, 1), 4);
  }

  public static String fakeReview(String fileName){
    String text = textToString(fileName);
    String adjective = "";
    while (text.indexOf("*")>0){
      int index = text.indexOf("*");
      int i;
      for (i = index; i<text.lastIndexOf(" "); i++){
        if (text.substring(i,i+1).equals(" ")){
          adjective = text.substring(index + 1, i);
          break;
        }
      }
      double num = sentimentVal(adjective);

      if (num > 0){
        text = text.substring(0,index) + randomPositiveAdj() + text.substring(i);
      }else{
        text = text.substring(0,index) + randomNegativeAdj() + text.substring(i);
      }
    }

    return text;
  }
 
  /**
   * returns the sum of sentiment values for the entire text file
   * @param fileName location of the file to be checked
   * @return sum of the sentiment values for each word in the text
   */
  public static double totalSentiment(String fileName){
    String text = textToString(fileName); //get the input string

    double sum = 0;
    
    String word = "";
    //loop through the characters in the input string
    for(int i = 0; i < text.length(); i++){
      //if the character selected is a letter, add it to the word
      if(Character.isLetter(text.charAt(i))){
        word += text.charAt(i);
      //if the character selected is not a letter
      //the word is done and the sentiment value can be calculated for it
      }else{
        sum += sentimentVal(word);
        word = ""; //reset the word
      }
    }

    return sum;
  }
  
  static{
    try {
      Scanner input = new Scanner(new File("C:/Users/1934355/Desktop/GitCode/APCSA/ConsumerLab_Code/cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("C:/Users/1934355/Desktop/GitCode/APCSA/ConsumerLab_Code/positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("C:/Users/1934355/Desktop/GitCode/APCSA/ConsumerLab_Code/negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.nextLine() + " \n";
        
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
}

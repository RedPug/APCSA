package Nim;

import java.util.Scanner;

public class Game {
    private Board board;

    public Game(){
        board = new Board();
    }

    public void play(){
        Scanner sc = new Scanner(System.in);
        System.out.println("To play with two players, enter 1. To play with the computer, enter 2");
        int mode = sc.nextInt(); //1 for two player, 2 for AI

        Player player1 = new Player();
        Player player2 = null;

        if(mode == 1){
            System.out.println();
            player2 = new Player();
        }

        int computerScore = 0;

        while(true){
            boolean turn = Math.random() > .5; //which player is going, true for p1, false for p2/computer

            while(this.board.getPileSum() > 0){
                turn = !turn; //switch whose turn it is
                if(turn){
                    player1.takeTurn(this.board);
                }else if(mode == 1){
                    player2.takeTurn(this.board);
                }else{
                    //computer
                    //get a random pile to remove from, avoiding empty piles
                    int pile = (int)(Math.random()*3)+1;
                    while(this.board.getCount(pile) <= 0 && this.board.getPileSum() > 0){
                        pile = (int)(Math.random()*3)+1;
                    }
                    
                    //pick a random number of things to remove from the pile
                    int maxItemsToRemove = Math.max(this.board.getCount(pile)/2,1);
    
                    int num = (int)(Math.random()*(maxItemsToRemove)+1);
    
                    this.board.takeItems(pile, num);
    
                    System.out.println("The computer has decided to take " + num + " items from pile " + pile + ".");
                }
            }
            if(!turn){
                System.out.println(player1.getName()+" won!");
                player1.addScore(1);
            }else{
                if(mode == 1){
                    System.out.println(player2.getName()+" won!");
                    player2.addScore(1);
                }else{
                    System.out.println("The computer literally won with random numbers, you idiot.");
                    computerScore++;
                }
            }

            System.out.println("Would you like to play again? Type 1 for yes, and 2 for no.");
            int choice = sc.nextInt();
            if(choice != 1){
                break;
            }
        }
        if(mode == 1){
            if(player1.getScore() > player2.getScore()){
                System.out.println(player1.getName() + " won with " + player1.getScore() + 
                " points, as opposed to " + player2.getName() + "'s " + player2.getScore() + " points.");
            }else if(player2.getScore() > player1.getScore()){
                System.out.println(player2.getName() + " won with " + player2.getScore() + 
                " points, as opposed to " + player1.getName() + "'s " + player1.getScore() + " points.");
            }else{
                System.out.println("Both players have tied with " + player1.getScore() + " points.");
            }
        }else{
            if(player1.getScore() > computerScore){
                System.out.println(player1.getName() + " won with " + player1.getScore() + 
                " points, as opposed to the dumb computer with " + computerScore + " points.");
            }else if(player2.getScore() > player1.getScore()){
                System.out.println("The computer won with " + computerScore + " points. " + 
                player1.getName() + " lost with a wimpy " + player1.getScore() + " points. Get some friends.");
            }else{
                System.out.println("You have tied with the computer with " + player1.getScore() + " points because you're so bad at this.");
            }
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.play();
    }
}

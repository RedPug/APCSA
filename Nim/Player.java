package Nim;

import java.util.Scanner;

public class Player {

    private String name;
    private int score;

    private static Scanner sc = new Scanner(System.in);

    public Player() {
    
        System.out.println("Hello, [insert cool name here], what is your name!!??");
    
        String name = sc.nextLine();
    
        this.name = name;
        this.score = 0;
    
        System.out.println("Hello, {"+name+"}, you are bad at this. Have a fun time playing this miserable game!");
    }

    public String getName(){
        return this.name;
    }

    public void takeTurn(Board board){
        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("Board: ");
        System.out.println("Pile 1: "+board.getCount(1));
        System.out.println("Pile 2: "+board.getCount(2));
        System.out.println("Pile 3: "+board.getCount(3));

        System.out.println(this.name + " enter 1, 2, or 3, to select a pile.");
        int pileNum = sc.nextInt();

        int maxItemsToRemove = Math.max(board.getCount(pileNum)/2,1);

        System.out.println("There are " + board.getCount(pileNum) + " items remaining. You may remove up to " + maxItemsToRemove + " items.");
        int amount = Math.min(sc.nextInt(), maxItemsToRemove);

        board.takeItems(pileNum, amount);
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int num){
        this.score += num;
    }
}

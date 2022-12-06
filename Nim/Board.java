package Nim;

public class Board {
    private static int pile1;
    private static int pile2;
    private static int pile3;
    
    
    public Board(){
        pile1 = (int)(Math.random()*41+10);
        pile2 = (int)(Math.random()*41+10);
        pile3 = (int)(Math.random()*41+10);
    }
    public void takeItems(int pileNum, int amount){
        switch(pileNum){
            case 1:
                pile1 -= amount;
                break;
            case 2:
                pile2 -= amount;
                break;
            case 3:
                pile3 -= amount;
                break;
        }
    }

    public int getCount(int pileNum){
        switch(pileNum){
            case 1:
                return pile1;
            case 2:
                return pile2;
            case 3:
                return pile3;
            default:
                return -1;
        }
    }

    public int getPileSum(){
        return pile1+pile2+pile3;
    }
}

package APCSA;


public class NumberRiddle{
    public static void main(String[] args){
        int init = 8;
        int num = init;
        System.out.println("num="+num);

        num *= 2;

        System.out.println("num="+num);

        num += 6;

        System.out.println("num="+num);

        double numb = num;
        numb /= 2;

        System.out.println("num="+numb);

        numb -= init;

        System.out.println("num="+numb);

    }
}
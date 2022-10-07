import java.util.Scanner;
import java.util.ArrayList;

public class DataAnalysis {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String mode = "";

        while(!mode.toLowerCase().equals("sum") && !mode.toLowerCase().equals("avg")){
            System.out.println("Please enter an analysis type: sum or avg");
            mode = sc.nextLine();
        }
        
        ArrayList<Double> dataPoints = new ArrayList<Double>();

        String in = "";

        System.out.println("Please enter your data. When you are done, type \"done\".");

        while(true){
            in = sc.nextLine();
            if(in.toLowerCase().equals("done")) break;
            dataPoints.add(Double.parseDouble(in));
        }

        double sum = 0;
        for(Double d : dataPoints){
            sum += d;
        }

        if(mode.equals("sum")){
            System.out.println("The sum of your data is: " + sum);
        }

        if(mode.equals("avg")){
            System.out.println("The average of your data is: " + (sum / dataPoints.size()));
        }
        sc.close();
    }
}

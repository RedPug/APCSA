import java.util.Scanner;

import javax.swing.JFrame;

public class HelloWorld{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        while(true){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(String.format("%010d.0, %010d.0",a,b));

            if(a == 10){break;}
        }
        scanner.close();
    }
}


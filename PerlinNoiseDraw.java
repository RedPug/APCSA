import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.*;

public class PerlinNoiseDraw    extends JPanel{

    static final int WIDTH = 900;
    static final int HEIGHT = 500;

    public PerlinNoiseDraw(){
        JFrame frame = new JFrame("perlin noise!");

        frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g1 = (Graphics2D)g;

        g1.setColor(Color.RED);
        g1.drawRect(0, 0, 50, 50);

        for(int i = 0; i < WIDTH/5; i++){
            int value = 0;
            for(int j = 0; j < HEIGHT/5; j++){
                value = (int) (255*Noise.getNoise(i/10.0,j/10.0));
                //System.out.println(value);
                g1.setColor(new Color(value, value, value));
                g1.fillRect(i*5, j*5, 5, 5);
            }
            //System.out.println(value);
        }
        g1.dispose();

        //for(int i = 0; i < 1000; i++){
        //    System.out.println(Noise.getNoise(i/10.0, 0));
        //}
    }
    public static void main(String[] args){
        new PerlinNoiseDraw();
    }
    
}

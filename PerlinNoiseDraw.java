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

        double scale = 10.0;
        
        int size = 5;

        for(int i = 0; i < WIDTH/size; i++){
            int value = 0;
            for(int j = 0; j < HEIGHT/size; j++){
                value = (int) (255*Noise.getNoise(i/scale,j/scale));
                //System.out.println(value);
                g1.setColor(new Color(value, value, value));
                g1.fillRect(i*size, j*size, size, size);
            }
            //System.out.println(value);
        }


        // for(int i = 0; i < 2; i++){
        //     Vector2D[] vecs = new Vector2D[]{
        //         Noise.randVecAtIndex(30+i, 30),
        //         Noise.randVecAtIndex(31+i, 30)
        //     };
        //     for(Vector2D vec : vecs){
        //         System.out.println(vec.x + "," + vec.y);
        //     }
        //     System.out.println();
        // }

        g1.dispose();

        //for(int i = 0; i < 1000; i++){
        //    System.out.println(Noise.getNoise(i/10.0, 0));
        //}
    }
    public static void main(String[] args){
        new PerlinNoiseDraw();
    }
    
}

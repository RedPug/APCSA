package my_crappy_projects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.*;

public class PerlinNoiseDraw extends JPanel{

    static final int WIDTH = 400;
    static final int HEIGHT = 300;

    private double z = 0;

    public PerlinNoiseDraw(){
        JFrame frame = new JFrame("perlin noise!");

        frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);

        while(true){
            this.repaint();
            //break;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g1 = (Graphics2D)g;
        
        int size = 1;

        double scale = 20;

        scale /= size;

        double scale2 = 60;

        scale2 /= size;

        long t0 = System.currentTimeMillis();

        int value = 0;

        for(int i = 0; i < WIDTH/size; i++){
            
            for(int j = 0; j < HEIGHT/size; j++){
                double n1 = Noise.getNoise(i/scale, j/scale, z/scale);
                double n2 = Noise.getNoise(i/scale2 + 2435423, j/scale2 + 3423423, z/scale2 + 908923);
                double avg = (n1 + n2) * 0.5;

                value = (int)(255*avg);
                //System.out.println(value);

                g1.setColor(new Color(value, value, value));
                g1.fillRect(i*size, j*size, size, size);

                //g1.setColor(new Color((int)(n1*255), (int)(n1*255), (int)(n1*255)));
                //g1.fillRect(i*size + WIDTH, j*size, size, size);

                //g1.setColor(new Color((int)(n2*255), (int)(n2*255), (int)(n2*255)));
                //g1.fillRect(i*size + WIDTH, j*size+HEIGHT, size, size);

                //g1.setColor(new Color((int)(n1*255), 0, (int)(n2*255)));
                //g1.fillRect(i*size, j*size + HEIGHT, size, size);
            }
        }

        long t1 = System.currentTimeMillis();

        System.out.println("Draw Time: " + (t1-t0) + "ms");

        z += 100/scale;

        scale += 0.1;

        //System.out.println(z);

        g1.dispose();
    }
    public static void main(String[] args){
        new PerlinNoiseDraw();
    }
    
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel{
    float fps = 60;
    float physicsTps = fps;
    static int screenWidth = 300;
    static int screenHeight = 300;

    public double timeScale;

    public JFrame frame;

    public Game(String title){
        JFrame frame = new JFrame(title);

        this.frame = frame;

        frame.setSize(screenWidth+16, screenHeight+40);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);

        frame.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {onMouseClick(e);}
            public void mousePressed(MouseEvent e){onMousePressed(e);}
            public void mouseReleased(MouseEvent e) {onMouseReleased(e);}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        frame.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {onMouseMoved(e);}
        });

        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                onMouseScroll(e);
            }
        });

        
        frame.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                onKeyTyped(e);
            }
            public void keyPressed(KeyEvent e){
                onKeyPressed(e);
            }
            public void keyReleased(KeyEvent e){
                onKeyReleased(e);
            }
        });

        this.timeScale = 1;
        

        this.init();
        /*
        while(true){
            long t0 = System.currentTimeMillis();

            this.tick();
            this.repaint();

            long t1 = System.currentTimeMillis();

            int tDiff = (int)(t1 - t0);

            int desiredT = (int)(1000/this.fps);

            try {
                Thread.sleep(Math.max(desiredT-tDiff,0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            screenWidth = this.getWidth();
            screenHeight = this.getHeight();
        }
        */
        long t0 = System.currentTimeMillis();
        long t1 = System.currentTimeMillis();
        //double acc = 0.0;
        while(true){
            /*
            long t = System.currentTimeMillis();

            double tL = (1000/this.physicsTps); //time for a logic tick in ms

            int dt = (int)(t - t0); //time that has passed
            */
            t0 = System.currentTimeMillis();
            /*
            acc += dt;

            while(acc >= tL){
                this.tick(tL);
                acc -= tL;
            }
            */
            //long t1 = System.currentTimeMillis();

            int tLastFrame = Math.min((int)(t0-t1),1);

            this.tick(physicsTps*tLastFrame);

            this.repaint();

            t1 = System.currentTimeMillis();

            int tDiff = (int)(t1 - t0);

            int desiredT = (int)(1000/fps);

            try {
                Thread.sleep(Math.max(desiredT-tDiff,0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        this.draw((Graphics2D)g);
    }

    public void draw(Graphics2D g){}

    public void onMouseClick(MouseEvent e){}

    public void onMousePressed(MouseEvent e){}
    
    public void onMouseReleased(MouseEvent e){}

    public void onMouseMoved(MouseEvent e){}

    public void onMouseScroll(MouseWheelEvent e){}

    public void onKeyPressed(KeyEvent e){}

    public void onKeyReleased(KeyEvent e){}

    public void onKeyTyped(KeyEvent e){}

    public void init(){}

    public void tick(double dt){}
}

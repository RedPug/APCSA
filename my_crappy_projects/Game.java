package my_crappy_projects;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JPanel{
    float fps = 60;
    float physicsTps = fps;

    int physicsRes = 10;

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
            public void mouseDragged(MouseEvent e) {}
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

        //game loop
        while(true){
            t0 = System.currentTimeMillis();

            //t0 - t1 because t0 is technically more up to date, as t1 was only updated in the last frame!
            int tLastFrame = Math.min((int)(t0-t1),1);

            for(int i = 0; i < physicsRes; i++){
                //the total time elapsed in the tick function during 1 frame is
                //equal tothe physics speed * the time it takes to render the last frame 
                //(idealy 1/60th of a second at physics speed 1)
                this.tick(physicsTps*tLastFrame/physicsRes);
            }
            
            this.repaint();

            t1 = System.currentTimeMillis();

            int tDiff = (int)(t1 - t0); //the time spent rendering the current frame

            int desiredT = (int)(1000/fps); //ideal time to render a frame

            try {
                Thread.sleep(Math.max(desiredT-tDiff,0)); //sleep for the remaining time for the frame
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

    /**
     * called every frame, used to draw all things to the screen.
     * @param g
     */
    public void draw(Graphics2D g){}

    public void onMouseClick(MouseEvent e){}

    public void onMousePressed(MouseEvent e){}
    
    public void onMouseReleased(MouseEvent e){}

    public void onMouseMoved(MouseEvent e){}

    public void onMouseScroll(MouseWheelEvent e){}

    public void onKeyPressed(KeyEvent e){}

    public void onKeyReleased(KeyEvent e){}

    public void onKeyTyped(KeyEvent e){}

    /**
     * used to initialize values before the game starts
     */
    public void init(){}

    /**
     * Updates the physics simulation to go forward dt milliseconds.
     * @param dt Number of milliseconds to fast forward
     */
    public void tick(double dt){}
}

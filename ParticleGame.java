package APCSA;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ParticleGame extends JPanel{

    private ArrayList<Particle> particles = new ArrayList<Particle>();

    public int particleRadius = 10;

    static int fps = 60;

    static int WIDTH = 300;
    static int HEIGHT = 300;

    public static void main(String[] args){

        JFrame frame = new JFrame("Particle Sim");
        ParticleGame game = new ParticleGame();
        frame.add(game);
        frame.setSize(WIDTH+16, HEIGHT+40);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}

            public void mousePressed(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    Particle p = new Particle(e.getX(), e.getY(), game.particleRadius);
                    game.particles.add(p);
                    
                }
            }
        });

        frame.getContentPane().addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                game.particleRadius -= e.getWheelRotation();
                
                game.particleRadius = Math.min(Math.max(game.particleRadius,10),50);
            }
        });
        
        game.init();

        while(true){
            long t0 = System.currentTimeMillis();

            game.tick();
            game.repaint();

            long t1 = System.currentTimeMillis();

            int tDiff = (int)(t1 - t0);

            int desiredT = 1000/fps;

            try {
                Thread.sleep(Math.max(desiredT-tDiff,0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WIDTH = game.getWidth();
            HEIGHT = game.getHeight();
        }
    }

    public void init(){
        double maxSpd = 0.5;
        for(int i = 0; i < 30; i++){
            Particle p = new Particle(Math.random()*300,Math.random()*300,(Math.random()+0.5)*10);
            p.velX = Math.random()*maxSpd*2-maxSpd;
            p.velY = Math.random()*maxSpd*2-maxSpd;
            //p.mass = p.radius;

            this.particles.add(p);
        }
        //Particle p1 = this.particles.get(5);
        //p1.velX = 5;
        //this.particles.set(5,p1);
    }

    public void tick(){

        for(Particle p : this.particles){
            p.update();
        }

        //System.out.println(this.getWidth());

        //double momentum = 0;

        //ArrayList<Particle> particlesOut = new ArrayList<Particle>();
        for(int i = 0; i < this.particles.size(); i++){
            Particle p1 = this.particles.get(i);
            //momentum += p1.mass * Math.sqrt(p1.velX * p1.velX + p1.velY * p1.velY);
            for(int j = i + 1; j < this.particles.size(); j++){
                Particle p2 = this.particles.get(j);

                if(p1.checkCollisions(p2)){ //if the particles collide, do something with them
                    double velX = p2.velX - p1.velX; //local velocity between the two points, from p1 to p2
                    double velY = p2.velY - p1.velY;
                    double velMag = Math.sqrt(velX*velX + velY*velY);
                    //velX /= velMag; //normalize velocity components to get direction
                    //velY /= velMag;

                    double xDiff = p2.x - p1.x;
                    double yDiff = p2.y - p1.y;

                    double fac = p2.radius/(p2.radius + p1.radius);

                    double xMid = lerp(p1.x, p2.x, fac);
                    double yMid = lerp(p1.y, p2.y, fac);

                    double dist = Math.sqrt(xDiff*xDiff + yDiff * yDiff);

                    xDiff /= dist; //normalize to just get a direction vector
                    yDiff /= dist;

                    double inertia = velMag * (p1.mass + p2.mass) / 2;

                    double fX = -xDiff*inertia*0.4;
                    double fY = -yDiff*inertia*0.4;

                    //System.out.println(fX+","+fY);
                    p1.applyForce(fX, fY);
                    p2.applyForce(-fX,-fY);

                    p1.x = xMid - p1.radius * xDiff;
                    p1.y = yMid - p1.radius * yDiff;

                    p2.x = xMid + p2.radius * xDiff;
                    p2.y = yMid + p2.radius * yDiff;

                    
                }
            }

            if(p1.x - p1.radius < 0 || p1.x + p1.radius > WIDTH){p1.velX *= -0.6;}
            if(p1.y - p1.radius < 0 || p1.y + p1.radius > HEIGHT){p1.velY *= -0.6;}

            p1.x = Math.min(Math.max(p1.x,p1.radius),WIDTH - p1.radius);
            p1.y = Math.min(Math.max(p1.y,p1.radius),HEIGHT - p1.radius);

            p1.velY += 0.09;
        }
        //System.out.println("momentum = " + momentum);
    }

    Double lerp(double x, double y, double fac){
        return fac * x + (1-fac) * y;
    }
    

    @Override
    public void paint(Graphics g0){
        super.paint(g0); // clears the screen

        Graphics2D g = (Graphics2D) g0;

        for(Particle p : this.particles){
            p.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("size = "+this.particleRadius, 10, 10);
    }
}

class Particle{
    public double x;
    public double y;
    public double velX;
    public double velY;
    public double radius;
    public double mass = 1;
    public Color color;

    public Particle(double x, double y, double radius){
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
        this.radius = radius;
        this.mass = this.radius;
        this.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }

    public void update(){
        this.x += this.velX;
        this.y += this.velY;
    }

    public void applyForce(double xMag, double yMag){
        this.velX += xMag/this.mass;
        this.velY += yMag/this.mass;
    }

    /**
     * Checks if this and other collide
     * @param other a particle to check against
     * @return true if the particles collided, false otherwise
     */
    public boolean checkCollisions(Particle other){
        return (this.x-other.x)*(this.x-other.x) + (this.y-other.y)*(this.y-other.y) <= (this.radius + other.radius)*(this.radius+other.radius);
    }

    public void draw(Graphics2D g){
        g.setColor(this.color);
        g.fillOval((int)(this.x-this.radius), (int)(this.y-this.radius), (int)(this.radius*2), (int)(this.radius*2));
    }
}

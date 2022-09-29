
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

public class ParticleGame extends Game{

    public ArrayList<Particle> particles;

    public int particleRadius = 10;

    public boolean gravity = false;

    public static double G = 100.0;

    public ParticleGame() {
        super("Particle Game!");
    }

    public static void main(String[] args){
        new ParticleGame();
    }

    //used to detect mouse movement when clicking
    private MouseEvent lastClickEvent;

    @Override
    public void onMousePressed(MouseEvent e) {
        lastClickEvent = e;
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        int x = lastClickEvent.getX();
        int y = lastClickEvent.getY();

        int dx = e.getX() - lastClickEvent.getX();
        int dy = e.getY() - lastClickEvent.getY();

        if(e.getButton() == MouseEvent.BUTTON1){
            if(this.particleRadius <=0){return;}
            Point pt = this.screenToGame(x, y);
            Particle p = new Particle(pt.x, pt.y, this.particleRadius);
            p.velX = dx/5/zoom;
            p.velY = dy/5/zoom;
            this.particles.add(p);
            //System.out.println("pos:"+e.getX()+","+e.getY());
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e){
        //Point p = e.getPoint();
        //p.translate(-8, -31);
        //System.out.println(p.x+", "+p.y);
    }

    @Override
    public void onMouseScroll(MouseWheelEvent e){
        this.particleRadius -= e.getWheelRotation();
                
        this.particleRadius = Math.min(Math.max(this.particleRadius,10),50);
    }

    private double translateX = 0;
    private double translateY = 0;
    private double zoom;
    private double scale;

    @Override
    public void onKeyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();

        switch(keyCode){
            case KeyEvent.VK_UNDEFINED:
                System.out.println("undefined input!");
                break;
            case KeyEvent.VK_G:
                this.gravity = !this.gravity;
                break;
            case KeyEvent.VK_W:
                translateY -= 10/zoom;
                break;
            case KeyEvent.VK_S:
                translateY += 10/zoom;
                break;
            case KeyEvent.VK_A:
                translateX -= 10/zoom;
                break;
            case KeyEvent.VK_D:
                translateX += 10/zoom;
                break;
            case KeyEvent.VK_Q:
                scale -= 1;
                zoom = Math.pow(1.1,scale);
                break;
            case KeyEvent.VK_E:
                scale += 1;
                zoom = Math.pow(1.1,scale);
                break;
            case KeyEvent.VK_UP:
                physicsTps *= 2;
                break;
            case KeyEvent.VK_DOWN:
                physicsTps /= 2;
                break;
            case KeyEvent.VK_F5:
                this.particles.clear();
                this.zoom = 1;
                this.scale = 0;
                break;
        }
    }

    @Override
    public void init(){
        this.particles = new ArrayList<Particle>();

        this.particleRadius = 10;
        this.zoom = 1;
        this.scale = 0;

        super.init();
    }

    public void solveCollision(Particle p1, Particle p2, double dt){
        if(p1.checkCollisions(p2)){ //if the particles collide, do something with them
            double velX = (p2.velX - p1.velX); //local velocity between the two points, from p1 to p2
            double velY = (p2.velY - p1.velY);
            double velMag = Math.sqrt(velX*velX + velY*velY);
            //velX /= velMag; //normalize velocity components to get direction
            //velY /= velMag;

            double xDiff = p2.x - p1.x;
            double yDiff = p2.y - p1.y;

            double fac = p2.radius/(p2.radius + p1.radius);

            double xMid = lerp(p1.x, p2.x, fac);
            double yMid = lerp(p1.y, p2.y, fac);

            double dist = Math.sqrt(xDiff*xDiff + yDiff * yDiff);

            if(dist != 0){
                xDiff /= dist; //normalize to just get a direction vector
                yDiff /= dist;

                double inertia = velMag * (p1.mass + p2.mass) / 2;

                double fX = -xDiff*inertia*1;
                double fY = -yDiff*inertia*1;

                //System.out.println(fX+","+fY);
                p1.applyForce(fX, fY, dt);
                p2.applyForce(-fX,-fY, dt);
            }
            
            p1.x = xMid - p1.radius * xDiff;
            p1.y = yMid - p1.radius * yDiff;

            p2.x = xMid + p2.radius * xDiff;
            p2.y = yMid + p2.radius * yDiff;

            if(dist == 0){
                p1.x += 1;
                p1.y += 1;
            }
        }
    }

    @Override
    public void tick(double dt){
        super.tick(dt);
        
        for(Particle p : this.particles){
            p.update(dt);
        }

        //System.out.println(this.getWidth());

        //double momentum = 0;

        //ArrayList<Particle> particlesOut = new ArrayList<Particle>();
        for(int i = 0; i < this.particles.size(); i++){
            Particle p1 = this.particles.get(i);
            //momentum += p1.mass * Math.sqrt(p1.velX * p1.velX + p1.velY * p1.velY);
            for(int j = i + 1; j < this.particles.size(); j++){
                if(j == i){break;}
                Particle p2 = this.particles.get(j);

                this.solveCollision(p1, p2, dt);

                if(this.gravity){
                    double rCube = Math.pow((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y),1.5); //distance (sqrt) cubed, combine to get 3/2 power

                    double fX = G*p1.mass*p2.mass/rCube*(p2.x-p1.x);
                    double fY = G*p1.mass*p2.mass/rCube*(p2.y-p1.y);
                    //System.out.println(p1.mass+","+p2.mass);

                    p1.applyForce(fX, fY, dt);
                    p2.applyForce(-fX, -fY, dt);
                }
            }

            //if(p1.x - p1.radius < 0 || p1.x + p1.radius > screenWidth){p1.velX *= -0.6;}
            //if(p1.y - p1.radius < 0 || p1.y + p1.radius > screenHeight){p1.velY *= -0.6;}

            //p1.x = Math.min(Math.max(p1.x,p1.radius),screenWidth - p1.radius);
            //p1.y = Math.min(Math.max(p1.y,p1.radius),screenHeight - p1.radius);

            //p1.velY += this.gravity;
        }
        //System.out.println("momentum = " + momentum);
    }

    private static Double lerp(double x, double y, double fac){
        return fac * x + (1-fac) * y;
    }
    
    private Point screenToGame(int x, int y){
        Point p = new Point(x,y);

        p.translate(-8, -31);

        double zoomWidth = screenWidth * zoom;
        double zoomHeight = screenHeight * zoom;

        double anchorx = (screenWidth - zoomWidth) / 2;
        double anchory = (screenHeight - zoomHeight) / 2;

        p.translate(-(int)anchorx, -(int)anchory);
        p.setLocation(p.x/zoom, p.y/zoom);
        p.translate((int)translateX, (int)translateY);

        return p;
    }

    @Override
    public void draw(Graphics2D g){
        //System.out.println("draw!");
        super.draw(g); // clears the screen

        g.setColor(Color.BLACK);
        g.drawString("size = "+this.particleRadius, 10, 10);
        g.drawString("Gravity = "+this.gravity,10,20);
        g.drawString("scale = "+this.scale,10,30);
        g.drawString("Physics Speed = "+this.physicsTps/this.fps,10,40);

        double zoomWidth = screenWidth * zoom;
        double zoomHeight = screenHeight * zoom;

        double anchorx = (screenWidth - zoomWidth) / 2;
        double anchory = (screenHeight - zoomHeight) / 2;

        AffineTransform at = new AffineTransform();
        at.translate(anchorx, anchory);
        at.scale(zoom, zoom);
        at.translate(-translateX, -translateY);

        g.setTransform(at);

        //System.out.println(this.particles);

        //double xAvg = 0.0;
        //double yAvg = 0.0;

        //double mTotal = 0.0;

        for(Particle p : this.particles){
            p.draw(g);
            //xAvg += p.x*p.mass;
            //yAvg += p.y*p.mass;
            //mTotal += p.mass;
        }

        //xAvg /= mTotal;
        //yAvg /= mTotal;
        //g.setColor(Color.red);
        //g.fillOval((int)xAvg-5, (int)yAvg-5, 10, 10);

        g.dispose();
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
        this.radius = Math.max(radius,10);
        this.mass = this.radius*this.radius;
        this.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }

    public void update(double dt){
        this.x += this.velX*dt/1000;
        this.y += this.velY*dt/1000;
    }

    public void applyForce(double xMag, double yMag, double dt){
        this.velX += xMag/this.mass*dt/1000;
        this.velY += yMag/this.mass*dt/1000;
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

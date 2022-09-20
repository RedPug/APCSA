
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

public class ParticleGame extends Game{

    

    public ArrayList<Particle> particles;

    public int particleRadius = 10;

    public boolean gravity = false;

    public static double G = 1;


    //static int WIDTH = 300;
    //static int HEIGHT = 300;

    public ParticleGame() {
        super("Particle Game!");
    }

    public static void main(String[] args){
        ParticleGame game = new ParticleGame();
        game.particleRadius = 10;
        game.fps = 1;
    }

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
            p.velX = dx/50;
            p.velY = dy/50;
            this.particles.add(p);
            System.out.println("pos:"+e.getX()+","+e.getY());
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

    @Override
    public void onKeyTyped(KeyEvent e){
        char key = e.getKeyChar();
        if(key == 'g'){
            this.gravity = !this.gravity;
        }

        if(key == 'w'){translateY -= 10/zoom;}
        if(key == 's'){translateY += 10/zoom;}

        if(key == 'a'){translateX -= 10/zoom;}
        if(key == 'd'){translateX += 10/zoom;}

        if(key == 'q'){zoom /= 1.1;}
        if(key == 'e'){zoom *= 1.1;}
    }

    @Override
    public void init(){
        this.particles = new ArrayList<Particle>();

        this.particleRadius = 10;
        this.zoom = 1;

        super.init();

        
        /*
        double maxSpd = 0.5;
        for(int i = 0; i < 30; i++){
            Particle p = new Particle(Math.random()*screenWidth,Math.random()*screenHeight,(Math.random()+1)*10);
            p.velX = Math.random()*maxSpd*2-maxSpd;
            p.velY = Math.random()*maxSpd*2-maxSpd;
            //p.mass = p.radius;

            this.particles.add(p);
        }
        */
        
        //Particle p1 = this.particles.get(5);
        //p1.velX = 5;
        //this.particles.set(5,p1);
    }

    public void solveCollision(Particle p1, Particle p2){
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

            if(dist != 0){
                xDiff /= dist; //normalize to just get a direction vector
                yDiff /= dist;

                double inertia = velMag * (p1.mass + p2.mass) / 2;

                double fX = -xDiff*inertia*0.4;
                double fY = -yDiff*inertia*0.4;

                //System.out.println(fX+","+fY);
                p1.applyForce(fX, fY);
                p2.applyForce(-fX,-fY);
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
    public void tick(){
        super.tick();
        
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

                this.solveCollision(p1, p2);

                if(this.gravity){
                    double rCube = Math.pow((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y),1.5); //distance (sqrt) cubed, combine to get 3/2 power

                    double fX = G*p1.mass*p2.mass/rCube*(p2.x-p1.x);
                    double fY = G*p1.mass*p2.mass/rCube*(p2.y-p1.y);
                    //System.out.println(p1.mass+","+p2.mass);

                    p1.applyForce(fX, fY);
                    p2.applyForce(-fX, -fY);
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

        double anchorx = (screenWidth - zoomWidth) / 2; //= screenWidth*(1-zoom)/2
        double anchory = (screenHeight - zoomHeight) / 2;

        //-1 when x = 0, 1 when x = screenWidth
        //double thingx = (p.x)/screenWidth-0.5;
        //double thingy = (p.y)/screenHeight-0.5;
        /*
        (0,0) -> (translateX, translateY)
        (screenWidth,screenHeight) -> (translateX + zoomWidth, translateY + zoomHeight)
         */

        //p.translate((int)translateX, (int)translateY);
        
        //p.translate(-(int)screenWidth/2, -(int)screenHeight/2);

        //p.setLocation(p.x*zoom, p.y*zoom);

        //p.setLocation(translateX + thingx * zoomWidth, translateY + thingy * zoomHeight);\

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
        g.drawString("zoom = "+this.zoom,10,30);

        //g.translate(-translateX, -translateY); //translate to where you're looking
        //g.translate(+screenWidth*scale/2, +screenHeight*scale/2); //center origin at center of screen
        //System.out.println("Scale = " + this.scale);
        //g.scale(scale, scale); //scale out from the center
        //g.translate(-screenWidth/2, -screenHeight/2); //return origin

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

        for(Particle p : this.particles){
            p.draw(g);
        }


        //g.translate(screenWidth/2, screenHeight/2);
        //g.scale(1/scale, 1/scale);
        //g.translate(-screenWidth/2, -screenHeight/2);
        //g.translate(translateX, translateY);
        

        //g.fillOval(20, 20, 10, 10);

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

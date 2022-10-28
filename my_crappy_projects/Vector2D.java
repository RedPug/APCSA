package my_crappy_projects;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D v){
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D sub(Vector2D v){
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D mult(double l){
        return new Vector2D(this.x * l, this.y * l);
    }

    public double dot(Vector2D v){
        return this.x * v.x + this.y * v.y;
    }

    public Vector2D normalize(){
        double len = Math.sqrt(this.x * this.x + this.y*this.y);

        return new Vector2D(this.x/len, this.y/len);
    }

    public static Vector2D fromPolar(double r, double theta){
        return new Vector2D(r*Math.cos(theta), r*Math.sin(theta));
    }
}

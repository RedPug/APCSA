package my_crappy_projects;

public class Vector3D {

    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double x, double y){
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3D add(Vector3D v){
        return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3D sub(Vector3D v){
        return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3D mult(double l){
        return new Vector3D(this.x * l, this.y * l, this.z * l);
    }

    public double dot(Vector3D v){
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector3D normalize(){
        double len = Math.sqrt(this.x * this.x + this.y*this.y + this.z*this.z);

        return new Vector3D(this.x/len, this.y/len, this.z/len);
    }

    public static Vector3D fromPolar(double r, double theta, double gamma){
        return new Vector3D(r*Math.cos(theta)*Math.cos(gamma), r*Math.sin(theta)*Math.cos(gamma), r*Math.cos(theta)*Math.sin(gamma));
    }
}

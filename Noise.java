import java.util.Iterator;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Noise {

    public static long seed = 0;

    public static double getNoise(double x, double y){
        double fracX = x % 1;
        double fracY = y % 1;

        Vector2D tl = randVecAtIndex((int)x, (int)y);
        //tl = tl.normalize();
        //tl = tl.mult(2);

        Vector2D tr = randVecAtIndex((int)x+1, (int)y);
        //tr = tr.normalize();
        //tr = tr.mult(2);

        Vector2D bl = randVecAtIndex((int)x, (int)y+1);
        //bl = bl.normalize();
        //bl = bl.mult(2);

        Vector2D br = randVecAtIndex((int)x+1, (int)y+1);
        //br = br.normalize();
        //br = br.mult(2);

        double d1 = tl.dot(new Vector2D(fracX, fracY));

        double d2 = tr.dot(new Vector2D(fracX-1, fracY));

        double d3 = bl.dot(new Vector2D(fracX, fracY-1));

        double d4 = br.dot(new Vector2D(fracX-1, fracY-1));

        double interpalated = interp(interp(d1, d2, fracX), interp(d3, d4, fracX), fracY);

        //interpalated /= 1.42;

        return interpalated / 2 + 0.5;
    }

    public static Vector2D randVecAtIndex(int x, int y){
        Random randX = new Random(seed);
        Random randY = new Random(seed);

        DoubleStream xStream = randX.doubles().skip(x*2);
        //DoubleStream yStream = randY.doubles().skip(y);

        Iterator<Double> xIterator = xStream.iterator();
        //Iterator<Double> yIterator = yStream.iterator();

        //double x1 = xIterator.next();
        double x1 = xIterator.next();

        //double y1 = yIterator.next()*Math.PI*2;
        double y1 = xIterator.next()*Math.PI*2;

        return Vector2D.fromPolar(x1, y1);
        
    }

    private static double interp(double a, double b, double fac){
        return lerp(a, b, ((6*fac-15)*fac + 10)*fac*fac*fac);
    }

    private static double lerp(double a, double b, double fac){
        return a + fac * (b - a);
    }
}

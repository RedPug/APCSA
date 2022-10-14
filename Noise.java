import java.util.Random;
import java.util.stream.DoubleStream;

public class Noise {

    public static long seed = 0;
    static Random randX = new Random(seed);
    static Random randY = new Random(seed);

    public static double getNoise(double x, double y){
        double fracX = x % 1;
        double fracY = y % 1;

        //System.out.println(x + "," + fracX);

        double [] arrX = randX.doubles((int)(x+2)).toArray();
        double [] arrY = randY.doubles((int)(y+2)).toArray();

        Vector2D[] vecs = randVecAtIndex((int)x, (int)y);

        Vector2D tl = vecs[0];

        //tl = tl.normalize();
        tl = tl.mult(2);

        Vector2D tr = vecs[1];
        //tr = tr.normalize();
        tr = tr.mult(2);

        Vector2D bl = vecs[2];
        //bl = bl.normalize();
        bl = bl.mult(2);

        Vector2D br = vecs[3];
        //br = br.normalize();
        br = br.mult(2);

        double d1 = tl.dot(new Vector2D(fracX, fracY));

        double d2 = tr.dot(new Vector2D(1-fracX, fracY));

        double d3 = bl.dot(new Vector2D(fracX, 1-fracY));

        double d4 = br.dot(new Vector2D(1-fracX, 1-fracY));

        double interpalated = interp(interp(d1, d2, fracX),interp(d3, d4, fracX), fracY);

        interpalated /= 1.42;

        return interpalated / 2 + 0.5;
    }

    private static Vector2D[] randVecAtIndex(int x, int y){
        DoubleStream xStream = randX.doubles().skip(x);
        DoubleStream yStream = randY.doubles().skip(y);

        double x1 = xStream.findFirst().getAsDouble() - 0.5;
        //xStream = xStream.skip(1);
        double x2 = xStream.findFirst().getAsDouble() - 0.5;

        double y1 = yStream.findFirst().getAsDouble() - 0.5;
        //yStream = yStream.skip(1);
        double y2 = yStream.findFirst().getAsDouble() - 0.5;

        return new Vector2D[]{
            new Vector2D(x1, y1),
            new Vector2D(x2, y1),
            new Vector2D(x1, y2),
            new Vector2D(x2, y2)
        };
    }

    private static double interp(double a, double b, double fac){
        return lerp(a, b, ((6*fac-15)*fac + 10)*fac*fac*fac);
    }

    private static double lerp(double a, double b, double fac){
        return a + fac * (b - a);
    }
}

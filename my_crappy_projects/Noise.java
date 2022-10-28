package my_crappy_projects;

import java.util.HashMap;
import java.util.Random;

public class Noise {

    public static long seed = 0;

    public static double getNoiseDEP(double x, double y){
        //System.out.println(randAtCoord((int)x, (int)y));
        double fracX = x % 1;
        double fracY = y % 1;

        Vector2D tl = randVec2DAtIndex((int)x, (int)y);
        //tl = tl.normalize();
        //tl = tl.mult(2);

        Vector2D tr = randVec2DAtIndex((int)x+1, (int)y);
        //tr = tr.normalize();
        //tr = tr.mult(2);

        Vector2D bl = randVec2DAtIndex((int)x, (int)y+1);
        //bl = bl.normalize();
        //bl = bl.mult(2);

        Vector2D br = randVec2DAtIndex((int)x+1, (int)y+1);
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

    public static double getNoise(double x, double y, double z){
        double fracX = x % 1;
        double fracY = y % 1;
        double fracZ = z % 1;

        Vector3D tl1 = randVecAtIndex((int)x, (int)y, (int)z);

        Vector3D tr1 = randVecAtIndex((int)x+1, (int)y, (int)z);

        Vector3D bl1 = randVecAtIndex((int)x, (int)y+1, (int)z);

        Vector3D br1 = randVecAtIndex((int)x+1, (int)y+1, (int)z);

        Vector3D tl2 = randVecAtIndex((int)x, (int)y, (int)z+1);

        Vector3D tr2 = randVecAtIndex((int)x+1, (int)y, (int)z+1);

        Vector3D bl2 = randVecAtIndex((int)x, (int)y+1, (int)z+1);

        Vector3D br2 = randVecAtIndex((int)x+1, (int)y+1, (int)z+1);


        double d1 = tl1.dot(new Vector3D(fracX, fracY, fracZ));

        double d2 = tr1.dot(new Vector3D(fracX-1, fracY, fracZ));

        double d3 = bl1.dot(new Vector3D(fracX, fracY-1, fracZ));

        double d4 = br1.dot(new Vector3D(fracX-1, fracY-1, fracZ));


        double d5 = tl2.dot(new Vector3D(fracX, fracY, fracZ-1));

        double d6 = tr2.dot(new Vector3D(fracX-1, fracY, fracZ-1));

        double d7 = bl2.dot(new Vector3D(fracX, fracY-1, fracZ-1));

        double d8 = br2.dot(new Vector3D(fracX-1, fracY-1, fracZ-1));


        double interp1 = interp(interp(d1, d2, fracX), interp(d3, d4, fracX), fracY);
        double interp2 = interp(interp(d5, d6, fracX), interp(d7, d8, fracX), fracY);

        double interpalated = interp(interp1, interp2, fracZ);

        //interpalated /= 1.42;

        return interpalated / 2 + 0.5;
    }

    public static double slowGetNoise(double x, double y, double z){
        double fracX = x % 1;
        double fracY = y % 1;
        double fracZ = z % 1;

        Vector3D tl1 = slowRandVecAtIndex((int)x, (int)y, (int)z);

        Vector3D tr1 = slowRandVecAtIndex((int)x+1, (int)y, (int)z);

        Vector3D bl1 = slowRandVecAtIndex((int)x, (int)y+1, (int)z);

        Vector3D br1 = slowRandVecAtIndex((int)x+1, (int)y+1, (int)z);

        Vector3D tl2 = slowRandVecAtIndex((int)x, (int)y, (int)z+1);

        Vector3D tr2 = slowRandVecAtIndex((int)x+1, (int)y, (int)z+1);

        Vector3D bl2 = slowRandVecAtIndex((int)x, (int)y+1, (int)z+1);

        Vector3D br2 = slowRandVecAtIndex((int)x+1, (int)y+1, (int)z+1);


        double d1 = tl1.dot(new Vector3D(fracX, fracY, fracZ));

        double d2 = tr1.dot(new Vector3D(fracX-1, fracY, fracZ));

        double d3 = bl1.dot(new Vector3D(fracX, fracY-1, fracZ));

        double d4 = br1.dot(new Vector3D(fracX-1, fracY-1, fracZ));


        double d5 = tl2.dot(new Vector3D(fracX, fracY, fracZ-1));

        double d6 = tr2.dot(new Vector3D(fracX-1, fracY, fracZ-1));

        double d7 = bl2.dot(new Vector3D(fracX, fracY-1, fracZ-1));

        double d8 = br2.dot(new Vector3D(fracX-1, fracY-1, fracZ-1));


        double interp1 = interp(interp(d1, d2, fracX), interp(d3, d4, fracX), fracY);
        double interp2 = interp(interp(d5, d6, fracX), interp(d7, d8, fracX), fracY);

        double interpalated = interp(interp1, interp2, fracZ);

        //interpalated /= 1.42;

        return interpalated / 2 + 0.5;
    }

    public static Vector2D randVec2DAtIndex(int x, int y){
        return Vector2D.fromPolar(randAtCoord(x, y, 32423), randAtCoord(x, y, 2135334)*Math.PI*2);
    }

    public static Vector3D randVecAtIndex(int x, int y, int z){
        return Vector3D.fromPolar(
            randAtCoord(x, y, z, 32423),
            randAtCoord(x, y, z, 2135334)*Math.PI*2,
            randAtCoord(x, y, z, 46434512)*Math.PI*2
        );
    }

    public static Vector3D slowRandVecAtIndex(int x, int y, int z){
        return Vector3D.fromPolar(
            slowRandAtCoord(x, y, z, 32423),
            slowRandAtCoord(x, y, z, 2135334)*Math.PI*2,
            slowRandAtCoord(x, y, z, 46434512)*Math.PI*2
        );
    }

    public static double randAtCoord(int x, int y, int z){
        return randAtCoord(x, y, z, 0);
    }

    private static HashMap<Integer[], Double> randCoordMap = new HashMap<Integer[], Double>();

    private static double randAtCoord(int x, int y, int z, int w){
        Integer[] key = new Integer[]{x,y,z,w};
        if(randCoordMap.containsKey(key)){
            return randCoordMap.get(new Integer[]{x,y,z,w});
        }

        int a = 749059057;
        int b = 1549059893;
        int c = 1049059909;
        int d = 2049061979;
        int e = 520833273;

        Random r1 = new Random(x + seed);
        Random r2 = new Random(r1.nextLong() * y + seed);
        Random r3 = new Random(z + seed + r2.nextLong() + r1.nextLong());
        Random r4 = new Random(w + seed + r3.nextLong() + r2.nextLong() + r1.nextLong());

        Random r = new Random((a*r1.nextLong() + b*r2.nextLong() + c*r3.nextLong() + d*r4.nextLong() + e)^seed);

        double value = r.nextDouble();

        if(!randCoordMap.containsKey(key)){
            randCoordMap.put(key, value);
        }

        return value;
    }

    private static double slowRandAtCoord(int x, int y, int z, int w){
        int a = 749059057;
        int b = 1549059893;
        int c = 1049059909;
        int d = 2049061979;
        int e = 520833273;

        Random r1 = new Random(x + seed);
        Random r2 = new Random(r1.nextLong() * y + seed);
        Random r3 = new Random(z + seed + r2.nextLong() + r1.nextLong());
        Random r4 = new Random(w + seed + r3.nextLong() + r2.nextLong() + r1.nextLong());

        Random r = new Random((a*r1.nextLong() + b*r2.nextLong() + c*r3.nextLong() + d*r4.nextLong() + e)^seed);

        return r.nextDouble();
    }

    public static double interp(double a, double b, double fac){
        return lerp(a, b, ((6*fac-15)*fac + 10)*fac*fac*fac);
    }

    public static double lerp(double a, double b, double fac){
        return a + fac * (b - a);
    }
}

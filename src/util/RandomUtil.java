package util;

import java.util.Random;

public class RandomUtil {
    static Random random = new Random();

    public static int nextInt(int x, int y) {
        return (int)nextDouble(x,y);
    }

    public static double nextDouble(double x, double y) {
        return random.nextDouble() * (y - x + 1) + x;
    }
}

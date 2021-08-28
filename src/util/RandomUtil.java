package util;

import java.util.Random;

public class RandomUtil {
    static Random random = new Random();

    public static int nextInt(int a, int b) {
        return (int)nextDouble(a,b);
    }

    public static double nextDouble(double a, double b) {
        return random.nextDouble() * (b - a + 1) + a;
    }
}

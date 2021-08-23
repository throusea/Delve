package util;

public class NextUtil {

    public static int next(int value, int x, int y) {
        value++;
        if(value == y + 1) value = x;
        return value;
    }
}

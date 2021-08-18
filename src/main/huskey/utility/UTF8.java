package utility;

import java.util.stream.IntStream;

public class UTF8 {
    public static String getStringsInRange(int head, int tail) {
        int[] ints = IntStream.range(head, tail + 1).toArray();

        StringBuilder result = new StringBuilder();
        for (int anInt : ints) {
            result.append((char) anInt);
        }

        return result.toString();
    }
}

package utility;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class UTF8 {
    public static ArrayList<String> getStringsInRange(int head, int tail) {
        int[] ints = IntStream.range(head, tail + 1).toArray();

        ArrayList<String> result = new ArrayList<>();
        for (int anInt : ints) {
            result.add(String.valueOf((char) anInt));
        }

        return result;
    }
}

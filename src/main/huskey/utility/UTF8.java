package utility;

import java.util.stream.IntStream;

/**
 * UTF8 に関連したメソッド群
 *
 * @author いっぺー
 */
public class UTF8 {
    /**
     * 文字コード (decimal) を範囲指定し、対象の文字を配列で取得する
     *
     * <p>始端と終端を共に含む文字列を返す。
     * 例えば、"head = 97 (UTF-8における'a'), tail = 122 (UTF-8における'z')" とすると、
     * a-zをすべて結合した文字列を得ることができる。
     *
     * @param head 始端
     * @param tail 終端
     * @return String
     * @author いっぺー
     */
    public static String getStringsInRange(int head, int tail) {
        int[] ints = IntStream.range(head, tail + 1).toArray();

        StringBuilder result = new StringBuilder();
        for (int anInt : ints) {
            result.append((char) anInt);
        }

        return result.toString();
    }
}

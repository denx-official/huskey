package xml.database;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CharSetTest {
    CharSet charSet;

    @Test
    void 適切な文字列を取得できるか() {
        charSet =  new CharSet(
                "false",
                "false",
                "true",
                "false",
                "true",
                ""
        );
        String[] expected = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " "};
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void 重複が削除されているか() {
        charSet = new CharSet(
                "false",
                "false",
                "true",
                "false",
                "true",
                "01 "
        );
        String[] expected = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " "};
        System.out.println(Arrays.toString(charSet.toStrings()));
        assertArrayEquals(expected, charSet.toStrings());
    }
}
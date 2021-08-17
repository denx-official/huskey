package xml.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharSetTest {
    CharSet charSet;
    final String lowercase = "abcdefghijklmnopqrstuvwxyz";
    final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numbers = "0123456789";
    final String symbols = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    @Test
    void lowerCaseのみ取得() {
        charSet =  new CharSet(
                "true",
                "false",
                "false",
                "false",
                "false",
                ""
        );
        String[] expected = lowercase.split("");
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void upperCaseのみ取得() {
        charSet =  new CharSet(
                "false",
                "true",
                "false",
                "false",
                "false",
                ""
        );
        String[] expected = uppercase.split("");
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void 数字のみ取得() {
        charSet =  new CharSet(
                "false",
                "false",
                "true",
                "false",
                "false",
                ""
        );
        String[] expected = numbers.split("");
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void 記号のみ取得() {
        charSet =  new CharSet(
                "false",
                "false",
                "false",
                "true",
                "false",
                ""
        );
        String[] expected = symbols.split("");
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void スペースのみ取得() {
        charSet =  new CharSet(
                "false",
                "false",
                "false",
                "false",
                "true",
                ""
        );
        String[] expected = new String[]{" "};
        assertArrayEquals(expected, charSet.toStrings());
    }

    @Test
    void 全ての文字が結合されているか() {
        charSet = new CharSet(
                "true",
                "true",
                "true",
                "true",
                "true",
                ""
        );
        String all = lowercase + uppercase + numbers + symbols + " ";
        String[] expected = all.split("");
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
                "41 "
        );
        String[] expected = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " "};
        assertArrayEquals(expected, charSet.toStrings());
    }
}
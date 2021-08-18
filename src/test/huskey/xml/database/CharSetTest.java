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
        assertEquals(lowercase, charSet.toString());
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
        assertEquals(uppercase, charSet.toString());
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
        assertEquals(numbers, charSet.toString());
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
        assertEquals(symbols, charSet.toString());
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
        assertEquals(" ", charSet.toString());
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
        String expected = lowercase + uppercase + numbers + symbols + " ";
        assertEquals(expected, charSet.toString());
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
        String expected = numbers + " ";
        assertEquals(expected, charSet.toString());
    }
}
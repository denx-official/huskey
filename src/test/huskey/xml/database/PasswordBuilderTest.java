package xml.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtil.GlobalConst;
import testUtil.StandardInputStream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordBuilderTest {
    PasswordBuilder builder;
    Database db = new DatabaseBuilder(
            GlobalConst.dbName,
            GlobalConst.masterKey,
            GlobalConst.huskeyDir
    ).build();

    String joinField(Password passwd) {
        return passwd.passLength + passwd.charSet.toString();
    }

    StandardInputStream in = new StandardInputStream();

    @BeforeEach
    void setup () {
        System.setIn(in);
    }

    @AfterEach
    void cleanUp() {
        System.setIn(null);
    }

    @Test
    void Passwordインスタンスの生成() {
        in.inputln("20");
        in.inputln("true");
        in.inputln("true");
        in.inputln("true");
        in.inputln("true");
        in.inputln("true");
        in.inputln("");

        builder = new PasswordBuilder(db, "settings");
        Password newPasswd = builder.build();
        String result = joinField(newPasswd);

        CharSet charSet = new CharSet(
                "true",
                "true",
                "true",
                "true",
                "true",
                ""
        );
        Password passwd = new Password(
                "20",
                charSet,
                ""
        );
        String expected = joinField(passwd);
        assertEquals(expected, result);
    }
}
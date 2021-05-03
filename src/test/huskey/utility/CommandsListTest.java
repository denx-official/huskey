package utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;


class CommandsListTest {
    private CommandsList cl;

    @BeforeEach
    void setup() {
        cl = new CommandsList();
    }

    @Nested
    class CheckCommands {
        @Test
        void コマンドが存在する場合() {
            boolean result = cl.checkCommands("init");
            assertEquals(true, result);
        }

        @Test
        void コマンドが存在しない場合() {
            boolean result = cl.checkCommands("hoge");
            assertEquals(false, result);
        }
    }
}
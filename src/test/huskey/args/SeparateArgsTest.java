package args;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class SeparateArgsTest {
    private SeparateArgs sepArgs = null;

    @AfterEach
    void cleanUpEach() {
        sepArgs = null;
    }

    @Nested
    class getCommand {
        @Test
        void commandを取得() {
            String[] args = {"get", "sample", "database", "-s"};

            sepArgs = new SeparateArgs(args);

            assertEquals("get", sepArgs.getCommand());
        }
    }

    @Nested
    class getValues {
        @Test
        void valuesを取得_valuesあり_optionsあり() {
            String[] args = {"get", "sample", "database", "-s"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {"sample", "database"};
            assertArrayEquals(expected, sepArgs.getValues());
        }

        @Test
        void valuesを取得_valuesあり_optionsなし() {
            String[] args = {"get", "sample", "database"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {"sample", "database"};
            assertArrayEquals(expected, sepArgs.getValues());
        }

        @Test
        void valuesを取得_valuesなし_optionsあり() {
            String[] args = {"database", "--delete", "hoge"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {""};
            assertArrayEquals(expected, sepArgs.getValues());
        }

        @Test
        void valuesを取得_valuesなし_optionsなし() {
            String[] args = {"help"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {""};
            assertArrayEquals(expected, sepArgs.getValues());
        }
    }

    @Nested
    class getOptions {
        @Test
        void optionsを取得_optionsあり() {
            String[] args = {"list", "-t", "--descend"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {"-t", "--descend"};
            assertArrayEquals(expected, sepArgs.getOptions());
        }

        @Test
        void optionsを取得_optionsなし() {
            String[] args = {"list"};

            sepArgs = new SeparateArgs(args);

            String[] expected = {""};
            assertArrayEquals(expected, sepArgs.getOptions());
        }
    }

    @Nested
    class 引数が空の時 {
        String[] emptyArray = {""};

        @BeforeEach
        void init() {
            sepArgs = new SeparateArgs(emptyArray);
        }

        @Test
        void commandを取得() {
            assertEquals("help", sepArgs.getCommand());
        }

        @Test
        void valuesを取得() {
            assertArrayEquals(emptyArray, sepArgs.getValues());
        }

        @Test
        void optionsを取得() {
            assertArrayEquals(emptyArray, sepArgs.getOptions());
        }
    }
}
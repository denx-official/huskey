package utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class SeparateArgsTest {
    private SeparateArgs sepArgs;

    @Nested
    class GetCommand {
        @Test
        void commandを取得() {
            String[] args = {"get", "sample", "database", "-s"};

            sepArgs = new SeparateArgs(args);

            assertEquals("get", sepArgs.getCommand());
        }
    }

    @Nested
    class GetValues {
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
    class GetOptions {
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
}
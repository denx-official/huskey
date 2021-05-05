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
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            assertEquals("get", hkArgs.getCommand());
        }
    }

    @Nested
    class GetValues {
        @Test
        void valuesを取得_valuesあり_optionsあり() {
            String[] args = {"get", "sample", "database", "-s"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {"sample", "database"};
            String[] result = hkArgs.getValues();
            assertArrayEquals(expected, result);
        }

        @Test
        void valuesを取得_valuesあり_optionsなし() {
            String[] args = {"get", "sample", "database"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {"sample", "database"};
            String[] result = hkArgs.getValues();
            assertArrayEquals(expected, result);
        }

        @Test
        void valuesを取得_valuesなし_optionsあり() {
            String[] args = {"database", "--delete", "hoge"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {""};
            String[] result = hkArgs.getValues();
            assertArrayEquals(expected, result);
        }

        @Test
        void valuesを取得_valuesなし_optionsなし() {
            String[] args = {"help"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {""};
            String[] result = hkArgs.getValues();
            assertArrayEquals(expected, result);
        }
    }

    @Nested
    class GetOptions {
        @Test
        void optionsを取得_optionsあり() {
            String[] args = {"list", "-t", "--descend"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {"-t", "--descend"};
            String[] result = hkArgs.getOptions();
            assertArrayEquals(expected, result);
        }

        @Test
        void optionsを取得_optionsなし() {
            String[] args = {"list"};

            sepArgs = new SeparateArgs(args);
            HuskeyArgs hkArgs = sepArgs.getHuskeyArgs();

            String[] expected = {""};
            String[] result = hkArgs.getOptions();
            assertArrayEquals(expected, result);
        }
    }
}
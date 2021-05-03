package util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class SeparateArgsTest {
    private SeparateArgs separateArgs;

    @Nested
    class 正常系 {

        @Nested
        class GetCommand {
            @Test
            void commandを取得() {
                String[] args = {"get", "sample", "database", "-s"};
                separateArgs = new SeparateArgs(args);

                String command = separateArgs.getCommand();
                assertEquals("get", command);
            }
        }

        @Nested
        class GetValues {
            @Test
            void valuesを取得_valuesあり_optionsあり() {
                String[] args = {"get", "sample", "database", "-s"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {"sample", "database"};
                String[] result = separateArgs.getValues();
                assertArrayEquals(expected, result);
            }

            @Test
            void valuesを取得_valuesあり_optionsなし() {
                String[] args = {"get", "sample", "database"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {"sample", "database"};
                String[] result = separateArgs.getValues();
                assertArrayEquals(expected, result);
            }

            @Test
            void valuesを取得_valuesなし_optionsあり() {
                String[] args = {"database", "--delete", "hoge"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {""};
                String[] result = separateArgs.getValues();
                assertArrayEquals(expected, result);
            }

            @Test
            void valuesを取得_valuesなし_optionsなし() {
                String[] args = {"help"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {""};
                String[] result = separateArgs.getValues();
                assertArrayEquals(expected, result);
            }
        }

        @Nested
        class GetOptions {
            @Test
            void optionsを取得_optionsあり() {
                String[] args = {"list", "-t", "--descend"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {"-t", "--descend"};
                String[] result = separateArgs.getOptions();
                assertArrayEquals(expected, result);
            }

            @Test
            void optionsを取得_optionsなし() {
                String[] args = {"list"};
                separateArgs = new SeparateArgs(args);

                String[] expected = {""};
                String[] result = separateArgs.getOptions();
                assertArrayEquals(expected, result);
            }
        }
    }

    @Nested
    class 異常系 {
        @Test
        void 第1引数に適切なcommandが与えられなかった場合() {
            String[] args = {"-s", "get", "sample", "database"};
            separateArgs = new SeparateArgs(args);

            assertThrows(IllegalArgumentException.class, separateArgs::getCommand);
        }
    }
}
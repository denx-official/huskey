package utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class CheckArgsTest {
    private SeparateArgs sepArgs;
    private HuskeyArgs hkArgs;

    @Nested
    class CommandTest {
        @Test
        void 適切なcommand名が与えられなかった場合() {
            String[] args = {"hoge", "fuga"};

            sepArgs = new SeparateArgs(args);
            hkArgs = sepArgs.getHuskeyArgs();

            CheckArgs checkArgs = new CheckArgs(hkArgs);
            assertThrows(IllegalArgumentException.class, checkArgs::run);
        }
    }

    @Nested
    class ValuesTest {

    }

    @Nested
    class OptionsTest {

    }
}
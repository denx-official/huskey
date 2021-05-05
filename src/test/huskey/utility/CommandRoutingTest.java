package utility;

import org.junit.jupiter.api.Test;
import types.HuskeyArgs;

import static org.junit.jupiter.api.Assertions.*;

class CommandRoutingTest {
    private CommandRouting cr;

    @Test
    void 適切なコマンドが与えられなかった場合() {
        String[] args = {"hoge", "fuga"};

        SeparateArgs sepArgs = new SeparateArgs(args);
        HuskeyArgs hkArgs = new HuskeyArgs(
            sepArgs.getCommand(),
            sepArgs.getValues(),
            sepArgs.getOptions()
        );

        cr = new CommandRouting(hkArgs);
        assertThrows(IllegalArgumentException.class, cr::run);
    }
}
package cmd;

import org.junit.jupiter.api.Test;
import utility.SeparateArgs;

import static org.junit.jupiter.api.Assertions.*;

class CommandRoutingTest {
    private CommandRouting cr;

    @Test
    void 適切なコマンドが与えられなかった場合() {
        String[] args = {"hoge", "fuga"};
        SeparateArgs sepArgs = new SeparateArgs(args);
        cr = new CommandRouting(
            sepArgs.getCommand(),
            sepArgs.getValues(),
            sepArgs.getOptions()
        );
        assertThrows(IllegalArgumentException.class, cr::run);
    }
}
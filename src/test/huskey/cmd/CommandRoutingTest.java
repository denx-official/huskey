package cmd;

import org.junit.jupiter.api.Test;
import utility.HuskeyException;
import utility.SeparateArgs;

import static org.junit.jupiter.api.Assertions.*;

class CommandRoutingTest {
    @Test
    void 適切なコマンドが与えられなかった場合() {
        String[] args = {"hoge", "fuga"};
        SeparateArgs sepArgs = new SeparateArgs(args);
        CommandRouting cr = new CommandRouting(
                sepArgs.getCommand(),
                sepArgs.getValues(),
                sepArgs.getOptions()
        );
        assertThrows(HuskeyException.class, cr::_run);
    }
}
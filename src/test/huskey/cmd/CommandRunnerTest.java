package cmd;

import org.junit.jupiter.api.Test;
import utility.HuskeyRuntimeException;

import static org.junit.jupiter.api.Assertions.*;

class CommandRunnerTest {
    @Test
    void 適切なコマンドが与えられなかった場合() {
        CommandRunner cr = new CommandRunner(
                "hoge",
                new String[]{""},
                new String[]{""}
        );
        assertThrows(HuskeyRuntimeException.class, cr::_run);
    }
}
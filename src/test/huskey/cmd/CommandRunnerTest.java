package cmd;

import args.HkArgs;
import org.junit.jupiter.api.Test;
import utility.HuskeyRuntimeException;

import static org.junit.jupiter.api.Assertions.*;

class CommandRunnerTest {
    @Test
    void 適切なコマンドが与えられなかった場合() {
        HkArgs hkArgs = new HkArgs(
                "hoge",
                new String[]{""},
                new String[]{""}
        );
        CommandRunner cr = new CommandRunner(hkArgs);
        assertThrows(HuskeyRuntimeException.class, cr::_run);
    }
}
package cmd;

import org.junit.jupiter.api.Test;
import utility.HuskeyRuntimeException;

import static org.junit.jupiter.api.Assertions.*;

class CommandRoutingTest {
    @Test
    void 適切なコマンドが与えられなかった場合() {
        CommandRouting cr = new CommandRouting(
                "hoge",
                new String[]{""},
                new String[]{""}
        );
        assertThrows(HuskeyRuntimeException.class, cr::_run);
    }
}
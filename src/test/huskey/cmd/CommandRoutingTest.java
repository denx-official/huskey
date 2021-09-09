package cmd;

import org.junit.jupiter.api.Test;
import utility.HuskeyException;

import static org.junit.jupiter.api.Assertions.*;

class CommandRoutingTest {
    @Test
    void 適切なコマンドが与えられなかった場合() {
        CommandRouting cr = new CommandRouting(
                "hoge",
                new String[]{""},
                new String[]{""},
                null
        );
        assertThrows(HuskeyException.class, cr::_run);
    }
}
package utility;

import java.util.Arrays;

public class CommandsList {
    private String[] commandsList = {
        "help",
        "init",
        "change",
        "database",
        "switch",
        "merge",
        "export",
        "import",
        "list",
        "search",
        "get",
        "set",
        "remove"
    };

    boolean checkCommands(String command) {
        return Arrays.asList(this.commandsList).contains(command);
    }
}

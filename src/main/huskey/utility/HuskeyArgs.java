package utility;

public class HuskeyArgs {
    String command;
    String[] values;
    String[] options;

    public HuskeyArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }
}

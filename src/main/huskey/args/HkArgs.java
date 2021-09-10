package args;

public class HkArgs {
    public final String command;
    public final String[] values;
    public final String[] options;

    public HkArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }
}

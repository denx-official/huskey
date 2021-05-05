package utility;

public class HuskeyArgs {
    private String command;
    private String[] values;
    private String[] options;

    public HuskeyArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    public String getCommand() {
        return this.command;
    }

    public String[] getValues() {
        return this.values;
    }

    public String[] getOptions() {
        return this.options;
    }
}

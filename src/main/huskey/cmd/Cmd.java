package cmd;

abstract class Cmd {
    protected String[] values;
    protected String[] options;

    public Cmd(String[] values, String[] options) {
        this.values = values;
        this.options = options;
    }

    abstract void run();
}

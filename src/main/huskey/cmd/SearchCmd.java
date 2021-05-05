package cmd;

public class SearchCmd extends Cmd {
    public SearchCmd(String[] values, String[] options) {
        super(values, options);
    }

    public void run() {
        System.out.println("run search");
        System.out.println("values: " + this.values);
        System.out.println("options: " + this.options);
    }
}
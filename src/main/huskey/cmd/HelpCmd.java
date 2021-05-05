package cmd;

public class HelpCmd extends Cmd {
    public HelpCmd(String[] values, String[] options) {
        super(values, options);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String values_str = String.join(", ", this.values);
        String options_str = String.join(", ", this.options);

        System.out.println("run help");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}

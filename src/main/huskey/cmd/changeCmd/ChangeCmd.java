package cmd.changeCmd;

import cmd.Cmd;

public class ChangeCmd extends Cmd {
    public ChangeCmd(String command, String[] values, String[] options) {
        super(command, values, options);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String values_str = String.join(", ", this.values);
        String options_str = String.join(", ", this.options);

        System.out.println("run change");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}

package cmd.helpCmd;

import cmd.Cmd;
import types.HuskeyArgs;

public class HelpCmd extends Cmd {
    public HelpCmd(HuskeyArgs hkArgs) {
        super(hkArgs);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String[] values = this.hkArgs.getValues();
        String[] options = this.hkArgs.getOptions();
        String values_str = String.join(", ", values);
        String options_str = String.join(", ", options);

        System.out.println("run help");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}
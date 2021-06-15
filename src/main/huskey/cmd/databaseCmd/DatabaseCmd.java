package cmd.databaseCmd;

import cmd.Cmd;
import types.HuskeyArgs;

public class DatabaseCmd extends Cmd {
    public DatabaseCmd(HuskeyArgs hkArgs) {
        super(hkArgs);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String[] values = this.hkArgs.getValues();
        String[] options = this.hkArgs.getOptions();
        String values_str = String.join(", ", values);
        String options_str = String.join(", ", options);

        System.out.println("run database");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}
package cmd;

import utility.HuskeyArgs;

public class RemoveCmd extends Cmd {
    public RemoveCmd(HuskeyArgs hkArgs) {
        super(hkArgs);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String[] values = this.hkArgs.getValues();
        String[] options = this.hkArgs.getOptions();
        String values_str = String.join(", ", values);
        String options_str = String.join(", ", options);
        
        System.out.println("run remove");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}

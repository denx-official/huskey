package cmd.helpCmd;

import cmd.Cmd;

public class HelpCmd implements Cmd {
    public HelpCmd() {
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run help command.");
    }
}

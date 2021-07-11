package cmd.helpCmd;

import cmd.Cmd;

public class HelpCmd implements Cmd {
    private final String huskeyDir;

    public HelpCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run help command.");
    }
}

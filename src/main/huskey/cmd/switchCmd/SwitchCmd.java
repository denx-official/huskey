package cmd.switchCmd;

import cmd.Cmd;

public class SwitchCmd implements Cmd {
    private final String huskeyDir;

    public SwitchCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run switch command.");
    }
}

package cmd.initCmd;

import cmd.Cmd;

public class InitCmd implements Cmd {
    private final String huskeyDir;

    public InitCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run init command.");
    }
}

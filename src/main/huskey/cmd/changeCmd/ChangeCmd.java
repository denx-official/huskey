package cmd.changeCmd;

import cmd.Cmd;

public class ChangeCmd implements Cmd {
    private final String huskeyDir;

    public ChangeCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run change command.");
    }
}

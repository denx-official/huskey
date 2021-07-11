package cmd.setCmd;

import cmd.Cmd;

public class SetCmd implements Cmd {
    private final String huskeyDir;

    public SetCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run set command.");
    }
}

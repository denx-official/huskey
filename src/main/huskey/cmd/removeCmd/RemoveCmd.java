package cmd.removeCmd;

import cmd.Cmd;

public class RemoveCmd implements Cmd {
    private final String huskeyDir;

    public RemoveCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run remove command.");
    }
}

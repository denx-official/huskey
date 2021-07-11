package cmd.listCmd;

import cmd.Cmd;

public class ListCmd implements Cmd {
    private final String huskeyDir;

    public ListCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run list command.");
    }
}

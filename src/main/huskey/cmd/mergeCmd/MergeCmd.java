package cmd.mergeCmd;

import cmd.Cmd;

public class MergeCmd implements Cmd {
    private final String huskeyDir;

    public MergeCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run merge command.");
    }
}

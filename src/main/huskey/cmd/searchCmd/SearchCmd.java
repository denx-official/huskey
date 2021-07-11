package cmd.searchCmd;

import cmd.Cmd;

public class SearchCmd implements Cmd {
    private final String huskeyDir;

    public SearchCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run search command.");
    }
}
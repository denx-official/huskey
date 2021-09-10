package cmd.listCmd;

import cmd.Cmd;

public class ListCmd implements Cmd {
    public ListCmd() {
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run list command.");
    }
}

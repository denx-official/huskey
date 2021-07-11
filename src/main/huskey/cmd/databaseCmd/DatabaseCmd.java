package cmd.databaseCmd;

import cmd.Cmd;

public class DatabaseCmd implements Cmd {
    private final String huskeyDir;

    public DatabaseCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run xml.database command.");
    }
}

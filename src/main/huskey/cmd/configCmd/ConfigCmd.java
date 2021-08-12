package cmd.configCmd;

import cmd.Cmd;

public class ConfigCmd implements Cmd {
    private final String huskeyDir;

    public ConfigCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run config command.");
    }
}

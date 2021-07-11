package cmd.getCmd;

import cmd.Cmd;

public class GetCmd implements Cmd {
    private final String huskeyDir;

    public GetCmd(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        // ここにコマンドの機能を実装する
        System.out.println("Run get command.");
    }
}

package cmd.setCmd;

import args.HkArgs;
import cmd.Cmd;
import cmd.UseDatabase;
import utility.HiddenInput;
import xml.database.Database;
import xml.database.Password;
import xml.database.PasswordBuilder;

public class SetCmd implements Cmd {
    private final HkArgs hkArgs;
    private final String huskeyDir;

    HiddenInput input = new HiddenInput();
    PasswordBuilder passwdBuilder;

    public SetCmd(HkArgs hkArgs, String huskeyDir) {
        this.hkArgs = hkArgs;
        this.huskeyDir = huskeyDir;
    }

    public void run() {
        String masterKey = this.readMasterKey();
        System.out.println(masterKey);

        String dbName = this.hkArgs.dbName(this.huskeyDir);
        Database db = UseDatabase.useDB(dbName, masterKey, this.huskeyDir);
        Password pw = this.buildPasswd(db, this.hkArgs.values[0]);
        System.out.println(pw.charSet.toString());
    }

    Password buildPasswd (Database db, String settingsTarget) {
        this.passwdBuilder = new PasswordBuilder(db, settingsTarget);
        return this.passwdBuilder.build();
    }

    String readMasterKey() {
        return input.read("データベース " + this.hkArgs.dbName(this.huskeyDir) + " のパスワード: ");
    }
}

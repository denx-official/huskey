package xml.database;

import utility.HiddenInput;
import xml.config.Config;
import xml.config.ConfigBuilder;

public class DatabaseFactory {
    private final String cmd;
    private final String[] values;
    private final String[] options;
    private final String huskeyDir;

    public DatabaseFactory(String cmd, String[] values, String[] options, String huskeyDir) {
        this.cmd = cmd;
        this.values = values;
        this.options = options;
        this.huskeyDir = huskeyDir;
    }

    public DatabaseBuilder build() {
        String dbName = this.getDBName();
        String masterKey = HiddenInput.read("データベース " + dbName + " のパスワード: ");
        return new DatabaseBuilder(dbName, masterKey, this.huskeyDir);
    }

    public String getDBName() {
        if (this.cmd.equals("merge")) {
            return this.values[0];
        }

        for (String option : this.options) {
            if (option.contains("--db=")) {
                return option.replace("--db=", "");
            }
        }

        return getDefaultDBName();
    }

    private String getDefaultDBName() {
        ConfigBuilder builder = new ConfigBuilder(this.huskeyDir);
        Config config = builder.build();
        return config.searchNode("//defaultDB").getTextContent();
    }
}

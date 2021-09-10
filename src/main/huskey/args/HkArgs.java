package args;

import xml.config.Config;
import xml.config.ConfigBuilder;

public class HkArgs {
    public final String command;
    public final String[] values;
    public final String[] options;

    public HkArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    public String dbName(String huskeyDir) {
        if (this.command.equals("merge")) {
            return this.values[0];
        }

        for (String option : this.options) {
            if (option.contains("--db=")) {
                return option.replace("--db=", "");
            }
        }

        return getDefaultDBName(huskeyDir);
    }

    private String getDefaultDBName(String huskeyDir) {
        ConfigBuilder builder = new ConfigBuilder(huskeyDir);
        Config config = builder.build();
        return config.searchNode("//defaultDB").getTextContent();
    }
}

package utility;

import cmd.*;

public class CommandRouting {
    private String command;
    private String[] values;
    private String[] options;

    public CommandRouting(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    public void run() {
        switch (command) {
            case "help":
                HelpCmd helpCmd = new HelpCmd(this.values, this.options);
                helpCmd.run();
                break;

            case "init":
                InitCmd initCmd = new InitCmd(this.values, this.options);
                initCmd.run();
                break;

            case "change":
                ChangeCmd changeCmd = new ChangeCmd(this.values, this.options);
                changeCmd.run();
                break;

            case "database":
                DatabaseCmd databaseCmd = new DatabaseCmd(this.values, this.options);
                databaseCmd.run();
                break;

            case "switch":
                SwitchCmd switchCmd = new SwitchCmd(this.values, this.options);
                switchCmd.run();
                break;

            case "merge":
                MergeCmd mergeCmd = new MergeCmd(this.values, this.options);
                mergeCmd.run();
                break;

            case "export":
                ExportCmd exportCmd = new ExportCmd(this.values, this.options);
                exportCmd.run();
                break;

            case "import":
                ImportCmd importCmd = new ImportCmd(this.values, this.options);
                importCmd.run();
                break;

            case "list":
                ListCmd listCmd = new ListCmd(this.values, this.options);
                listCmd.run();
                break;

            case "search":
                SearchCmd searchCmd = new SearchCmd(this.values, this.options);
                searchCmd.run();
                break;

            case "get":
                GetCmd getCmd = new GetCmd(this.values, this.options);
                getCmd.run();
                break;

            case "set":
                SetCmd setCmd = new SetCmd(this.values, this.options);
                setCmd.run();
                break;

            case "remove":
                RemoveCmd removeCmd = new RemoveCmd(this.values, this.options);
                removeCmd.run();
                break;
        }
    }
}

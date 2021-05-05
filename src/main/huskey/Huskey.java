import cmd.*;
import utility.*;

public class Huskey {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("コマンドを入力してください");
            System.exit(0);
        }

        SeparateArgs separateArgs = new SeparateArgs(args);

        try {
            String command = separateArgs.getCommand();
            String[] values = separateArgs.getValues();
            String[] options = separateArgs.getOptions();

            run(command, values, options);
        }
        catch (IllegalArgumentException e) {
            System.out.println("適切なコマンドを指定してください。");
            System.exit(0);
        }
    }

    private static void run(String command, String[] values, String[] options) {
        switch (command) {
            case "help":
                HelpCmd helpCmd = new HelpCmd(values, options);
                helpCmd.run();
                break;

            case "init":
                InitCmd initCmd = new InitCmd(values, options);
                initCmd.run();
                break;

            case "change":
                ChangeCmd changeCmd = new ChangeCmd(values, options);
                changeCmd.run();
                break;

            case "database":
                DatabaseCmd databaseCmd = new DatabaseCmd(values, options);
                databaseCmd.run();
                break;

            case "switch":
                SwitchCmd switchCmd = new SwitchCmd(values, options);
                switchCmd.run();
                break;

            case "merge":
                MergeCmd mergeCmd = new MergeCmd(values, options);
                mergeCmd.run();
                break;

            case "export":
                ExportCmd exportCmd = new ExportCmd(values, options);
                exportCmd.run();
                break;

            case "import":
                ImportCmd importCmd = new ImportCmd(values, options);
                importCmd.run();
                break;

            case "list":
                ListCmd listCmd = new ListCmd(values, options);
                listCmd.run();
                break;

            case "search":
                SearchCmd searchCmd = new SearchCmd(values, options);
                searchCmd.run();
                break;

            case "get":
                GetCmd getCmd = new GetCmd(values, options);
                getCmd.run();
                break;

            case "set":
                SetCmd setCmd = new SetCmd(values, options);
                setCmd.run();
                break;

            case "remove":
                RemoveCmd removeCmd = new RemoveCmd(values, options);
                removeCmd.run();
                break;
        }
    }
}

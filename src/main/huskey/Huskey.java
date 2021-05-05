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
                Help help = new Help(values, options);
                help.run();
                break;

            case "init":
                Init init = new Init(values, options);
                init.run();
                break;

            case "change":
                Change change = new Change(values, options);
                change.run();
                break;

            case "database":
                Database database = new Database(values, options);
                database.run();
                break;

            case "switch":
                Switch switch_ = new Switch(values, options);
                switch_.run();
                break;

            case "merge":
                Merge merge = new Merge(values, options);
                merge.run();
                break;

            case "export":
                Export export = new Export(values, options);
                export.run();
                break;

            case "import":
                Import import_ = new Import(values, options);
                import_.run();
                break;

            case "list":
                List list = new List(values, options);
                list.run();
                break;

            case "search":
                Search search = new Search(values, options);
                search.run();
                break;

            case "get":
                Get get = new Get(values, options);
                get.run();
                break;

            case "set":
                Set set = new Set(values, options);
                set.run();
                break;

            case "remove":
                Remove remove = new Remove(values, options);
                remove.run();
                break;
        }
    }
}

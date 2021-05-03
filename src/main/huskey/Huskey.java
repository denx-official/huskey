import utility.*;

public class Huskey {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("コマンドを入力してください");
            System.exit(0);
        }

        SeparateArgs separateArgs = new SeparateArgs(args);

        String command = separateArgs.getCommand();
        String[] values = separateArgs.getValues();
        String[] options = separateArgs.getOptions();

        run(command, values, options);
    }

    private static void run(String command, String[] values, String[] options) {
        switch (command) {
            case "init":
                System.out.println("run init");
                break;

            case "change":
                System.out.println("run change");
                break;

            case "database":
                System.out.println("run database");
                break;

            case "switch":
                System.out.println("run switch");
                break;

            case "merge":
                System.out.println("run merge");
                break;

            case "export":
                System.out.println("run export");
                break;

            case "import":
                System.out.println("run import");
                break;

            case "list":
                System.out.println("run list");
                break;

            case "search":
                System.out.println("run search");
                break;

            case "get":
                System.out.println("run get");
                break;

            case "set":
                System.out.println("run set");
                break;

            case "remove":
                System.out.println("run remove");
                break;
        }
    }
}

import utility.*;

public class Huskey {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("コマンドを入力してください");
            System.exit(0);
        }

        try {
            SeparateArgs separateArgs = new SeparateArgs(args);

            String command = separateArgs.getCommand();
            String[] values = separateArgs.getValues();
            String[] options = separateArgs.getOptions();

            CommandRouting cr = new CommandRouting(command, values, options);
            cr.run();
        }
        catch (IllegalArgumentException e) {
            System.out.println("適切なコマンドを指定してください。");
            System.exit(0);
        }
    }
}

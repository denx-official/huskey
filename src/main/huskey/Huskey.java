import utility.*;

public class Huskey {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("コマンドを入力してください");
            System.exit(0);
        }

        try {
            SeparateArgs sepArgs = new SeparateArgs(args);

            HuskeyArgs huskeyArgs = sepArgs.getHuskeyArgs();

            CommandRouting cr = new CommandRouting(huskeyArgs);
            cr.run();
        }
        catch (IllegalArgumentException e) {
            System.out.println("適切なコマンドを指定してください。");
            System.exit(0);
        }
    }
}

import utility.*;

public class Huskey {
    public static void main(String[] _args) {
        String[] args;

        if (_args.length == 0) {
            // コマンドライン引数が空の場合、"huskey help" と同等のものとみなす
            args = new String[] {"help"};
        }
        else {
            args = _args;
        }

        SeparateArgs sepArgs = new SeparateArgs(args);
        HuskeyArgs huskeyArgs = sepArgs.getHuskeyArgs();
        CommandRouting cr = new CommandRouting(huskeyArgs);

        try {
            cr.run();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

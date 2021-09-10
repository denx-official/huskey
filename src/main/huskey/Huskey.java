import cmd.CommandRunner;
import utility.SeparateArgs;

/**
 * コマンドライン引数を分解 -> コマンドルーティングへ渡し、実行
 *
 * @author いっぺー
 */
public class Huskey {
    public static void main(String[] args) {
        SeparateArgs sepArgs = new SeparateArgs(args);
        CommandRunner cr = new CommandRunner(
                sepArgs.getCommand(),
                sepArgs.getValues(),
                sepArgs.getOptions()
        );
        cr.run();
    }
}

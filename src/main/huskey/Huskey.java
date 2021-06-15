import utility.*;
import types.HuskeyArgs;

/**
 * コマンドライン引数を分解・再構築 -> コマンドルーティングへ渡し、実行
 *
 * <p>コマンドライン引数をcommand, values, optionsに分解し、
 * HuskeyArgs型にまとめる。その後それをコマンドルーティングへ渡し、コマンドを実行する。
 *
 * @author いっぺー
 */
public class Huskey {
    public static void main(String[] args) {
        SeparateArgs sepArgs = new SeparateArgs(args);
        HuskeyArgs huskeyArgs = new HuskeyArgs(
            sepArgs.getCommand(),
            sepArgs.getValues(),
            sepArgs.getOptions()
        );
        CommandRouting cr = new CommandRouting(huskeyArgs);

        try {
            cr.run();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

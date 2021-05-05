import utility.*;
import types.HuskeyArgs;

/**
 * コマンドライン引数を分解・再構築 -> コマンドルーティングへ渡し、実行
 *
 * <p>コマンドライン引数をcommand, values, optionsに分解し、
 * HuskeyArgs型にまとめた後、コマンドルーティングへ渡し、コマンドを実行する。
 *
 * <p>コマンドライン引数が与えられなかった場合は、"huskey help" が与えられたものと見なす。
 * 
 * <p>（cr.run()のエラーハンドリングをmain関数で行っているが、これは仕様変更する可能性有）
 *
 * @author いっぺー
 */
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

import args.HkArgs;
import cmd.CommandRunner;
import args.SeparateArgs;

/**
 * コマンドライン引数を分解 -> CommandRunnerへ渡し、実行
 *
 * @author いっぺー
 */
public class Huskey {
    public static void main(String[] args) {
        SeparateArgs sepArgs = new SeparateArgs(args);
        HkArgs hkArgs = sepArgs.genHkArgs();
        CommandRunner cr = new CommandRunner(hkArgs);
        cr.run();
    }
}

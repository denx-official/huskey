package utility;

/**
 * コマンドライン引数の分解
 *
 * <p>コマンドライン引数を分解し、command, values, optionsをそれぞれのgetメソッドから取得する。
 *
 * @author いっぺー
 */
public class SeparateArgs {
    private final String[] args;

    public SeparateArgs(String[] args) {
        this.args = args;
    }

    /**
     * commandの取得
     *
     * <p>コマンドライン第一引数を返す（空の場合は "help" を返す）。
     *
     * @return String
     * @author いっぺー
     */
    public String getCommand() {
        if (this.args[0].equals("")) {
            return "help";
        }
        return this.args[0];
    }

    /**
     * valuesの取得
     *
     * <p>コマンドライン引数からvaluesを取得する。
     *
     * @return String[]
     * @author いっぺー
     */
    public String[] getValues() {
        int cutPoint = this.getCutPoint();

        if (cutPoint == -1) {
            return this.cutout(Target.VALUES, this.args.length);
        }

        return this.cutout(Target.VALUES, cutPoint);
    }

    /**
     * optionsの取得
     *
     * <p>コマンドライン引数からoptionsを取得する。
     *
     * @return String[]
     * @author いっぺー
     */
    public String[] getOptions() {
        int cutPoint = this.getCutPoint();

        if (cutPoint == -1) {
            return new String[] {""};
        }

        return this.cutout(Target.OPTIONS, cutPoint);
    }

    /**
     * cutoutメソッドによって取得する引数の種類
     *
     * @author いっぺー
     */
    private enum Target {
        VALUES,
        OPTIONS
    }

    /**
     * cutPoint（optionsが始まるインデックス）を基準にvalues/optionsを取得する
     *
     * <p>Target.VALUESの場合、コマンドライン引数の第二引数からcutPointまでの配列を返す。
     * valuesが存在しない（cutPointが1以下）場合、"" のみを要素に持つ配列を返す。
     *
     * <p>Target.OPTIONSの場合、コマンドライン引数のcutPointから最後までの配列を返す。
     *
     * @param target 取得する引数の種類（Target.VALUES or Target.OPTIONS）
     * @param cutPoint optionsが始まるインデックス
     * @return String[]
     * @author いっぺー
     */
    private String[] cutout(Target target, int cutPoint) {
        if (target == Target.VALUES && cutPoint - 1 <= 0) {
            return new String[] {""};
        }

        String[] result;
        int srcPos;

        if (target == Target.VALUES) {
            result = new String[cutPoint - 1];
            srcPos = 1;
        } else {
            result = new String[this.args.length - cutPoint];
            srcPos = cutPoint;
        }

        System.arraycopy(this.args, srcPos, result, 0, result.length);

        return result;
    }

    /**
     * cutPoint（optionsが始まるインデックス）を取得する
     *
     * <p>コマンドライン引数に "-" を含むポイントをoptionsが始まるインデックスとし、その値を返す。
     * ポイントが存在しなかった場合、-1を返す。
     *
     * @return int
     * @author いっぺー
     */
    private int getCutPoint() {
        for (int i = 0; i < this.args.length; i++) {
            // オプションを含むかを判定
            if (this.args[i].startsWith("-")) {
                return i;
            }
        }
        return -1;
    }
}

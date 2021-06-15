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
     * @return values
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
     * <p>コマンドライン引数からvaluesを取得する（取得法は <pre>cutoutValues</pre> を参照）。
     *
     * @return command
     * @author いっぺー
     */
    public String[] getValues() {
        String[] values;

        int argsLen = this.args.length;
        for (int i = 0; i < argsLen; i++) {
            // オプションを含むかを判定
            if (this.args[i].startsWith("-")) {
                values = cutoutValues(i);
                return values;
            }
        }

        values = cutoutValues(argsLen);
        return values;
    }

    /**
     * optionsの取得
     *
     * <p>コマンドライン引数からoptionsを取得する（取得法は <pre>cutoutOptions</pre> を参照）。
     *
     * @return options
     * @author いっぺー
     */
    public String[] getOptions() {
        String[] options;

        int argsLen = this.args.length;
        for (int i = 0; i < argsLen; i++) {
            // オプションを含むかを判定
            if (this.args[i].startsWith("-")) {
                options = cutoutOptions(argsLen, i);
                return options;
            }
        }

        options = new String[1];
        options[0] = "";

        return options;
    }

    /**
     * コマンドライン引数からvaluesを切り抜く
     *
     * <p>コマンドライン引数の二番目からvaluesが始まるため、そのポイントから
     * valuesが閉じるポイント（optionsが始まるポイント）までを切り抜くことでvaluesを取得する。
     *
     * <p>optionsが始まるポイントは、頭文字が "-" から始まる引数のインデックスとしている。
     *
     * <p>"-" から始まる引数が存在しない場合、それは与えられたコマンドライン引数に
     * optionsが存在しないことを意味するため、cutPointは引数の最後尾ということになる。
     *
     * <p>valuesが存在しない場合、すなわちvaluesの閉じるポイントと引数の二番目が同じだった場合、
     * "" のみを要素に持つ配列を返す。
     *
     * @param cutPoint valuesが閉じるポイント（optionsが始まるポイント）
     * @return values
     * @author いっぺー
     */
    private String[] cutoutValues(int cutPoint) {
        String[] values;

        if (cutPoint - 1 > 0) { // -1 はコマンド分の長さを引いている
            values = new String[cutPoint - 1];
            for (int i = 0; i < values.length; i++) {
                values[i] = this.args[i+1];
            }
        }
        else {
            values = new String[1];
            values[0] = "";
        }

        return values;
    }

    /**
     * コマンドライン引数からoptionsを切り抜く
     *
     * <p>optionsが始まるポイントを頭文字に "-" を持つ引数のインデックスとし、そこから
     * 引数の最後までを切り抜くことでoptionsを取得する。
     *
     * <p>optionsが存在しない場合、すなわち頭文字に "-" を持つ引数が存在しない場合、
     * "" のみを要素に持つ配列を返す。
     *
     * @param argsLen コマンドライン引数の長さ（optionsが閉じるポイント）
     * @param cutPoint optionsが始まるポイント
     * @return options
     * @author いっぺー
     */
    private String[] cutoutOptions(int argsLen, int cutPoint) {
        String[] options;

        options = new String[argsLen - cutPoint];
        for (int i = 0; i < options.length; i++) {
            options[i] = this.args[i + cutPoint];
        }

        return options;
    }
}

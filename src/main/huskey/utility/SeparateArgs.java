package utility;

public class SeparateArgs {
    private String[] args;

    public SeparateArgs(String[] args) {
        this.args = args;
    }

    public HuskeyArgs getHuskeyArgs() {
        String command = getCommand();
        String[] values = getValues();
        String[] options = getOptions();

        HuskeyArgs args = new HuskeyArgs(command, values, options);
        return args;
    }

    public String getCommand() {
        return this.args[0];
    }

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

    private String[] cutoutOptions(int argsLen, int cutPoint) {
        String[] options;

        options = new String[argsLen - cutPoint];
        for (int i = 0; i < options.length; i++) {
            options[i] = this.args[i + cutPoint];
        }

        return options;
    }
}

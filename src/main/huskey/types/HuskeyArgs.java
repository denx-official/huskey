package types;

/**
 * HuskeyArgs型
 *
 * <p>command, values, optionsをひとまとめとした独自型。
 *
 * <p>値の参照のみを許可するため、getterメソッドを導入している。
 *
 * @author  いっぺー
 */
public class HuskeyArgs {
    private final String command;
    private final String[] values;
    private final String[] options;

    public HuskeyArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    public String getCommand() {
        return this.command;
    }

    public String[] getValues() {
        return this.values;
    }

    public String[] getOptions() {
        return this.options;
    }
}

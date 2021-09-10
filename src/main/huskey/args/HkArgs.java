package args;

import xml.config.Config;
import xml.config.ConfigBuilder;

/**
 * 分解されたコマンドライン引数
 *
 * @author いっぺー
 */
public class HkArgs {
    /**
     * 実行するコマンド
     */
    public final String command;

    /**
     * 実行に必要な値
     */
    public final String[] values;

    /**
     * 指定された追加の機能
     */
    public final String[] options;

    public HkArgs(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    /**
     * データベース名の取得
     *
     * <p>コマンドライン引数からデータベース名を指定する場合、"--db=" というオプションを用いることで可能となる。
     * （ただし例外として、mergeコマンドはvaluesの値にデータベース名を取る）
     *
     * <p>データベース名が省略されていた場合、.huskey/Config.xmlからdefaultDB要素の値を使用する。
     *
     * @param huskeyDir ドットフォルダーのディレクトリ
     * @return String
     * @author いっぺー
     */
    public String dbName(String huskeyDir) {
        if (this.command.equals("merge")) {
            return this.values[0];
        }

        for (String option : this.options) {
            if (option.contains("--db=")) {
                return option.replace("--db=", "");
            }
        }

        return getDefaultDBName(huskeyDir);
    }

    /**
     * デフォルトのデータベース名の取得
     *
     * <p>.huskey/Config.xmlからdefaultDB要素の値を取得する。
     *
     * @param huskeyDir ドットフォルダーのディレクトリ
     * @return String
     * @author いっぺー
     */
    private String getDefaultDBName(String huskeyDir) {
        ConfigBuilder builder = new ConfigBuilder(huskeyDir);
        Config config = builder.build();
        return config.searchNode("//defaultDB").getTextContent();
    }
}

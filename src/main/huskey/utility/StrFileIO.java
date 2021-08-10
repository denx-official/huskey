package utility;

import java.io.*;
import java.nio.file.Paths;

/**
 * テキストファイルの入出力を行うクラス
 *
 * @author いっぺー
 */
public class StrFileIO {
    private final String path;

    public StrFileIO(String path) {
        this.path = path;
    }

    /**
     * テキストファイルの読み込み
     *
     * @return String
     * @author いっぺー
     */
    public String readStrFile() {
        File file = Paths.get(this.path).toFile();

        try {
            int ch;
            StringBuilder text = new StringBuilder();
            FileReader reader = new FileReader(file);
            while ((ch = reader.read()) != -1) {
                text.append((char) ch);
            }
            reader.close();
            return text.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * テキストファイルの書き出し
     *
     * @param text 文字列のファイル情報
     * @author いっぺー
     */
    public void writeStrFile(String text) {
        try {
            FileWriter writer = new FileWriter(this.path);
            PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
            pw.print(text);
            pw.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

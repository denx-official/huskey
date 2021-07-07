package database;

import java.io.*;
import java.nio.file.Paths;

/**
 * バイナリファイルの入出力を行うクラス
 *
 * @author いっぺー
 * @see Database
 */
public class BinFileIO {
    private final String path;

    public BinFileIO(String path) {
        this.path = path;
    }

    /**
     * バイナリファイルの読み込み
     *
     * @return byte[]
     * @author いっぺー
     */
    public byte[] readBinFile() {
        File file = Paths.get(this.path).toFile();

        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis)
        ) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] readBuffer = new byte[1024];
            int size = bis.read(readBuffer);
            while (size != -1) {
                out.write(readBuffer, 0, size);
                size = bis.read(readBuffer);
            }

            return out.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * バイナリファイルの書き出し
     *
     * @param bytes バイト列のファイル情報
     * @author いっぺー
     */
    public void writeBinFile(byte[] bytes) {
        try (
                FileOutputStream fos = new FileOutputStream(this.path);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

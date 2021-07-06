package database;

import java.io.*;
import java.nio.file.Paths;

/**
 * バイナリファイルの入出力を行うクラス
 *
 * @author いっぺー
 * @see Database
 */
public class FileIO {

    /**
     * バイナリファイルの読み込み
     *
     * @param path 読み込むファイルのパス
     * @return byte[]
     * @author いっぺー
     */
    static byte[] readBinFile(String path) {
        File file = Paths.get(path).toFile();

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
     * @param path  書き出すファイルのパス
     * @param bytes バイト列のファイル情報
     * @author いっぺー
     */
    static void writeBinFile(String path, byte[] bytes) {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

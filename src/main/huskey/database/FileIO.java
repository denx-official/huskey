package database;

import java.io.*;
import java.nio.file.Paths;

public class FileIO {
    static byte[] readDB(String path) {
        File dbFile = Paths.get(path).toFile();

        try(
                FileInputStream fis = new FileInputStream(dbFile);
                BufferedInputStream bis = new BufferedInputStream(fis)
        ) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] readBuffer = new byte[1024];
            int size = bis.read(readBuffer);
            while(size != -1) {
                out.write(readBuffer, 0, size);
                size = bis.read(readBuffer);
            }

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

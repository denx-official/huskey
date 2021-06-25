package utility;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Database {
    private final String dbName;
    private final String masterKey;

    public Database(String dbName, String masterKey) {
        this.dbName = dbName;
        this.masterKey = masterKey;
    }

    public static String[] showDBList() {
        return new String[] {"sample"};
    }

    public boolean isKeyMatched() throws FileNotFoundException {
        return Objects.equals(this.masterKey, "sample");
    }
}

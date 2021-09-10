package testUtil;

import java.io.InputStream;

public class StandardInputStream extends InputStream {
    private final StringBuilder builder = new StringBuilder();
    private final String lf = System.getProperty("line.separator");

    public void inputln(String str) {
        builder.append(str).append(lf);
    }

    @Override
    public int read() {
        if (builder.length() == 0) return -1;
        int result = builder.charAt(0);
        builder.deleteCharAt(0);
        return result;
    }
}

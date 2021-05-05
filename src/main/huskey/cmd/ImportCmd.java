package cmd;

public class ImportCmd extends Cmd {
    public ImportCmd(String[] values, String[] options) {
        super(values, options);
    }

    public void run() {
        // ここにコマンドの機能を実装する
        String values_str = String.join(", ", this.values);
        String options_str = String.join(", ", this.options);
        
        System.out.println("run import");
        System.out.println("values: " + values_str);
        System.out.println("options: " + options_str);
    }
}

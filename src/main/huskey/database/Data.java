package database;

public class Data {
    private String _title;
    private String _userName;
    private String _password;
    private String _message;
    private final HkTime _created;
    private HkTime _updated;

    public Data(String title, String userName, String password, String message, HkTime created, HkTime updated) {
        this._title = title;
        this._userName = userName;
        this._password = password;
        this._message = message;
        this._created = created;
        this._updated = updated;
    }

    public String title() { return this._title; }
    public String userName() { return this._userName; }
    public String password() { return this._password; }
    public String message() { return this._message; }
    public HkTime created() { return this._created; }
    public HkTime updated() { return this._updated; }

    public void update(String target, String newValue) {
        switch (target) {
            case "title":
                this._title = newValue;
                break;
            case "userName":
                this._userName = newValue;
                break;
            case "password":
                this._password = newValue;
                break;
            case "message":
                this._message = newValue;
                break;
            default:
                throw new IllegalArgumentException("引数 target には、title/userName/password/message のいずれかを指定してください。");
        }
        updateTime();
    }

    private void updateTime() {
        this._updated = new HkTime(
            2021,
            2,
            2,
            12,
            0,
            0
        );
    }
}

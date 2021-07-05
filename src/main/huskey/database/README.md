# database

データベースの入出力および操作に関連したパッケージ。  
データベースは XML 形式となっているが、ライブラリ使用者からはその操作が見えないよう隠蔽している。

## データベースの構成

huskey では、複数の独立したデータベースによってパスワードを管理する仕組みを取っている。  
構成は以下の通り。

- Database
    - masterKey: データベースを暗号化／復号するための共通鍵
    - Config: データベースの設定
        - passLength: 自動生成するパスワードの長さ
        - charSet: パスワードの自動生成に使用する文字/記号
        - trials: パスワード入力の試行回数
    - Dataset: 複数の Data の集合
        - Data
            - title: データの名前
            - userName: ユーザー名
            - password: パスワード
            - message: メッセージ
            - created: データの作成日時
            - updated: データの更新日時

データベースは _~/.huskey/database/_ ディレクトリに保存される。

## DatabaseBuilder

Database インスタンスを取得するためのクラス。  
masterKey の照合、データベースの読み込み／復号を行う。

```java
DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey);

if (!builder.isKeyMatched()) {
    // 不正なパスワードが入力されたときの処理
}

Database db = builder.buildDatabase();
```

## Database（開発中）

データベースの操作および書き出しを行うクラス。

```java
// データベースの構築
Database db = builder.buildDatabase();

// データベース名の変更
String name = db.getDBName();
db.setDBName("something");

// masterKeyの更新
String newKey = "boZzfgstKkwCKClO60PM";
db.setMasterKey(newKey);

// データセット/コンフィグの取得
Dataset dataset = db.useDBChild("dataset");
Config config = db.useDBChild("config");

// データセット/コンフィグの更新
db.setDBChild("dataset", dataset);
db.setDBChild("config", config);

// データベース名のリストを取得
String[] dbList = Database.getDBList();
```

### Dataset

データセットの操作を行うクラス。  
Database の `useDataset` メソッドからインスタンスを取得できる。

```java
// データセットの取得
Dataset dataset = db.useDBChild("dataset");

// データセット内にある全データの名前を取得
String[] dataList = dataset.getDataList();

// データがデータセット内に存在するか否か
boolean result = dataset.isDataExist("Google");

// データの取得
Data data = dataset.useData("Google");

// データの追加/更新
dataset.setData("Google", data);

// データの削除
dataset.removeData("Google");
```

#### ProcessEachData

データセット内にある各データに対して一定の処理を実行するための抽象クラス。  
処理内容は `process` メソッドを呼び出し側でオーバーライドして実装する。

```java
Document doc;

// ...

ProcessEachData<T> process = new ProcessEachData<T>(doc) {
    @Override
    public void process(Data data, Element dataElem, int i) {
        // 各データに対する処理

        // ...

        // 各データに対する処理を中止 → afterAllの実行へ移行
        this.stopProcessing();
    }

    @Override
    public T afterAll() {
        // 全ての処理が終了した時に実行される関数
        // 戻り値はクラス宣言時に指定した型となる。
    }
};

process.run();
```

### Data

データを操作するクラス。

```java
// データの新規作成
Data newData = new Data(
    "huskey", // title
    "jonh", // userName
    "8lQEANKe600DUNmo0XZb", // password
    "", // message
    HkTime.now(), // created
    HkTime.now() // updated
);

// データセットからデータの取得
Data data = dataset.useData("Google");

// テキスト情報の取得
String title = data.getText("title");
String userName = data.getText("userName");
String password = data.getText("password");
String message = data.getText("message");

// 時間情報の取得
HkTime created = data.getTime("created");
HkTime updated = data.getTime("updated");

// データの更新
data.set("password", "rTXm1ikZKI9bDUMzrKKU");
```

#### HkTime

Data インスタンス内で保持する時間情報を定義したクラス。

```java
// 現在時刻を HkTime 型で取得
HkTime hkTime = HkTime.now();

int year = hkTime.get("year");
int month = hkTime.get("month");
int date = hkTime.get("date");
int hours = hkTime.get("hours");
int minutes = hkTime.get("minutes");
int seconds = hkTime.get("seconds");
```

## Config（開発中）

データベースの設定を操作するクラス。

データベースの新規作成時に _~/.huskey/config.xml_ の initialConf タグ内にある設定を初期設定として用いる。

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
            - deleteAfter: 試行回数が上限に達した時、データベースを削除するか否か
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
DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, dbDir);

if (!builder.isKeyMatched()) {
    // 不正なパスワードが入力されたときの処理
}

try {
    Database db = builder.buildDatabase();
} catch (FileNotFoundException e) {
    // データベースが存在しなかったときの処理
}
```

## Database（開発中）

データベースの操作および書き出しを行うクラス。

### Dataset

データセットの操作を行うクラス。  
Database の `useDataset` メソッドからインスタンスを取得できる。

```java
// データセットの取得
Dataset dataset = db.useDataset();

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

// （doc の初期化）

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
Dataset クラスの `useData` メソッドからインスタンスを生成できる。

```java
// データの取得
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
data.update("password", "rTXm1ikZKI9bDUMzrKKU");
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

_~/.huskey/globalConfig.xml_ を基本的な設定とし、各データベースでその設定を上書きするような仕組みにする予定。  
つまり、データベースの config にグローバルの設定から変更したい点だけを記述して反映させる機能ということ。  
イメージとしては、VSCode のワークスペースの概念が最も近い。

上のような仕組みにするのであれば、各データベースのディレクトリに _config.xml_ を作成して管理したほうが良さげか。

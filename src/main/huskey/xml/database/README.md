# database

データベースの操作に関連したパッケージ。  
データベースは XML 形式となっており、その操作には DOM や XPath を用いる。

## データベースの構成

huskey では、複数の独立したデータベースによってパスワードを管理する仕組みを取っている。  
構成は以下の通り。

- Database
    - masterKey: データベースを暗号化／復号するための共通鍵
    - Settings: データベースの設定
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

暗号化されていないデータベースのサンプルとして、 _./src/test/huskey/resources/SampleDB.xml_ を用意しているので参考までに。

## 各クラスについて

### DatabaseBuilder

Database インスタンスを取得するためのクラス。  
masterKey の照合、データベースの読み込み／復号を行う。

```java
DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey);

if (!builder.exists()) {
    // データベースが存在しないときの処理
}

if (!builder.isKeyMatched()) {
    // 不正なパスワードが入力されたときの処理
    builder.setMasterKey("newPassword");
    retry();
}

Database db = builder.build();
```

### Database

データベースの操作および書き出しを行うクラス（具体的な操作法は次章にて解説）。

```java
// データベースの構築
Database db = builder.build();

String path = "//data[@title = 'Google']";
if (!db.nodeExist(path)) {
    // 対象の Node が存在しないときの処理
}

// ノードの検索
Node node = db.searchNode(path).item(0);

// masterKeyの更新
String newKey = "boZzfgstKkwCKClO60PM";
db.setMasterKey(newKey);

// データベースの書き出し
db.write();

// ==============================

// データベース名のリストを取得
String[] dbList = Database.getDBList();
```

### Data

データを操作するクラス。

```java
// データの新規作成
Data data = new Data(
        "huskey", // title
        "jonh", // userName
        "8lQEANKe600DUNmo0XZb", // password
        "", // message
        HkTime.now(), // created
        HkTime.now() // updated
);

// Data 型を Element 型に変換
Element elem = data.toElement(db.doc);
```

#### HkTime

Data インスタンス内で保持する時間情報を定義したクラス。

```java
// 現在時刻の取得
HkTime hkTime = HkTime.now();

// HkTime 型を Element 型に変換
Element elem = hkTime.toElement(db.doc, "updated");
```

#### BinFileIO

バイナリファイルの入出力を行うクラス（詳細は割愛）。

## データベースの操作について

前述の通り、huskey ではデータベース操作を DOM および XPath に依存している（各ワードの解説については xml パッケージにある README を参照）。  
ここでは huskey におけるデータベースの操作法について簡単に解説を行う。

### Node/Element の取得

huskey では XPath 構文を用いて Node を簡単に取得できるよう、 Database クラスに `searchNodeList` メソッドを用意している。  
このメソッドは、引数に XPath 構文を入力することで検索結果に該当した全ての Node を NodeList 型として取得できる。

```java
Database db = builder.build();

// data 要素をすべて取得
NodeList dataList = db.searchNodeList("//data);
Node firstData = dataList.item(0); // 先頭の node を取得
```

また、Node ではなく Element で取得したい場合は、型をキャスト（型変換）することで可能となる。

```java
NodeList nodeList = db.searchNodeList("//data");

for (int i = 0; i < nodeList.getLength(); i++) {
    Element data = (Element) nodeList.item(i); // i 番目の Node を Element 型で取得
    String title = data.getAttribute("title"); // title 属性の値を取得
}
```

Element 型では属性へのアクセスが簡単にできるため、こうしたケースでは Element 型を利用した方がコードを簡潔に書ける。

### データベースの更新

データベースの内容を更新する際は、先ほどの `searchNodeList` メソッドなどから Node/Element を取得し、それらから提供されるメソッドを用いることで可能となる。

```java
Element data = (Element) db.searchNode("//data[@title = 'Google']");
data.setAttribute("title", "Google2"); // title 属性の更新

Node passNode = data.getElementsByTagName("password").item(0); // Element 内から password タグを持つ Node を取得
passNode.setTextContent("MJ0fQstGuhzYA5BaHqL0"); // password 要素内の Text を更新
```

**データを更新した際は、updated 要素の更新も忘れずに行うこと！**

```java
Node updated = data.getElementsByTagName("updated").item(0);
Element newUpdated = HkTime.now().toElement(db.doc, "updated");
data.removeChild(updated);
data.appendChild(newUpdated);
```

### データの新規追加

データセットに新たなデータを作成する場合は、Data クラスを通して取得できる Element を追加することで実現できる。

```java
Data data = new Data(
        "huskey", // title
        "jonh", // userName
        "8lQEANKe600DUNmo0XZb", // password
        "", // message
        HkTime.now(), // created
        HkTime.now() // updated
);
Element dataElem = data.toElement(db.doc); // Data 型を Element 型に変換

Node dataset = db.searchNodeList("//dataset").item(0);
dataset.appendChild(dataElem); // データセットに新規データを追加
```

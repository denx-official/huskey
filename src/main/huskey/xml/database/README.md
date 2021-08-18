# database

データベースの操作に関連したパッケージ。  
データベースは XML 形式となっており、その操作には DOM や XPath を用いる。

## データベースの構成

huskey では、複数の独立したデータベースによってパスワードを管理する仕組みを取っている。  
構成は以下の通り。

- Database
    - masterKey: データベースを暗号化/復号するための共通鍵
    - Settings: データベースの設定
        - defaultUserName: デフォルトのユーザー名
        - passLength: 自動生成するパスワードの長さ
        - charSet: パスワードの自動生成に使用する文字/記号
    - Dataset: 複数の Data の集合
        - Data
            - title: データの名前
            - userName: ユーザー名
            - password:
                - value: パスワードの値
                - charSet: 上記のパスワード生成の際に使用した文字/記号
            - message: メッセージ
            - created: データの作成日時
            - updated: データの更新日時

データベースは _~/.huskey/database/_ ディレクトリに保存される。

暗号化されていないデータベースのサンプルとして、 _./src/test/huskey/resources/SampleDB.xml_ を用意しているので参考までに。

## 各クラスについて

### DatabaseBuilder

Database インスタンスを取得するためのクラス。  
masterKey の照合、データベースの読み込み/復号を行う。

```java
DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);

if (!builder.exists()) {
    // データベースが存在しないときの処理
}

if (!builder.isKeyMatched()) {
    // 不正なパスワードが入力されたときの処理
}

// masterKey の更新
builder.setMasterKey("newPassword");

// データベースの読み込み
Database db = builder.build();
```

masterKey の照合には BCrypt を用いている。  
`setMasterKey` によって masterKey 更新する際に、そのハッシュ値も自動的に更新される。

また、データベースの読み込みを行う際、裏ではその復号処理を行っている。

### Database

データベースの操作および書き出しを行うクラス（具体的な操作法は次章にて解説）。

```java
// データベースの構築
Database db = builder.build();

String path = "//data[@title = 'Google']";
if (!db.exists(path)) {
    // 対象の Node が存在しないときの処理
}

// ノードの取得
Node node = db.searchNode(path);
NodeList nodeList = db.searchNodeList(path); // 複数のノードを取得する場合はこっち

// masterKey の更新
String newKey = "boZzfgstKkwCKClO60PM";
db.setMasterKey(newKey);

// updated (データの更新日時) の更新
db.updateTime("title");

// データベースの書き出し
db.write();

// ==============================

// データベース名のリストを取得
String[] dbList = Database.getDBList(huskeyDir);
```

`write` メソッド時にデータベースは自動的に暗号化された状態で保存されるようプログラムしている。

### Data

データを操作するクラス。

```java
// データの新規作成
Data data = new Data(
        "huskey", // title
        "jonh", // userName
        password, // password (Password 型)
        "", // message
        HkTime.now(), // created (HkTime 型)
        HkTime.now() // updated (HkTime 型)
);

// Data 型を Element 型に変換
Element elem = data.toElement(db.doc);
```

#### Password

パスワードに関連した情報を保持するクラス。

```java
Password password = new Password(
        "", // value (パスワードの値)
        "20", // passLength (パスワード長)
        charSet // charSet (パスワード生成に使用する文字、CharSet 型)
);

// value を更新する
password.update(); // charSet を使用して自動生成
password.update("fSRb157bWeu6bDmzcGed"); // 任意の値で更新

// Password 型を Element 型に変換
Element elem = charSet.toElement(db.doc);
```

#### CharSet

パスワード生成に使用する文字を保持するクラス。

```java
CharSet charSet = new CharSet(
        "true", // lowerCase (小文字, a-z)
        "true", // upperCase (大文字, A-Z)
        "true", // number (数字, 0-9)
        "false", // symbols (記号, !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~)
        "false", // space (空白)
        "" // custom (任意文字)
);

// 対象の文字をすべて結合して取得
String strings = charSet.toString();

// CharSet 型を Element 型に変換
Element elem = charSet.toElement(db.doc);
```

#### HkTime

Data インスタンス内で保持する時間情報を定義したクラス。

```java
// 現在時刻の取得
HkTime hkTime = HkTime.now();

// 取得できる変数のリストを取得 (year, month, date, hours, minutes, seconds)
for (String name : HkTime.iterator()) {
    // get メソッドから各値にアクセス
    int num = hkTime.get(name);

    // 各処理
}
```

## データベースの操作について

前述の通り、huskey ではデータベース操作を DOM および XPath に依存している（各ワードの解説については xml パッケージにある README を参照）。  
ここでは huskey におけるデータベースの操作法について簡単に解説を行う。

### Node/Element の取得

huskey では XPath 構文を用いて Node を簡単に取得できるよう、 Database クラスに `searchNode` および `searchNodeList` メソッドを用意している。  
これらのメソッドは、引数に XPath 構文を入力し、検索結果に該当した Node を取得できる。  
該当する Node が１つの場合は前者、複数個の場合は後者を用いること。

```java
Database db = builder.build();

// data 要素をすべて取得
NodeList dataList = db.searchNodeList("//data");
Node firstData = dataList.item(0); // 先頭の Node を取得
```

また、Node ではなく Element (要素) で取得したい場合は、型をキャスト（型変換）することで可能となる。

```java
NodeList nodeList = db.searchNodeList("//data");

for (int i = 0; i < nodeList.getLength(); i++) {
    Element data = (Element) nodeList.item(i); // i 番目の Node を Element 型で取得
    String title = data.getAttribute("title"); // title 属性の値を取得
}
```

Element 型では属性へのアクセスが簡単にできるため、こうしたケースでは Element 型を利用した方がコードを簡潔に書ける。

### データベースの更新

データベースの内容を更新する際は、先ほどの `searchNode` メソッドなどから Node/Element を取得し、それらから提供されるメソッドを用いることで可能となる。

```java
Element data = (Element) db.searchNode("//data[@title = 'Google']");
data.setAttribute("title", "Google2"); // title 属性の更新

Node passNode = data.getElementsByTagName("password").item(0); // Element 内から password タグを持つ Node を取得
passNode.setTextContent("MJ0fQstGuhzYA5BaHqL0"); // password 要素内の Text を更新
```

**データを更新した際は、updated 要素の更新も忘れずに行うこと！**

```java
db.updateTime("Google"); // これで "//data[@title = 'Google']/updated" が更新される
```

### データの新規追加

データセットに新たなデータを作成する場合は、Data クラスを通して取得できる Element を追加することで実現できる。

```java
CharSet charSet = new CharSet(
        "true", // lowercase
        "true", // uppercase
        "true", // number
        "false", // symbols
        "false", // space
        "" // custom
);
Password password = new Password("", "20", charSet);
password.update();

Data data = new Data(
        "huskey", // title
        "jonh", // userName
        password, // password
        "", // message
        HkTime.now(), // created
        HkTime.now() // updated
);
Element dataElem = data.toElement(db.doc); // Data 型を Element 型に変換

Node dataset = db.searchNode("//dataset");
dataset.appendChild(dataElem); // データセットに新規データを追加
```

### データの削除

特定のデータを削除する場合は、データセットを Node で取得し、`removeChild` メソッドを用いて削除する。

```java
Node data = db.searchNode("//data[@title = 'Google']");
Node dataset = db.searchNode("//dataset");
dataset.removeChild(data);
```

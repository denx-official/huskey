# database

データベースの入出力および操作に関連したパッケージ。  
データベースは XML 形式となっており、その操作には DOM や XPath を用いる。

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

Database db = builder.buildDatabase();
```

### Database

データベースの操作および書き出しを行うクラス（具体的な操作法は次章にて解説）。

```java
// データベースの構築
Database db = builder.buildDatabase();

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

#### FileIO

バイト列によるファイルの入出力を行うクラス（詳細は割愛）。

## データベースの操作法について

前述の通り、データベースの操作には **DOM** という XML 文章の操作規格、および **XPath** という XML 文章の検索 API の２つを用いる。  
ここで簡単ではあるが、これらの使い方について解説を行う。

### 前提知識

#### XML

XML (_Extensible Markup Language_) とは、 **データの意味と内容を記述することに特化したマークアップ言語** の１つである。  
例えば、こんなようなもの。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- 売り物リスト -->
<売り物>
    <果物 name="りんご">
        <値段>500</値段>
        <個数>3</個数>
    </果物>
    <果物 name="ばなな">
        <値段>300</値段>
        <個数>5</個数>
    </果物>
    <果物 name="すいか">
        <値段>2000</値段>
        <個数>1</個数>
    </果物>
</売り物>
```

**タグ Tag** （\<>の中に書かれている文字、例: 売り物、果物、値段、など）が「データの意味」であり、そのタグの中に書かれた文字（例: 値段タグの中に書かれた「500」、など）が「データの内容」を表している。  
タグの内側に書かれた「name="りんご"」といったものは **属性 Attribute (Attr)** といい、データに対して追加の情報を記述することができる。  
また、開始タグから終了タグまでの一塊を **要素 Element** と呼ぶ。

参考

- [XML入門 | JavaDrive](https://www.javadrive.jp/xml/)

#### DOM

DOM (_Document Object Model_) とは、 **HTML や XML といった文章を取り扱うための API** のことである。

DOM では文章全体のことを **Document** と呼び、それを構成するオブジェクトを **Node** という。  
Node にはいくつかの種類があり、上で挙げた **Element** や **Attr** 、Element の中に文字で内容が書かれた **Text** 、「売り物リスト」と書かれたコメントアウトを示す **Comment** などなど、これらは全て Node の一種である。

今回のプロジェクトにおいてはひとまずこれだけ理解できればなんとかなる、はず。

参考

- [DOM、Node、Elementの違いとそれぞれの使い分け | Black Everyday Company](https://kuroeveryday.blogspot.com/2018/11/difference-between-dom-and-node-and-element.html)
- [XMLデータを操作する～DOMの詳細：技術者のためのXML再入門（11） - ＠IT](https://www.atmarkit.co.jp/ait/articles/0209/10/news002.html)

#### XPath

XPath (_XML Path Language_) とは、 **XML 文章の検索を目的とした構文、およびそれを利用した検索 API** のことである。

例えば、先ほど例に挙げた XML 文章の「『売り物』の中にある『果物 name="りんご"』要素が欲しい」という時は、 `/売り物/果物[@name = 'りんご']` と記述することで、その要素にアクセスすることができる。

XPath の記法は非常に多く存在するため詳細は割愛するが、最低限以下のものを押さえておけばなんとかなる。

- `//`
    - 意味: パスの省略
    - 例: `//果物`
        - 果物要素を取得
- `@(属性名)`
    - 意味: 属性の値
    - 例: `//果物[@name = 'りんご']`
        - 属性 name の値が「りんご」の果物要素を取得
- `text()`
    - 意味: 要素内の Text
    - 例: `//値段[text() = 2000]`
        - Text が「2000」の値段要素を取得
- `*`
    - 意味: すべて
    - 例: `/売り物/*`
        - 売り物要素内にあるすべての要素を取得
- `[contains(（対象）, '（検索内容）')]`
    - 意味: （対象）内に（検索内容）を含む要素を取得
    - 例: `//果物[contains(@name, 'り')]`
        - 属性 name に「り」を含む果物要素を取得
    - 例: `//値段[contains(text(), '0')]`
        - Text に「0」を含む値段要素を取得
- `or`, `and`
    - 意味: もしくは／かつ
    - 例: `//果物[contains(@name, 'り') or contains(@name, 'め')]`
        - 属性 name に「り」もしくは「め」を含む果物要素を取得

詳しい記法は以下の URL を参考に。

- [XPathとは？基本概念や書き方をわかりやすく解説！ | Octoparse](https://www.octoparse.jp/blog/xpath-introduction/)
- [便利なXPathまとめ - ZOZO Technologies TECH BLOG](https://techblog.zozo.com/entry/xpath)

### huskey におけるデータベースの操作

#### Node/Element の取得

huskey では XPath 構文を用いて Node を簡単に取得できるよう、 Database クラスに `searchNodeList` メソッドを用意している。  
このメソッドは、引数に XPath 構文を入力することで検索結果に該当した全ての Node を NodeList 型として取得できる。

```java
Database db = builder.buildDatabase();

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

#### データベースの更新

データベースの内容を更新する際は、先ほどの `searchNodeList` メソッドなどから Node/Element を取得し、それらから提供されるメソッドを用いることで可能となる。

```java
Element data = (Element) db.searchNode("//data[@title = 'Google']");
data.setAttribute("title", "Google2"); // title 属性の更新

Node passNode = data.getElementsByTagName("password").item(0); // Element 内から password タグを持つ Node を取得
passNode.setTextContent("MJ0fQstGuhzYA5BaHqL0"); // password 要素内の Text を更新

// データを更新した際は、updated 要素の更新も行う事
Node updated = data.getElementsByTagName("updated").item(0);
Element newUpdated = HkTime.now().toElement(db.doc, "updated");
data.removeChild(updated);
data.appendChild(newUpdated);
```

#### データの新規追加

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

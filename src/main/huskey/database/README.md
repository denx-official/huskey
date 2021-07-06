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

if (!builder.isKeyMatched()) {
    // 不正なパスワードが入力されたときの処理
}

Database db = builder.buildDatabase();
```

### Database

データベースの操作および書き出しを行うクラス（具体的な操作法は次章にて解説）。  

```java
// データベースの構築
Database db = builder.buildDatabase();

// masterKeyの更新
String newKey = "boZzfgstKkwCKClO60PM";
db.setMasterKey(newKey);

// ノードの検索
Node node = db.searchNode("/database/dataset/data[@title = 'Google']");

// データベース名のリストを取得
String[] dbList = Database.getDBList();

// データベースの書き出し
// （開発中）
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

## データベースの操作法について

前述の通り、データベースの操作には **DOM** という XML 文章の操作規格、および **XPath** という XML 文章の検索APIの２つを用いる。  
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
Node にはいくつかの種類があり、上で挙げた **Element** や **Attr** 、Elementの中に文字で内容が書かれた **Text** 、「売り物リスト」と書かれたコメントアウトを示す **Comment** などなど、これらは全て Node の一種である。

今回のプロジェクトにおいてはひとまずこれだけ理解できればなんとかなる、はず。

参考

- [DOM、Node、Elementの違いとそれぞれの使い分け | Black Everyday Company](https://kuroeveryday.blogspot.com/2018/11/difference-between-dom-and-node-and-element.html)
- [XMLデータを操作する～DOMの詳細：技術者のためのXML再入門（11） - ＠IT](https://www.atmarkit.co.jp/ait/articles/0209/10/news002.html)

#### XPath

XPath (_XML Path Language_) とは、 **XML 文章の検索を目的とした構文、およびそれを利用した検索 API** のことである。

例えば、先ほど例に挙げた XML 文章の「『くだもの』の中にある『りんご』の中にある『値段』要素が欲しい」という時は、 `/くだもの/りんご/値段` と記述することで、その要素にアクセスすることができる。

さらに実践的な使い方は次章にて紹介する。

参考

- [Java標準APIでのXML読み込み方4つを大紹介 サンプルで比較しよう](https://engineer-club.jp/java-xml-read)

### huskey におけるデータベースの操作

#### Node/Element の取得

huskey では XPath 構文を用いて Node を簡単に取得できるよう、 Database クラスに `searchNodeList` メソッドを用意している。  
このメソッドは、引数に XPath 構文を入力することで検索結果に該当した全ての Node を NodeList 型として取得できる。


```java
Database db = builder.buildDatabase();

// database > dataset > title="Google" という Attr を持つ data 要素を取得する
// 今回は１つしか取得できるものがないと確定しているので、要素の長さは１
Node node = db.searchNodeList("/database/dataset/data[@title = 'Google']").item(0);
```

また、Node ではなく Element で取得したい場合は、型をキャスト（型変換）することで可能となる。

```java
Element elem = (Element) db.searchNode("/database/dataset/data[@title = 'Google']").item(0);

// Attr の内容を取得
String title = elem.getAttribute("title");
```

```java
// database > dataset >  data 要素を全て取得
NodeList nodeList = db.searchNodeList("/database/dataset/data");

for (int i = 0; i < nodeList.getLength(); i++) {
    Element data = (Element) nodeList.item(i); // i 番目の Node を Element 型にキャスト
    Node passNode = data.getElementsByTagName("password").item(0); // data 要素内の "password" タグを持つ要素の取得
    String password = passNode.getTextContent(); // password 要素内の Text を取得
}
```

Element 型では Attr や要素内の Element へのアクセスが簡単にできるため、こうしたケースでは Element 型を利用した方がコードを簡潔に書ける。

#### データベースの更新

データベースの内容を更新する際は、先ほどの `searchNodeList` メソッドなどから Node/Element を取得し、それらから提供されるメソッドを用いることで可能となる。

```java
Element data = (Element) db.searchNode("/database/dataset/data[@title = 'Google']");
data.setAttribute("title", "Google2"); // title 属性の更新

Node passNode = data.getElementsByTagName("password").item(0);
passNode.setTextContent("MJ0fQstGuhzYA5BaHqL0"); // password 要素内の Text を更新
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

Node dataset = db.searchNode("/database/dataset");
dataset.appendChild(dataElem); // データセットに新規データを追加
```

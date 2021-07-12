# xml

XML 形式のファイルを操作するための抽象クラスを定義したパッケージ。  
抽象クラスの継承先は以下の通り。

- config
    - huskey 全体で用いられる設定ファイル（_~/.huskey/config.xml_）に関連したパッケージ。
- database
    - データベースの操作や書き出しに関連したパッケージ。

## XMLWriter

XMLファイルの情報を出力する機能を持った抽象クラス。  
`write` メソッドは Template Method パターンで実装しており、継承先で `getDoc` `encrypt` `getFilePath` メソッドをオーバーライドすることで機能するようになる。

## XMLBuilder

XMLWriter のサブクラスのインスタンスを構築するAPIの抽象クラス。  
こちらも `build` コマンドを Template Method パターンを採用し、`decrypt` `getFilePath` `returnNewInstance` メソッドのオーバーライドによって機能する。

## XMLOperator

Document の検索や更新を行うクラス。  
Database や Config クラスに委譲して使用する。

## XMLParser

byte[] 型の XML 文章と Document 型の変換を行うクラス。

## 前提知識

huskey では XML 文章の操作を多用するため、その周辺知識がどうしても必要となる。  
ここではこれらについて簡単に解説を行う。  
さらに深く学びたい方は、各項目にある参考 URL を参照されたし。

### XML

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

### DOM

DOM (_Document Object Model_) とは、 **HTML や XML といった文章を取り扱うための API** のことである。

DOM では文章全体のことを **Document** と呼び、それを構成するオブジェクトを **Node** という。  
Node にはいくつかの種類があり、上で挙げた **Element** や **Attr** 、Element の中に文字で内容が書かれた **Text** 、「売り物リスト」と書かれたコメントアウトを示す **Comment** などなど、これらは全て Node の一種である。

詳しい使い方については database パッケージ内の README を参照されたし。

今回のプロジェクトにおいてはひとまずこれだけ理解できればなんとかなる、はず。

参考

- [DOM、Node、Elementの違いとそれぞれの使い分け | Black Everyday Company](https://kuroeveryday.blogspot.com/2018/11/difference-between-dom-and-node-and-element.html)
- [XMLデータを操作する～DOMの詳細：技術者のためのXML再入門（11） - ＠IT](https://www.atmarkit.co.jp/ait/articles/0209/10/news002.html)

### XPath

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
    - 意味: もしくは/かつ
    - 例: `//果物[contains(@name, 'り') or contains(@name, 'め')]`
        - 属性 name に「り」もしくは「め」を含む果物要素を取得

詳しい記法は以下の URL を参考に。

- [XPathとは？基本概念や書き方をわかりやすく解説！ | Octoparse](https://www.octoparse.jp/blog/xpath-introduction/)
- [便利なXPathまとめ - ZOZO Technologies TECH BLOG](https://techblog.zozo.com/entry/xpath)

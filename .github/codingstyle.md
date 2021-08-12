# Coding Style

## フォーマットについて

インデント幅等の書式については、_./.editorconfig_ の設定を使用する。  
内容は IntelliJ IDEA のデフォルト設定を用いる。

IntelliJ IDEA で .editorconfig を使用する場合は、「設定 > エディター > コードスタイル」より、「EditorConfig のサポートを有効にする」にチェックを入れることで使用できる。

コードフォーマットは文章を全て選択し、Windows の場合は \[Ctrl] + \[Alt] + \[L]、Mac の場合は \[option] + \[command] + \[L] で実行できる。

**コードフォーマットはコミット前に必ず行うように！**

### .md ファイルのフォーマット

IntelliJ は .md をフォーマットする際に、言語指定されたコードブロックのテキストをその言語の設定でフォーマットするというお節介機能が付いている。  
これによりコードブロックにクラス等を省略した文法違反のコードを記述して .md ファイルをフォーマットすると、インデントがぐちゃぐちゃになってしまう。

こうした場合はそのコードブロックを外してフォーマットを行うか、言語指定なしのコードブロックを使うようにすること。

## コーディングについて

### 例外処理

例外処理を強要するようなメソッドを扱う場合は、その例外を try-catch 文で補足し、例外時の処理を別で記述するか、`RuntimeException` およびそのサブクラスでその例外をラップして送出し、処理を止めること。

例

```java
try {
    doSomething(); // IOException の例外処理を強要するメソッドを実行
} catch (IOException e) {
    throw new RuntimeException(e);
        // IOException の場合は "UncheckedIOException" でラップするのが本当は適切
        // なにでラップしたらいいか分からなければ "RuntimeException" でぶん投げとけば OK
}
```

### テストについて

テストコードは src/test/huskey ディレクトリに、パッケージごとにディレクトリを分けて作成すること。

**データベースや config.xml 等のファイルを書き換えるテストをする場合は、テスト終了後にファイルが元の状態に戻るようにすること！**  
方法は任せるが、私は

- ファイルの内容は変えずに書き出しを行い、そのファイルの更新日を見る（変わっていれば書き出し成功）
    - [./src/test/huskey/xml/database/databaseTest.java](https://github.com/denx-official/huskey/blob/main/src/test/huskey/xml/database/DatabaseTest.java) の「データベースの書き出し」
- ファイルを別の内容で上書きし、テスト終了後に元のファイルに戻す
    - [./src/test/huskey/utility/BinFileIOTest.java](https://github.com/denx-official/huskey/blob/main/src/test/huskey/utility/BinFileIOTest.java)
    - [./src/test/huskey/utility/StrFileIOTest.java](https://github.com/denx-official/huskey/blob/main/src/test/huskey/utility/StrFileIOTest.java)
    - [./src/test/huskey/xml/database/databaseTest.java](https://github.com/denx-official/huskey/blob/main/src/test/huskey/xml/database/DatabaseTest.java) の「setMasterKey」

といった方法を取った。

上記のテストに限らず、他のテストの書き方も参考にすること。  
テストの書き方が分からない場合は、私（いっぺー）や Java に詳しい人に相談しよう。

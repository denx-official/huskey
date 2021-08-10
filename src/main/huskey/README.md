# huskey

## パッケージ

- cmd
    - コマンドの実装に関連するパッケージ
- crypt
    - データベースの暗号化/復号や masterKey のハッシュ化に関するパッケージ
- utility
    - huskey 内で汎用的に扱うモジュールをまとめたパッケージ
- xml
    - Database や Config といった XML 文章を扱うパッケージ

パッケージの詳細については、各ディレクトリにある README およびテストコードを参照されたし。

## 開発時のお約束（随時更新）

### コードの書式

インデント幅等の書式については、_./.editorconfig_ の設定を使用する。  
内容は IntelliJ IDEA のデフォルト設定を用いている。

IntelliJ IDEA で .editorconfig を使用する場合は、「設定 > エディター > コードスタイル」より、「EditorConfig のサポートを有効にする」にチェックを入れることで使用できる。

コードフォーマットは文章を全て選択し、Windows の場合は \[Ctrl] + \[Alt] + \[L]、Mac の場合は \[option] + \[command] + \[L] で実行できる。

**コードフォーマットはコミット前に必ず行うように！**

#### .md ファイルのフォーマット

IntelliJ は .md をフォーマットする際に、言語指定されたコードブロックのテキストをその言語の設定でフォーマットするというお節介機能が付いている。  
これによりコードブロックにクラス等を省略した文法違反のコードを記述して .md ファイルをフォーマットすると、インデントがぐちゃぐちゃになってしまう。

こうした場合はそのコードブロックを外してフォーマットを行うか、言語指定なしのコードブロックを使うようにすること。

### コーディングについて

#### 例外処理

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

#### 例外送出

huskey 実行時にユーザー側の不正な操作（例：コマンドライン引数が不正、masterKey の照合失敗など）によって処理を停止せざるを得なくなった場合は、`utility.HuskeyException` を使って例外を送出し処理を止めること。

メッセージ内容は、なぜ処理が止まったのかの理由を簡潔に記述すること。

例

```java
void doSomething() {
    DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);

    if (!builder.isKeyMatched()) {
        throw new HuskeyException("データベース " + dbName + " のパスワードが正しくありません。");
    }
}
```

# huskey

## パッケージ

- cmd
    - コマンドの実装に関連するパッケージ
- crypt
    - データベースの暗号化/復号や masterKey のハッシュ化に関するパッケージ
- database
    - データベースの入出力や操作に関連するパッケージ
- utility
    - huskey 内で汎用的に扱うモジュールをまとめたパッケージ

パッケージの詳細については、各ディレクトリにある README およびテストコードを参照されたし。

## 開発時のお約束（随時更新）

### コーディングについて

#### 例外処理

例外処理を強要するようなメソッドを扱う場合は、その例外を try-catch 文で補足し、例外時の処理を別で記述するか、`RuntimeException` およびそのサブクラスでその例外をラップして送出し、処理を止めること。

例

```java
try {
    doSomething(); // IOException の例外処理を強要するメソッドを実行
} catch (IOException e) {
    throw new RuntimeException(e);
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


# cmd

コマンドに関連したパッケージ。

## Cmd

コマンドに最低限実装して欲しい項目を定義したインターフェース。

## CommandRunner

HkArgs（分解されたコマンドライン引数）からコマンドを決定・実行するクラス。

```java
CommandRunner cr = new CommandRunner(hkArgs);
cr.run();
```

### UseDatabase

データベースの取得を行うクラス。  
masterKey を照合する処理や、データベースの存在確認といった、各コマンドで共通するフローをまとめた static method `UseDatabase.useDB` を使用することで、簡単にデータベースを取得することができる。

```java
Database db = UseDatabase.useDB(dbName, masterKey, huskeyDir);
```

## コマンド一覧

各コマンドのクラス名は、予約語（既にプログラミング言語側で使われている単語）との競合を避けるため _○○Cmd_ という名前にしている。

- ChangeCmd
    - データベースの masterKey や設定を変更するコマンド。
- DatabaseCmd
    - データベース周りの処理を担当するコマンド。
- GetCmd
    - パスワードの取得。
- HelpCmd
    - ヘルプの表示。
- InitCmd
    - データベースの初期化。
- ListCmd
    - データセットの一覧表示。
- MergeCmd
    - データベースの結合。
- RemoveCmd
    - パスワードの削除。
- SearchCmd
    - データセットの検索。
- SetCmd
    - パスワードの追加/更新。
- SwitchCmd
    - 既定のデータベースの変更。

### 各コマンド実装時のお約束

#### 引数について

現状、各クラスの初期化には huskeyDir（後述）という引数のみを設定している。  
コマンド実装の際に values や options が必要になった場合は、それに応じて適宜コードを修正すること。  
（修正する箇所は、自クラスのメンバ変数とコンストラクター、および CommandRunner クラスで初期化する際の引数）

#### huskeyDirについて

引数 huskeyDir には、huskey のドットフォルダーが存在するディレクトリを入力する。  
テストを行う際には、この値を `GlobalConst.huskeyDir` に設定すると、テスト用のディレクトリを参照するようになる。

#### 例外送出

huskey 実行時にユーザー側の不正な操作（例：コマンドライン引数が不正、masterKey の照合失敗など）によって処理を停止せざるを得なくなった場合は、`utility.HuskeyRuntimeException` を使って例外を送出し処理を止めること。

メッセージ内容は、なぜ処理が止まったのかの理由を簡潔に記述すること。

例

```java
void doSomething() {
    DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);

    if (!builder.isKeyMatched()) {
        throw new HuskeyRuntimeException("データベース " + dbName + " のパスワードが正しくありません。");
    }
}
```

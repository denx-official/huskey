# cmd

コマンドに関連したパッケージ。

## Cmd

コマンドに最低限実装して欲しい項目を定義したインターフェース。

## CommandRouting

コマンドライン引数からコマンドを決定・実行するクラス。

```java
String command = "help";
String[] values = {""};
String[] options = {""};

CommandRouting cr = new CommandRouting(command, values, options);
cr.run();
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

現状、各クラスの初期化には `huskeyDir` という huskey のドットフォルダーが存在するディレクトリを示す引数のみを設定している。  
コマンド実装の際に values や options が必要になった場合は、それに応じて適宜コードを修正すること。  
（修正する箇所は、自クラスのメンバ変数とコンストラクター、および CommandRouting クラスで初期化する際の引数）

#### テストについて

テストコードは src/test/cmd/○○Cmd/ ディレクトリに作成すること。

また、先の `huskeyDir` は、テスト時に参照するドットファイルのディレクトリを指定するために用意している。  
テストをする際は、このディレクトリを _./target/test-classes/resources/_ にするとテスト用のファイルを参照することができる。

また、 **データベースや config 等を書き換えるテストをする場合は、テスト終了後にファイルが元の状態に戻るようにすること。**  
方法は任せるが、私は

- ファイルの内容は変えずに書き出しを行い、そのファイルの更新日を見る（変わっていれば書き出し成功）
    - 参考: _./src/test/huskey/xml/database/databaseTest.java_
- 実際のデータを書き出して、全テスト終了後に元のファイルを書き出して元に戻す
    - 参考: _./src/test/huskey/utility/BinFileIOTest.java_

といった方法を取った。

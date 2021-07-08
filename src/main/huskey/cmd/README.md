# cmd

コマンドに関連したパッケージ。

## Cmd

コマンドに最低限実装して欲しい項目を定義した抽象クラス。

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

開発の際に別のクラスが必要になった場合は、各自ディレクトリに作成すること。  
また、テストコードは src/test/cmd/○○Cmd/ ディレクトリに作成すること。

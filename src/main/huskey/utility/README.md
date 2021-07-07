# utility

huskey で汎用的に用いるパッケージ。

## BinFileIO

バイナリファイル（データベースや xml ファイル）の入出力を行うクラス（詳細は割愛）。

## GlobalConst

グローバル定数を定義したクラス。

```java
// huskeyのドットフォルダーのディレクトリ (~/.huskey/)
String hkDir = GlobalConst.HUSKEY_DIR;
```

## HiddenInput

標準入力の値をコンソール上に表示せずに取得するクラス。

```java
String databaseName = "sample";
String masterKey = HiddenInput.read(databaseName);
// -> データベース sample のパスワード:
```

## HuskeyException

huskey 実行時にユーザー側の不正な操作（例：コマンドライン引数が不正、masterKey の照合失敗など）が発生した時の例外クラス。  
この例外は cmd.CommandRouting クラスの run メソッドで捕捉され、与えられたメッセージが標準エラーとして表示される。

## SeparateArgs

コマンドライン引数を command, values, options に分解するクラス。

```java
SeparateArgs sepArgs = new SeparateArgs(args);

String command = sepArgs.getCommand();
String[] values = sepArgs.getValues();
String[] options = sepArgs.getOptions();
```

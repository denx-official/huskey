# utility

huskey で汎用的に用いるパッケージ。

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

huskey実行時に発生しうる例外クラス。  
この例外は cmd.CommandRouting クラスの run メソッドで捕捉され、与えられたメッセージが標準エラーとして表示される。

```java
void doSomething() {
    // ...
    if (!status) {
        throw new HuskeyException("（具体的なエラー内容）");
    }
}
```

## SeparateArgs

コマンドライン引数を command, values, options に分解するクラス。

```java
SeparateArgs sepArgs = new SeparateArgs(args);

String command = sepArgs.getCommand();
String[] values = sepArgs.getValues();
String[] options = sepArgs.getOptions();
```

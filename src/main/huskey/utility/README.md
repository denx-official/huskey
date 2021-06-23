# utility

huskey で汎用的に用いるパッケージ。

## HiddenInput

標準入力の値をコンソール上に表示せずに取得するクラス。

```java
String databaseName = "sample";
String masterKey = HiddenInput.read(databaseName);
// -> データベース sample のパスワード:
```

## SeparateArgs

コマンドライン引数を command, values, options に分解するクラス。

```java
SeparateArgs sepArgs = new SeparateArgs(args);

String command = sepArgs.getCommand();
String[] values = sepArgs.getValues();
String[] options = sepArgs.getOptions();
```

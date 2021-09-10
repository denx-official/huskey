# args

コマンドライン引数に関連したパッケージ。

## SeparateArgs

コマンドライン引数を分解し、HkArgs インスタンスを生成するクラス。

```java
SeparateArgs sepArgs = new SeparateArgs(args);
HkArgs hkArgs = sepArgs.genHkArgs();
```

## HkArgs

分解されたコマンドライン引数をまとめたクラス。

```java
// コマンドライン引数からデータベース名を取得
// データベース名が存在しなかった場合、Config.xml の defaultDB 要素の値を使用する
String dbName = hkArgs.getDBName(huskeyDir);
```

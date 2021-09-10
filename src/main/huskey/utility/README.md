# utility

huskey で汎用的に用いるパッケージ。

## BinFileIO

バイナリ形式でのファイルの入出力を行うクラス。

```java
BinFileIO io = new BinFileIO("path/to/file");
byte[] result = io.readBinFile();
io.writeBinFile("something");
```

## StrFileIO

BinFileIO の文字列版（割愛）。

## GlobalConst

グローバル定数を定義したクラス。

```java
// huskeyのドットフォルダーのディレクトリ (~/.huskey/)
String hkDir = GlobalConst.HUSKEY_DIR;
```

## HiddenInput

標準入力の値をコンソール上に表示せずに取得するクラス。

```java
String msg = "データベース SampleDB のパスワード: ";
String masterKey = HiddenInput.read(msg);
// -> データベース SampleDB のパスワード: 
```

## HuskeyRuntimeException

huskey 実行時にユーザー側の不正な操作（例：コマンドライン引数が不正、masterKey の照合失敗など）が発生した時の例外クラス。  
この例外は cmd.CommandRunner クラスの run メソッドで捕捉され、与えられたメッセージが標準エラーとして表示される。

## UTF8

UTF8 に関連したメソッド群。  
文字コードは [このサイト](https://utf8-chartable.de/unicode-utf8-table.pl?utf8=dec) を参考に。

```java
// 文字コード (decimal) を範囲指定し、対象の文字を配列で取得する
ArrayList<String> result = UTF8.getStringsInRange(97, 122); // a-z
```

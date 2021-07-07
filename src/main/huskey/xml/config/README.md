# config

huskey 全体で用いられる設定ファイル（_~/.huskey/config.xml_）に関連したパッケージ。

## ConfigBuilder

Config インスタンスを取得するためのクラス。

```java
ConfigBuilder builder = new ConfigBuilder();
Config config = builder.build();
```

## Config

コンフィグの操作および書き出しを行うクラス。

```java
// config の構築
Config config = builder.build();

// ノードの検索
String path = "//passLength";
Node node = config.searchNode(path).item(0);

// コンフィグの書き出し
config.write();
```

DOM 操作等については database パッケージ内の README で解説しているため省略する。

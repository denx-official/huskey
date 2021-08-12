# config

huskey 全体で用いられる設定ファイル（_~/.huskey/config.xml_）に関連したパッケージ。

## ConfigBuilder

Config インスタンスを取得するためのクラス。

```java
ConfigBuilder builder = new ConfigBuilder(huskeyDir);
Config config = builder.build();
```

## Config

コンフィグの操作および書き出しを行うクラス。

```java
// config の構築
Config config = builder.build();

String path = "//defaultDB";
if (!config.exists(path)) {
    // 対象の Node が存在しないときの処理
}

// ノードの検索
Node node = config.searchNode(path);

// コンフィグの書き出し
config.write();
```

DOM 操作等については Database クラスと同様であるため詳細は割愛する（詳しくは database パッケージの README を参照）。

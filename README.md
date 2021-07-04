# huskey

## huskey とは？

**huskey** は、ターミナル上でパスワードが管理できる、Java 製クロスプラットフォーム・CUI パスワードマネージャーです。

## アーキテクチャ

![huskey_architecture](img/huskey_architecture.jpg)

- Database
  - master_key, config, Dataset をまとめたもの
- master_key
  - データベースへのアクセスに必要なキー
- config
  - パスワード生成等のデータベースの設定
- Dataset
  - Data の集合
- Data
  - タイトル、ユーザー名、パスワード、メッセージ、作成日時を持つもの

## コマンド

"\*" が付いた引数は可変長です。

丸括弧で囲まれた引数は省略可能です。  
例えば、引数 "(\<database>)" を省略した場合、既定のデータベースがターゲットとなります。

以下のコマンドは変更される可能性があります。

### General

| Commands                 | Description                                                                                                 |
| :----------------------- | :---------------------------------------------------------------------------------------------------------- |
| huskey help (\<command>) | huskey のコマンドとそれらの解説の一覧を表示する。\<command>が入力された場合、そのコマンドの詳細を表示する。 |

### Database

| Commands                                   | Description                                           |
| :----------------------------------------- | :---------------------------------------------------- |
| huskey init \<new_database>                | 新しいデータベースを作成する。                        |
| huskey change key (\<database>)            | \<database> の _master_key_ を変更する。              |
| huskey change config (\<database>)         | \<database> の設定を変更する。                        |
| huskey database                            | データベースの一覧と、既定のデータベースを表示する。  |
| huskey database --delete \<database>       | \<database> を削除する。                              |
| huskey switch \<database>                  | 既定のデータベースを \<database> に変更する。         |
| huskey merge \<database_1> (\<database_2>) | \<database_1> を \<database_2> にマージする。         |

### Dataset

| Commands                             | Description                             |
| :----------------------------------- | :-------------------------------------- |
| huskey list (\<database>) \[options] | \<database> 内の _dataset_ を表示する。 |
| huskey search \<keyword>             | \<keyword> から _dataset_ を検索する。  |

### Data

| Commands                                     | Description                           |
| :------------------------------------------- | :------------------------------------ |
| huskey get \<title> (\<database>) \[options] | \<title> の _data_ を取得する。       |
| huskey set \<title> (\<database>) \[options] | \<title> の _data_ を追加・更新する。 |
| huskey remove \*\<title> (\<database>)       | \*\<title> _data_ を削除する。        |

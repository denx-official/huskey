# crypt

データベースの暗号化/復号、ハッシュ値取得に関連したパッケージ。  
暗号化/復号のアルゴリズムには AES、ハッシュ化のアルゴリズムには BCrypt を用いる。

## BCrypt

masterKey のハッシュ値を得るためのクラス。

## Encrypt

バイト列のデータベースと masterKey と初期化ベクトルから暗号化を行い、暗号文をバイト列で返す。

## Decrypt

バイト列の暗号化されたデータベースと masterKey と初期化ベクトルから復号を行い、平文をバイト列で返す。

## 暗号モードについて

使用する暗号モードは未定、これは他のメンバーや有識者と相談しつつ決定する。

調べてみた範囲だと、CTR は実装が比較的容易らしい。  
私が見た暗号化/復号ライブラリでは、CBC がデフォルトであったり、CBC or CTR 推奨となっているものが多かった。  
ただ最近では上記のモードがクラシック（古いもの）として扱われていることもある。

### 参考

- [AES Rijndael Cipher explained as a Flash animation - YouTube](https://www.youtube.com/watch?v=gP4PqVGudtg)
- [AESを理解する - Qiita](https://qiita.com/tobira-code/items/152befa86bd515f67241)
- [C#でAES暗号化アルゴリズムを外部ライブラリに一切頼らず完全実装してみた - Qiita](https://qiita.com/kkent030315/items/ab0792aa1e8948b57490)
- [暗号技術入門04 ブロック暗号のモード〜ブロック暗号をどのように繰り返すのか〜 | SpiriteK Blog](http://www.spiritek.co.jp/spkblog/2016/12/01/%E6%9A%97%E5%8F%B7%E6%8A%80%E8%A1%93%E5%85%A5%E9%96%8004-%E3%83%96%E3%83%AD%E3%83%83%E3%82%AF%E6%9A%97%E5%8F%B7%E3%81%AE%E3%83%A2%E3%83%BC%E3%83%89%E3%80%9C%E3%83%96%E3%83%AD%E3%83%83%E3%82%AF/)
- [AES対応のPython暗号化ライブラリを比較検証してみた | DevelopersIO](https://dev.classmethod.jp/articles/python-crypto-libraries/)

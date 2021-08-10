# crypt

データベースの暗号化/復号、ハッシュ値取得に関連したパッケージ。  
暗号化/復号のアルゴリズムには AES-GCM を用いる。

## 暗号関連の実装について

プロジェクトスタート時は暗号周りの実装を全て自分たちで行う方針でいたが、その場合以下のような点が問題となる。

- AES に関しては標準ライブラリが使える。
- 独自実装が脆弱性を生む可能性。
- DENX にセキュリティーに特化した技術者がいない。

これらを考慮した結果、サードパーティ製のライブラリを使う方針に切り替えた。

## AES

AES-GCM を用いた暗号化/復号処理を行うクラス。  
それぞれを行う際に `AES.EnDeCrypt` メソッドを用いるのだが、暗号化/復号を切り替える際には変数 opmode の値を `Cipher.ENCRYPT_MODE`（暗号化）もしくは `Cipher.DECRYPT_MODE`（復号）とする。

## IV

AES-GCM に用いる初期化ベクトルを生成するクラス。

## SHA256

SHA256 によるハッシュ化処理を行うクラス。

今回用いる AES-GCM の設定では鍵長が 256 ビットと決まっているため、masterKey を `SHA256.hashText` メソッドにかけることで鍵長を統一させる。

### 参考

- [AES Rijndael Cipher explained as a Flash animation - YouTube](https://www.youtube.com/watch?v=gP4PqVGudtg)
- [AESを理解する - Qiita](https://qiita.com/tobira-code/items/152befa86bd515f67241)
- [C#でAES暗号化アルゴリズムを外部ライブラリに一切頼らず完全実装してみた - Qiita](https://qiita.com/kkent030315/items/ab0792aa1e8948b57490)
- [暗号技術入門04 ブロック暗号のモード〜ブロック暗号をどのように繰り返すのか〜 | SpiriteK Blog](http://www.spiritek.co.jp/spkblog/2016/12/01/%E6%9A%97%E5%8F%B7%E6%8A%80%E8%A1%93%E5%85%A5%E9%96%8004-%E3%83%96%E3%83%AD%E3%83%83%E3%82%AF%E6%9A%97%E5%8F%B7%E3%81%AE%E3%83%A2%E3%83%BC%E3%83%89%E3%80%9C%E3%83%96%E3%83%AD%E3%83%83%E3%82%AF/)
- [AES対応のPython暗号化ライブラリを比較検証してみた | DevelopersIO](https://dev.classmethod.jp/articles/python-crypto-libraries/)
- [サクッとBCryptで文字列を暗号化する - 其未来](https://sonomirai.hatenablog.com/entry/2018/02/23/213606)
- [Java AES 256 GCM Encryption and Decryption Example | JCE Unlimited Strength](https://www.javainterviewpoint.com/java-aes-256-gcm-encryption-and-decryption/)

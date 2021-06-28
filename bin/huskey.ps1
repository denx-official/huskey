$ErrorActionPreference = "Stop"

if (!(gcm java -ea SilentlyContinue)) { # java コマンドが存在しない場合
    echo "huskey を使用するには The Java Runtime Environment (JRE) をインストールする必要があります。"
    echo "Java | Oracle (https://java.com/) より最新版の JRE をインストールしてください。"
    exit 1
}

$ps1_file = which huskey
$bin_dir = Split-Path $ps1_file
java -classpath $bin_dir Huskey $Args

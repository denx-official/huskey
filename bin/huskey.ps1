$ps1_file = which hello
$bin_dir = Split-Path $ps1_file

java -classpath $bin_dir Huskey $Args
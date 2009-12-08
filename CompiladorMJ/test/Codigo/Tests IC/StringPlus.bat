@ECHO OFF
echo str0: "Hello">codigo.tmp
echo str1: " World. ">>codigo.tmp
echo str2: "'+' for string test is a ">>codigo.tmp
echo str3: "success">>codigo.tmp
echo+>>codigo.tmp
echo _DV_StringPlus: []>>codigo.tmp
echo+>>codigo.tmp
echo # StringPlus.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move str1, R3>>codigo.tmp
echo Library __stringCat(R2, R3), R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Move str2, R2>>codigo.tmp
echo Move str3, R3>>codigo.tmp
echo Library __stringCat(R2, R3), R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de StringPlus.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
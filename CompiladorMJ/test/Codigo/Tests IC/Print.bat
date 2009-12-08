@ECHO OFF
echo str0: "Hello World">codigo.tmp
echo+>>codigo.tmp
echo _DV_ArrayBub: []>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move str0, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayBub.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
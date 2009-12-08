@ECHO OFF
echo str0: "Error got ">codigo.tmp
echo str1: " instead of 10)">>codigo.tmp
echo+>>codigo.tmp
echo _DV_ArrayBub: []>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 10, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Library __printi(R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 10, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Library __printi(R2), Rdummy>>codigo.tmp
echo Move str1, R2>>codigo.tmp
echo Library __println(R2), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayBub.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
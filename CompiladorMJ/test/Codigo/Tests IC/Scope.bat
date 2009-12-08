@ECHO OFF
echo str0: "Error 1">codigo.tmp
echo str1: "Error 2">>codigo.tmp
echo str2: "Error 3">>codigo.tmp
echo str3: "Error 1">>codigo.tmp
echo str4: "End Of Test">>codigo.tmp
echo+>>codigo.tmp
echo _DV_TestScope: []>>codigo.tmp
echo+>>codigo.tmp
echo # TestScope.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 5, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_0>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move str1, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Move str2, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_3>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_3>>codigo.tmp
echo Move str3, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move str4, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de TestScope.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
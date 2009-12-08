@ECHO OFF
echo str0: "Not Working break">codigo.tmp
echo str1: "Not Working false, entering while any way">>codigo.tmp
echo str2: "Entering while with true expression">>codigo.tmp
echo str3: "Not Working break">>codigo.tmp
echo str4: " ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_WhileTest2: []>>codigo.tmp
echo+>>codigo.tmp
echo # WhileTest2.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Jump _salida_while_0>>codigo.tmp
echo Move str0, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move str1, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo _while_2:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_2>>codigo.tmp
echo Move str2, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Jump _salida_while_2>>codigo.tmp
echo Move str3, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _while_2>>codigo.tmp
echo _salida_while_2:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _while_3:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 8, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_3>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 5, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpFalse _otro_1>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Jump _while_3>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Library __printi(R3), Rdummy>>codigo.tmp
echo Move str4, R3>>codigo.tmp
echo Library __print(R3), Rdummy>>codigo.tmp
echo Jump _while_3>>codigo.tmp
echo _salida_while_3:>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de WhileTest2.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
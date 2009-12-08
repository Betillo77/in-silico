@ECHO OFF
echo _DV_BreakContinueTest: []>codigo.tmp
echo+>>codigo.tmp
echo # BreakContinueTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move 100, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move 400, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Sub R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_0>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _salida_while_1>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 400, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_1>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Jump _salida_while_0>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 400, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_3>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Sub R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de BreakContinueTest.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
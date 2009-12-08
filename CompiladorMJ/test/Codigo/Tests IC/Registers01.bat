@ECHO OFF
echo _DV_Registers01: []>codigo.tmp
echo+>>codigo.tmp
echo # Registers01.change>>codigo.tmp
echo _Registers01_change:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 11, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 12, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 13, R4>>codigo.tmp
echo Move p1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 14, R5>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move p1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Return 999>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move p1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Sub R6, R5>>codigo.tmp
echo StaticCall _Registers01_change(p1 = R5), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Registers01.change>>codigo.tmp
echo+>>codigo.tmp
echo # Registers01.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 1000, R5>>codigo.tmp
echo StaticCall _Registers01_change(p1 = R5), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Move R4, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Registers01.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
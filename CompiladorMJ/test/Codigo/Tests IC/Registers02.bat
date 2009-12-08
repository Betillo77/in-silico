@ECHO OFF
echo _DV_Registers02: []>codigo.tmp
echo+>>codigo.tmp
echo # Registers02.change>>codigo.tmp
echo _Registers02_change:>>codigo.tmp
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
echo Move 12, R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo Move p1, R5>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo Mul R6, R5>>codigo.tmp
echo Move p2, R6>>codigo.tmp
echo Move R2, R7>>codigo.tmp
echo Mul R7, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move p3, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo Mul R7, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move p4, R6>>codigo.tmp
echo Move R4, R7>>codigo.tmp
echo Mul R7, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Return R5>>codigo.tmp
echo # Fin de Registers02.change>>codigo.tmp
echo+>>codigo.tmp
echo # Registers02.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 3, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo Move R2, R7>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo Move R4, R9>>codigo.tmp
echo StaticCall _Registers02_change(p1 = R6, p2 = R7, p3 = R8, p4 = R9), R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Registers02.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
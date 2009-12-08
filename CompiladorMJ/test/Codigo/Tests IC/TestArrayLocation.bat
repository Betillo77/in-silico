@ECHO OFF
echo _DV_ArrayLocation: []>codigo.tmp
echo+>>codigo.tmp
echo # ArrayLocation.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 7, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 7, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Mul 4, R4>>codigo.tmp
echo Library __allocateArray(R4), R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Move 7, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move 6, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move 5, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 6, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move 13, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Mul 4, R5>>codigo.tmp
echo Library __allocateArray(R5), R5>>codigo.tmp
echo ArrayLength R5, R5>>codigo.tmp
echo Move 7, R6>>codigo.tmp
echo Mul 4, R6>>codigo.tmp
echo Library __allocateArray(R6), R6>>codigo.tmp
echo ArrayLength R6, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_0>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Library __printb(R4), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move 6, R8>>codigo.tmp
echo MoveArray R7[R8], R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayLocation.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
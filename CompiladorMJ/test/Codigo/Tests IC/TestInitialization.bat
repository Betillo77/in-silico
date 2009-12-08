@ECHO OFF
echo str0: " ">codigo.tmp
echo str1: " ">>codigo.tmp
echo str2: " ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_TestInitialazation: []>>codigo.tmp
echo _DV_Test: []>>codigo.tmp
echo+>>codigo.tmp
echo # TestInitialazation.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Library __allocateObject(16), R4>>codigo.tmp
echo MoveField _DV_Test, R4.0>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo ArrayLength R5, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move str0, R4>>codigo.tmp
echo Library __print(R4), Rdummy>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Library __printi(R4), Rdummy>>codigo.tmp
echo Move str1, R4>>codigo.tmp
echo Library __print(R4), Rdummy>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo MoveField R5.3, R4>>codigo.tmp
echo Library __printb(R4), Rdummy>>codigo.tmp
echo Move str2, R4>>codigo.tmp
echo Library __print(R4), Rdummy>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo MoveField R5.2, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_1>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Library __printb(R4), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de TestInitialazation.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
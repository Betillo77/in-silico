@ECHO OFF
echo str0: " ">codigo.tmp
echo+>>codigo.tmp
echo _DV_Temp: []>>codigo.tmp
echo _DV_Temp2: [_Temp2_print,_Temp2_printFirst]>>codigo.tmp
echo+>>codigo.tmp
echo # Temp.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(12), R2>>codigo.tmp
echo MoveField _DV_Temp2, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 7, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 5, R3>>codigo.tmp
echo Mul 4, R3>>codigo.tmp
echo Library __allocateArray(R3), R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveField R3, R4.1>>codigo.tmp
echo Move 4, R3>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo MoveArray R3, R4[R5]>>codigo.tmp
echo Move 5, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveField R3, R4.2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 43, R4>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R5.2, R4>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Temp.main>>codigo.tmp
echo+>>codigo.tmp
echo # Temp2.printFirst>>codigo.tmp
echo _Temp2_printFirst:>>codigo.tmp
echo Move this, R1>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo VirtualCall R1.0(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Temp2.printFirst>>codigo.tmp
echo+>>codigo.tmp
echo # Temp2.print>>codigo.tmp
echo _Temp2_print:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str0, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Temp2.print>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
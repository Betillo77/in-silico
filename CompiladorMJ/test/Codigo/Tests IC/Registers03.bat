@ECHO OFF
echo str0: "[1]">codigo.tmp
echo str1: "[2]">>codigo.tmp
echo str2: "[3]">>codigo.tmp
echo str3: " ">>codigo.tmp
echo str4: " ">>codigo.tmp
echo str5: " ">>codigo.tmp
echo str6: " ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_Temp: [_Temp_mul2,_Temp_mul3,_Temp_mul1]>>codigo.tmp
echo _DV_Registers03: []>>codigo.tmp
echo+>>codigo.tmp
echo # Temp.mul1>>codigo.tmp
echo _Temp_mul1:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Library __println(R2), Rdummy>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de Temp.mul1>>codigo.tmp
echo+>>codigo.tmp
echo # Temp.mul2>>codigo.tmp
echo _Temp_mul2:>>codigo.tmp
echo Move str1, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move p2, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de Temp.mul2>>codigo.tmp
echo+>>codigo.tmp
echo # Temp.mul3>>codigo.tmp
echo _Temp_mul3:>>codigo.tmp
echo Move str2, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 10000, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move p2, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de Temp.mul3>>codigo.tmp
echo+>>codigo.tmp
echo # Registers03.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(8), R2>>codigo.tmp
echo MoveField _DV_Temp, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Move 4, R6>>codigo.tmp
echo Move R6, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveField R6, R7.1>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo Move R4, R8>>codigo.tmp
echo Move R5, R9>>codigo.tmp
echo Mul R9, R8>>codigo.tmp
echo Add R8, R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo Library __printi(R6), Rdummy>>codigo.tmp
echo Move str3, R6>>codigo.tmp
echo Library __println(R6), Rdummy>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo Move R3, R9>>codigo.tmp
echo VirtualCall R7.2(this = R7, p1 = R8, p2 = R9), R6>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Move R2, R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo VirtualCall R8.0(this = R8, p1 = R9, p2 = R10), R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo Library __printi(R6), Rdummy>>codigo.tmp
echo Move str4, R6>>codigo.tmp
echo Library __println(R6), Rdummy>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo Move R3, R9>>codigo.tmp
echo VirtualCall R7.0(this = R7, p1 = R8, p2 = R9), R6>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Move R2, R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9, p2 = R10), R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo Library __printi(R6), Rdummy>>codigo.tmp
echo Move str5, R6>>codigo.tmp
echo Library __println(R6), Rdummy>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo Move R3, R9>>codigo.tmp
echo VirtualCall R7.1(this = R7, p1 = R8, p2 = R9), R6>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Move R2, R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo VirtualCall R8.0(this = R8, p1 = R9, p2 = R10), R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo Library __printi(R6), Rdummy>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveField R6, R7.1>>codigo.tmp
echo Move str6, R6>>codigo.tmp
echo Library __println(R6), Rdummy>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo Move R3, R9>>codigo.tmp
echo VirtualCall R7.1(this = R7, p1 = R8, p2 = R9), R6>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Move R2, R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9, p2 = R10), R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo Library __printi(R6), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Registers03.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
@ECHO OFF
echo str0: "Error 1">codigo.tmp
echo str1: "Error 2">>codigo.tmp
echo str2: "Error 3">>codigo.tmp
echo str3: "Error 4">>codigo.tmp
echo str4: "Hello World">>codigo.tmp
echo+>>codigo.tmp
echo _DV_VirtualFunctionCallTest: []>>codigo.tmp
echo _DV_A: [_A_plus,_A_minus]>>codigo.tmp
echo _DV_B: [_B_plus,_A_minus]>>codigo.tmp
echo _DV_C: [_C_plus,_A_minus]>>codigo.tmp
echo+>>codigo.tmp
echo # VirtualFunctionCallTest.fun>>codigo.tmp
echo _VirtualFunctionCallTest_fun:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de VirtualFunctionCallTest.fun>>codigo.tmp
echo+>>codigo.tmp
echo # VirtualFunctionCallTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Library __allocateObject(4), R2>>codigo.tmp
echo MoveField _DV_A, R2.0>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_0>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Library __allocateObject(4), R3>>codigo.tmp
echo MoveField _DV_B, R3.0>>codigo.tmp
echo StaticCall _C_castToA(p1 = R3), R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move str1, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Library __allocateObject(4), R3>>codigo.tmp
echo MoveField _DV_C, R3.0>>codigo.tmp
echo StaticCall _C_castToA(p1 = R3), R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_2>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Move str2, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Library __allocateObject(4), R2>>codigo.tmp
echo MoveField _DV_C, R2.0>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_3>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_3>>codigo.tmp
echo Move str3, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move str4, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de VirtualFunctionCallTest.main>>codigo.tmp
echo+>>codigo.tmp
echo # A.plus>>codigo.tmp
echo _A_plus:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de A.plus>>codigo.tmp
echo+>>codigo.tmp
echo # A.minus>>codigo.tmp
echo _A_minus:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Sub R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de A.minus>>codigo.tmp
echo+>>codigo.tmp
echo # B.plus>>codigo.tmp
echo _B_plus:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de B.plus>>codigo.tmp
echo+>>codigo.tmp
echo # C.plus>>codigo.tmp
echo _C_plus:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de C.plus>>codigo.tmp
echo+>>codigo.tmp
echo # C.castToA>>codigo.tmp
echo _C_castToA:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de C.castToA>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
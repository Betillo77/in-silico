@ECHO OFF
echo str0: "Ingrese el tamano del arreglo: ">codigo.tmp
echo str1: "">>codigo.tmp
echo str2: "Ingrese el elemento ">>codigo.tmp
echo str3: ": ">>codigo.tmp
echo str4: "">>codigo.tmp
echo str5: "">>codigo.tmp
echo str6: "El vector ordenado es: ">>codigo.tmp
echo str7: " ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_Quicksort: [_Quicksort_partition,_Quicksort_quicksort]>>codigo.tmp
echo+>>codigo.tmp
echo # Quicksort.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move str0, R5>>codigo.tmp
echo Library __print(R5), Rdummy>>codigo.tmp
echo Library __readi(), R5>>codigo.tmp
echo Move R5, R3>>codigo.tmp
echo Move str1, R5>>codigo.tmp
echo Library __println(R5), Rdummy>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Mul 4, R5>>codigo.tmp
echo Library __allocateArray(R5), R5>>codigo.tmp
echo Move R5, R2>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move str2, R5>>codigo.tmp
echo Library __print(R5), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Move str3, R5>>codigo.tmp
echo Library __print(R5), Rdummy>>codigo.tmp
echo Library __readi(), R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R5, R6[R7]>>codigo.tmp
echo Move str4, R5>>codigo.tmp
echo Library __println(R5), Rdummy>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Library __allocateObject(4), R5>>codigo.tmp
echo MoveField _DV_Quicksort, R5.0>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move R4, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo Move 1, R9>>codigo.tmp
echo Sub R9, R8>>codigo.tmp
echo VirtualCall R5.1(this = R5, p1 = R6, p2 = R7, p3 = R8), Rdummy>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo Move str5, R5>>codigo.tmp
echo Library __println(R5), Rdummy>>codigo.tmp
echo Move str6, R5>>codigo.tmp
echo Library __println(R5), Rdummy>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpGE _otro_1>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Move 1, R7>>codigo.tmp
echo Sub R7, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpFalse _otro_2>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _else_0>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Jump _salida_if_0>>codigo.tmp
echo _else_0:>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Library __printi(R5), Rdummy>>codigo.tmp
echo Move str7, R5>>codigo.tmp
echo Library __print(R5), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Quicksort.main>>codigo.tmp
echo+>>codigo.tmp
echo # Quicksort.partition>>codigo.tmp
echo _Quicksort_partition:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move p2, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move p3, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _while_2:>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_2>>codigo.tmp
echo _while_3:>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R2, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpGE _otro_3>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_3>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R2>>codigo.tmp
echo Jump _while_3>>codigo.tmp
echo _salida_while_3:>>codigo.tmp
echo _while_4:>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpLE _otro_4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Sub R6, R5>>codigo.tmp
echo Move R5, R3>>codigo.tmp
echo Jump _while_4>>codigo.tmp
echo _salida_while_4:>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpL _otro_5>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Jump _salida_while_2>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R2, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R2, R7>>codigo.tmp
echo MoveArray R5, R6[R7]>>codigo.tmp
echo Move R4, R5>>codigo.tmp
echo Move p1, R6>>codigo.tmp
echo Move R3, R7>>codigo.tmp
echo MoveArray R5, R6[R7]>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R2>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Sub R6, R5>>codigo.tmp
echo Move R5, R3>>codigo.tmp
echo Jump _while_2>>codigo.tmp
echo _salida_while_2:>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Return R5>>codigo.tmp
echo # Fin de Quicksort.partition>>codigo.tmp
echo+>>codigo.tmp
echo # Quicksort.quicksort>>codigo.tmp
echo _Quicksort_quicksort:>>codigo.tmp
echo Move p2, R1>>codigo.tmp
echo Move p3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpGE _otro_6>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo Move p2, R5>>codigo.tmp
echo Move p3, R6>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4, p2 = R5, p3 = R6), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move p2, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3, p2 = R4, p3 = R5), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move p3, R5>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3, p2 = R4, p3 = R5), Rdummy>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Quicksort.quicksort>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
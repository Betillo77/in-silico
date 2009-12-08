@ECHO OFF
echo str0: " ">codigo.tmp
echo str1: "">>codigo.tmp
echo+>>codigo.tmp
echo _DV_ArrayBub: [_ArrayBub_insert,_ArrayBub_bubbleSort,_ArrayBub_init,_ArrayBub_swap,_ArrayBub_test,_ArrayBub_display]>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.init>>codigo.tmp
echo _ArrayBub_init:>>codigo.tmp
echo Move 10, R1>>codigo.tmp
echo Mul 4, R1>>codigo.tmp
echo Library __allocateArray(R1), R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.init>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.insert>>codigo.tmp
echo _ArrayBub_insert:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo MoveArray R1, R2[R3]>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.2, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.insert>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.display>>codigo.tmp
echo _ArrayBub_display:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Library __printi(R2), Rdummy>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move str1, R2>>codigo.tmp
echo Library __println(R2), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.display>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.bubbleSort>>codigo.tmp
echo _ArrayBub_bubbleSort:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.2, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Sub R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpLE _otro_1>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _while_2:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_2>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo MoveArray R4[R5], R3>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.1, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Move 1, R7>>codigo.tmp
echo Add R7, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpLE _otro_3>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo VirtualCall R3.3(this = R3, p1 = R4, p2 = R5), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Jump _while_2>>codigo.tmp
echo _salida_while_2:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Sub R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.bubbleSort>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.swap>>codigo.tmp
echo _ArrayBub_swap:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Move p2, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Move p2, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.swap>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Library __allocateObject(12), R1>>codigo.tmp
echo MoveField _DV_ArrayBub, R1.0>>codigo.tmp
echo VirtualCall R1.4(this = R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayBub.main>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.test>>codigo.tmp
echo _ArrayBub_test:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(12), R2>>codigo.tmp
echo MoveField _DV_ArrayBub, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo VirtualCall R2.2(this = R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 77, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 99, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 44, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 55, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 22, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 88, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 11, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 66, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 33, R3>>codigo.tmp
echo VirtualCall R2.0(this = R2, p1 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo VirtualCall R2.5(this = R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo VirtualCall R2.1(this = R2), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo VirtualCall R2.5(this = R2), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ArrayBub.test>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
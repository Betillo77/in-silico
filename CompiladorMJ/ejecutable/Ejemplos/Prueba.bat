@ECHO OFF
echo str0: "El valor de este programa en Java fue: 23044, en MJ es:">codigo.tmp
echo str1: " ">>codigo.tmp
echo str2: "  el hijo ">>codigo.tmp
echo str3: " ">>codigo.tmp
echo str4: "Nietos: ">>codigo.tmp
echo str5: " ">>codigo.tmp
echo str6: " ">>codigo.tmp
echo str7: " ">>codigo.tmp
echo str8: "prueba">>codigo.tmp
echo str9: "Pereira">>codigo.tmp
echo str10: "fulano">>codigo.tmp
echo str11: "de tal">>codigo.tmp
echo str12: "hijo de fulano">>codigo.tmp
echo str13: "de tal">>codigo.tmp
echo str14: "ella">>codigo.tmp
echo str15: "nieto de fulano">>codigo.tmp
echo str16: "de tal">>codigo.tmp
echo str17: "ella">>codigo.tmp
echo str18: "la otra">>codigo.tmp
echo str19: " ">>codigo.tmp
echo str20: "Universidad Tecnologica de ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_Prueba: [_Prueba_iniciar,_Prueba_probarOcultamiento,_Prueba_probarOperaciones,_Prueba_probarWhileIfBreakContinue,_Prueba_probarAssigment,_Prueba_probarLiteral]>>codigo.tmp
echo _DV_padre: [_padre_ganacias,_padre_datos,_padre_labora]>>codigo.tmp
echo _DV_hijo: [_padre_ganacias,_hijo_datos,_hijo_labora,_hijo_dararray,_hijo_minovia]>>codigo.tmp
echo _DV_nieto: [_padre_ganacias,_nieto_datos,_nieto_labora,_hijo_dararray,_nieto_minovia,_nieto_universidad]>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_Prueba, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo VirtualCall R2.0(this = R2), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Prueba.main>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.iniciar>>codigo.tmp
echo _Prueba_iniciar:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo VirtualCall R4.3(this = R4), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo VirtualCall R4.1(this = R4), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo VirtualCall R4.4(this = R4), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo VirtualCall R4.5(this = R4), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo VirtualCall R4.2(this = R4), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Move 2, R7>>codigo.tmp
echo Move 2, R8>>codigo.tmp
echo Compare R8, R7>>codigo.tmp
echo JumpFalse _otro_0>>codigo.tmp
echo Move 1, R7>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Move this, R8>>codigo.tmp
echo StaticCall _Prueba_probarStatic(p1 = R4, p2 = R5, p3 = R6, p4 = R7, p5 = R8), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo StaticCall _padre_probarHerencia(), R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Library __itos(R3), R2>>codigo.tmp
echo Library __println(R2), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Prueba.iniciar>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarStatic>>codigo.tmp
echo _Prueba_probarStatic:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move p3, R2>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _else_0>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p5, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _salida_if_0>>codigo.tmp
echo _else_0:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p5, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move p4, R2>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _else_1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 32, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _salida_if_1>>codigo.tmp
echo _else_1:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p5, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de Prueba.probarStatic>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarWhileIfBreakContinue>>codigo.tmp
echo _Prueba_probarWhileIfBreakContinue:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 10, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_1>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_2>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo Jump _salida_if_2>>codigo.tmp
echo _else_2:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 100, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_4>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_3>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo Jump _salida_if_3>>codigo.tmp
echo _else_3:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo Mul R6, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 99, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_5>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_4>>codigo.tmp
echo Jump _salida_while_1>>codigo.tmp
echo Jump _salida_if_4>>codigo.tmp
echo _else_4:>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo _salida_if_4:>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 8, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_6>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_5>>codigo.tmp
echo Jump _salida_while_0>>codigo.tmp
echo Jump _salida_if_5>>codigo.tmp
echo _else_5:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo _salida_if_5:>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Mul R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Div R5, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de Prueba.probarWhileIfBreakContinue>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarOcultamiento>>codigo.tmp
echo _Prueba_probarOcultamiento:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 10, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_6>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 120, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo Move R4, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R1>>codigo.tmp
echo _salida_if_6:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de Prueba.probarOcultamiento>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarAssigment>>codigo.tmp
echo _Prueba_probarAssigment:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_Prueba, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 23, R4>>codigo.tmp
echo Mul 4, R4>>codigo.tmp
echo Library __allocateArray(R4), R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R4, R5.1>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_7>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _salida_if_7>>codigo.tmp
echo _else_7:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo _salida_if_7:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R4, R5.1>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_8>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _salida_if_8>>codigo.tmp
echo _else_8:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo _salida_if_8:>>codigo.tmp
echo Move 23, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R4, R5.2>>codigo.tmp
echo Move 32, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R4, R5.4>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 22, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move 32, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 22, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveField R4, R5.3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R4, R5.2>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo MoveField R6.2, R5>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveField R7.3, R6>>codigo.tmp
echo Div R6, R5>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveField R7.4, R6>>codigo.tmp
echo Mul R6, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Move 1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo ArrayLength R5, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Return R4>>codigo.tmp
echo # Fin de Prueba.probarAssigment>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarLiteral>>codigo.tmp
echo _Prueba_probarLiteral:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move str0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_9>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Jump _salida_if_9>>codigo.tmp
echo _else_9:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo _salida_if_9:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_10>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Jump _salida_if_10>>codigo.tmp
echo _else_10:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo _salida_if_10:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo ArrayLength R5, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Return R4>>codigo.tmp
echo # Fin de Prueba.probarLiteral>>codigo.tmp
echo+>>codigo.tmp
echo # Prueba.probarOperaciones>>codigo.tmp
echo _Prueba_probarOperaciones:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Sub R2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_7>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 11, R4>>codigo.tmp
echo Mod R4, R3>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Mul R4, R3>>codigo.tmp
echo Move 5, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Div R5, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Sub R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_11>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_11>>codigo.tmp
echo _else_11:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_11:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_8>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_12>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_12>>codigo.tmp
echo _else_12:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_12:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpLE _otro_9>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_13>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_13>>codigo.tmp
echo _else_13:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_13:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpG _otro_10>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_14>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_14>>codigo.tmp
echo _else_14:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_14:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpL _otro_11>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_15>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_15>>codigo.tmp
echo _else_15:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_15:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpFalse _otro_12>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_16>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_16>>codigo.tmp
echo _else_16:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_16:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_13>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_17>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_17>>codigo.tmp
echo _else_17:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_17:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_14>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_15>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_16>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_16>>codigo.tmp
echo _otro_16:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_16:>>codigo.tmp
echo And R4, R3>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_18>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_18>>codigo.tmp
echo _else_18:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_18:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_17>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_17>>codigo.tmp
echo _otro_17:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_17:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_18>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_19>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_19>>codigo.tmp
echo _otro_19:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_19:>>codigo.tmp
echo And R4, R3>>codigo.tmp
echo Jump _salida_otro_18>>codigo.tmp
echo _otro_18:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_18:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_19>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_19>>codigo.tmp
echo _else_19:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_19:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpFalse _otro_20>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_20>>codigo.tmp
echo _otro_20:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_20:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_21>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_22>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_22>>codigo.tmp
echo _otro_22:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_22:>>codigo.tmp
echo And R4, R3>>codigo.tmp
echo Jump _salida_otro_21>>codigo.tmp
echo _otro_21:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_21:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_20>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_20>>codigo.tmp
echo _else_20:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_20:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_23>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_23>>codigo.tmp
echo _otro_23:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_23:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_24>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_25>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_25>>codigo.tmp
echo _otro_25:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_25:>>codigo.tmp
echo And R4, R3>>codigo.tmp
echo Jump _salida_otro_24>>codigo.tmp
echo _otro_24:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_24:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_21>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_21>>codigo.tmp
echo _else_21:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_21:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_26>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_26>>codigo.tmp
echo _otro_26:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_26:>>codigo.tmp
echo Compare 1, R3>>codigo.tmp
echo JumpTrue _otro_27>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_28>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_28>>codigo.tmp
echo _otro_28:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_28:>>codigo.tmp
echo Or R4, R3>>codigo.tmp
echo Jump _salida_otro_27>>codigo.tmp
echo _otro_27:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo _salida_otro_27:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_22>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_22>>codigo.tmp
echo _else_22:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_22:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_29>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_29>>codigo.tmp
echo _otro_29:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_29:>>codigo.tmp
echo Compare 1, R3>>codigo.tmp
echo JumpTrue _otro_30>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_31>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_31>>codigo.tmp
echo _otro_31:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_31:>>codigo.tmp
echo Or R4, R3>>codigo.tmp
echo Jump _salida_otro_30>>codigo.tmp
echo _otro_30:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo _salida_otro_30:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_23>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_23>>codigo.tmp
echo _else_23:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_23:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpFalse _otro_32>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_32>>codigo.tmp
echo _otro_32:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_32:>>codigo.tmp
echo Compare 1, R3>>codigo.tmp
echo JumpTrue _otro_33>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_34>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_34>>codigo.tmp
echo _otro_34:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_34:>>codigo.tmp
echo Or R4, R3>>codigo.tmp
echo Jump _salida_otro_33>>codigo.tmp
echo _otro_33:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo _salida_otro_33:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_24>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_24>>codigo.tmp
echo _else_24:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_24:>>codigo.tmp
echo Move 2, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_35>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_35>>codigo.tmp
echo _otro_35:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_35:>>codigo.tmp
echo Compare 1, R3>>codigo.tmp
echo JumpTrue _otro_36>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_37>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_37>>codigo.tmp
echo _otro_37:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_37:>>codigo.tmp
echo Or R4, R3>>codigo.tmp
echo Jump _salida_otro_36>>codigo.tmp
echo _otro_36:>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo _salida_otro_36:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_25>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _salida_if_25>>codigo.tmp
echo _else_25:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo _salida_if_25:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de Prueba.probarOperaciones>>codigo.tmp
echo+>>codigo.tmp
echo # padre.probarHerencia>>codigo.tmp
echo _padre_probarHerencia:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_padre, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Library __allocateObject(32), R3>>codigo.tmp
echo MoveField _DV_hijo, R3.0>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Library __allocateObject(36), R4>>codigo.tmp
echo MoveField _DV_nieto, R4.0>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Library __allocateObject(32), R5>>codigo.tmp
echo MoveField _DV_hijo, R5.0>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Library __allocateObject(36), R6>>codigo.tmp
echo MoveField _DV_nieto, R6.0>>codigo.tmp
echo Move R6, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo Library __allocateObject(36), R7>>codigo.tmp
echo MoveField _DV_nieto, R7.0>>codigo.tmp
echo Move R7, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R4, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R5, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R6, R8>>codigo.tmp
echo VirtualCall R8.1(this = R8), Rdummy>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move R1, R10>>codigo.tmp
echo MoveField R10.4, R9>>codigo.tmp
echo Move str1, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo MoveField R11.2, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str2, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str3, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo MoveField R11.2, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo ArrayLength R9, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move str4, R9>>codigo.tmp
echo Move R3, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str5, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R4, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str6, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R5, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str7, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R6, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo ArrayLength R9, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Move 300, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R2, R8>>codigo.tmp
echo Move 200, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo Move 2, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R4, R8>>codigo.tmp
echo Move 22, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R5, R8>>codigo.tmp
echo Move 23, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R6, R8>>codigo.tmp
echo Move 23, R9>>codigo.tmp
echo VirtualCall R8.2(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move 2, R8>>codigo.tmp
echo Mul 4, R8>>codigo.tmp
echo Library __allocateArray(R8), R8>>codigo.tmp
echo Move R4, R10>>codigo.tmp
echo VirtualCall R10.3(this = R10), R9>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo MoveArray R8, R9[R10]>>codigo.tmp
echo Move R4, R8>>codigo.tmp
echo Move R4, R9>>codigo.tmp
echo MoveField R8, R9.6>>codigo.tmp
echo Library __allocateObject(32), R8>>codigo.tmp
echo MoveField _DV_hijo, R8.0>>codigo.tmp
echo Move R4, R17>>codigo.tmp
echo MoveField R17.6, R16>>codigo.tmp
echo MoveField R16.6, R15>>codigo.tmp
echo MoveField R15.6, R14>>codigo.tmp
echo MoveField R14.6, R13>>codigo.tmp
echo MoveField R13.6, R12>>codigo.tmp
echo MoveField R12.6, R11>>codigo.tmp
echo VirtualCall R11.3(this = R11), R10>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo MoveArray R10[R11], R9>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo MoveArray R8, R9[R10]>>codigo.tmp
echo Move str8, R8>>codigo.tmp
echo Move R4, R12>>codigo.tmp
echo MoveField R12.7, R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo MoveArray R10[R11], R9>>codigo.tmp
echo MoveField R8, R9.5>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move R4, R13>>codigo.tmp
echo VirtualCall R13.3(this = R13), R12>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo VirtualCall R10.4(this = R10), R9>>codigo.tmp
echo ArrayLength R9, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move R5, R10>>codigo.tmp
echo VirtualCall R10.4(this = R10), R9>>codigo.tmp
echo Move R6, R11>>codigo.tmp
echo VirtualCall R11.4(this = R11), R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo ArrayLength R9, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move R6, R10>>codigo.tmp
echo Move str9, R11>>codigo.tmp
echo VirtualCall R10.5(this = R10, p1 = R11), R9>>codigo.tmp
echo ArrayLength R9, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R1, R9>>codigo.tmp
echo MoveField R9.3, R8>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo MoveField R10.3, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo MoveField R10.3, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R4, R10>>codigo.tmp
echo MoveField R10.3, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R5, R10>>codigo.tmp
echo MoveField R10.3, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R6, R10>>codigo.tmp
echo MoveField R10.3, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Move R7, R9>>codigo.tmp
echo Add R9, R8>>codigo.tmp
echo Return R8>>codigo.tmp
echo # Fin de padre.probarHerencia>>codigo.tmp
echo+>>codigo.tmp
echo # padre.datos>>codigo.tmp
echo _padre_datos:>>codigo.tmp
echo Move str10, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move str11, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Move 30, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de padre.datos>>codigo.tmp
echo+>>codigo.tmp
echo # padre.labora>>codigo.tmp
echo _padre_labora:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de padre.labora>>codigo.tmp
echo+>>codigo.tmp
echo # padre.ganacias>>codigo.tmp
echo _padre_ganacias:>>codigo.tmp
echo Move this, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo VirtualCall R1.2(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.3, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de padre.ganacias>>codigo.tmp
echo+>>codigo.tmp
echo # hijo.datos>>codigo.tmp
echo _hijo_datos:>>codigo.tmp
echo Move str12, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move str13, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Move str14, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.5>>codigo.tmp
echo Move 19, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Mul 4, R1>>codigo.tmp
echo Library __allocateArray(R1), R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.7>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de hijo.datos>>codigo.tmp
echo+>>codigo.tmp
echo # hijo.labora>>codigo.tmp
echo _hijo_labora:>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 18, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpLE _otro_38>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_38>>codigo.tmp
echo _otro_38:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_38:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _else_26>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Jump _salida_if_26>>codigo.tmp
echo _else_26:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo _salida_if_26:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de hijo.labora>>codigo.tmp
echo+>>codigo.tmp
echo # hijo.minovia>>codigo.tmp
echo _hijo_minovia:>>codigo.tmp
echo Move this, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.6>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Move this, R12>>codigo.tmp
echo MoveField R12.6, R11>>codigo.tmp
echo MoveField R11.6, R10>>codigo.tmp
echo MoveField R10.6, R9>>codigo.tmp
echo MoveField R9.6, R8>>codigo.tmp
echo MoveField R8.6, R7>>codigo.tmp
echo MoveField R7.6, R6>>codigo.tmp
echo MoveField R6.6, R5>>codigo.tmp
echo MoveField R5.6, R4>>codigo.tmp
echo MoveField R4.6, R3>>codigo.tmp
echo MoveField R3.6, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.5, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de hijo.minovia>>codigo.tmp
echo+>>codigo.tmp
echo # hijo.dararray>>codigo.tmp
echo _hijo_dararray:>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.7, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de hijo.dararray>>codigo.tmp
echo+>>codigo.tmp
echo # nieto.datos>>codigo.tmp
echo _nieto_datos:>>codigo.tmp
echo Move str15, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move str16, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Move str17, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.5>>codigo.tmp
echo Move str18, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.8>>codigo.tmp
echo Move 18, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de nieto.datos>>codigo.tmp
echo+>>codigo.tmp
echo # nieto.labora>>codigo.tmp
echo _nieto_labora:>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 18, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpLE _otro_39>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_39>>codigo.tmp
echo _otro_39:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_39:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _else_27>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Mul R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Jump _salida_if_27>>codigo.tmp
echo _else_27:>>codigo.tmp
echo Move 2000, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo _salida_if_27:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de nieto.labora>>codigo.tmp
echo+>>codigo.tmp
echo # nieto.minovia>>codigo.tmp
echo _nieto_minovia:>>codigo.tmp
echo Move this, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.6>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Move this, R12>>codigo.tmp
echo MoveField R12.6, R11>>codigo.tmp
echo MoveField R11.6, R10>>codigo.tmp
echo MoveField R10.6, R9>>codigo.tmp
echo MoveField R9.6, R8>>codigo.tmp
echo MoveField R8.6, R7>>codigo.tmp
echo MoveField R7.6, R6>>codigo.tmp
echo MoveField R6.6, R5>>codigo.tmp
echo MoveField R5.6, R4>>codigo.tmp
echo MoveField R4.6, R3>>codigo.tmp
echo MoveField R3.6, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.5, R1>>codigo.tmp
echo Move str19, R2>>codigo.tmp
echo Library __stringCat(R1, R2), R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.8, R2>>codigo.tmp
echo Library __stringCat(R1, R2), R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de nieto.minovia>>codigo.tmp
echo+>>codigo.tmp
echo # nieto.universidad>>codigo.tmp
echo _nieto_universidad:>>codigo.tmp
echo Move str20, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Library __stringCat(R1, R2), R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de nieto.universidad>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
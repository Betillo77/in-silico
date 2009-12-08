@ECHO OFF
echo str0: "Algoritmo para calcular los simbolos PRIMERO/SIGUIENTE">codigo.tmp
echo str1: "">>codigo.tmp
echo str2: "Nota: para que funcione cada terminal y no terminal debe ser de longitud 1,">>codigo.tmp
echo str3: "los no terminales deben ser letras (A-Z) mayusculas, el primer simbolo debe">>codigo.tmp
echo str4: "ser el simbolo de inicio y no se debe usar el simbolo $ (fin de entrada)">>codigo.tmp
echo str5: "">>codigo.tmp
echo str6: "Ingrese el numero de reglas: ">>codigo.tmp
echo str7: "">>codigo.tmp
echo str8: "Ingrese el no terminal de la regla ">>codigo.tmp
echo str9: ": ">>codigo.tmp
echo str10: "">>codigo.tmp
echo str11: "Ingrese el cuerpo de la regla ">>codigo.tmp
echo str12: ": ">>codigo.tmp
echo str13: "">>codigo.tmp
echo str14: "No terminal ">>codigo.tmp
echo str15: ":">>codigo.tmp
echo str16: "Primero: ">>codigo.tmp
echo str17: "Siguiente: ">>codigo.tmp
echo str18: "">>codigo.tmp
echo str19: ", ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_Primeros_y_Siguientes: []>>codigo.tmp
echo _DV_Solucion: []>>codigo.tmp
echo _DV_Regla: [_Regla_esAnulable,_Regla_subRegla,_Regla_iniciarRegla]>>codigo.tmp
echo _DV_Simbolo: [_Simbolo_iniciar]>>codigo.tmp
echo _DV_Terminal: [_Terminal_iniciar]>>codigo.tmp
echo _DV_NoTerminal: [_NoTerminal_iniciar]>>codigo.tmp
echo _DV_ConjuntoSimbolos: [_ConjuntoSimbolos_buscarAgregar,_ConjuntoSimbolos_iniciar,_ConjuntoSimbolos_unir,_ConjuntoSimbolos_tostring,_ConjuntoSimbolos_agregar]>>codigo.tmp
echo+>>codigo.tmp
echo # Primeros_y_Siguientes.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo StaticCall _Solucion_solucionar(), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Primeros_y_Siguientes.main>>codigo.tmp
echo+>>codigo.tmp
echo # Solucion.solucionar>>codigo.tmp
echo _Solucion_solucionar:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Library __allocateObject(20), R5>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R5.0>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Library __allocateObject(20), R6>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R6.0>>codigo.tmp
echo Move R6, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Move R7, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Library __allocateObject(20), R8>>codigo.tmp
echo MoveField _DV_Terminal, R8.0>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move R7, R8>>codigo.tmp
echo Move 36, R9>>codigo.tmp
echo VirtualCall R8.0(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R4, R8>>codigo.tmp
echo Move 20, R9>>codigo.tmp
echo VirtualCall R8.1(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move R5, R8>>codigo.tmp
echo Move 20, R9>>codigo.tmp
echo VirtualCall R8.1(this = R8, p1 = R9), Rdummy>>codigo.tmp
echo Move str0, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str1, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str2, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str3, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str4, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str5, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move str6, R8>>codigo.tmp
echo Library __print(R8), Rdummy>>codigo.tmp
echo Library __readi(), R8>>codigo.tmp
echo Move R8, R1>>codigo.tmp
echo Move str7, R8>>codigo.tmp
echo Library __println(R8), Rdummy>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo Mul 4, R8>>codigo.tmp
echo Library __allocateArray(R8), R8>>codigo.tmp
echo Move R8, R2>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo Move R1, R9>>codigo.tmp
echo Compare R9, R8>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R8>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R8>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move 0, R9>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo Library __allocateObject(12), R14>>codigo.tmp
echo MoveField _DV_Regla, R14.0>>codigo.tmp
echo Move R14, R13>>codigo.tmp
echo Move str8, R14>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo Move 1, R17>>codigo.tmp
echo Add R17, R16>>codigo.tmp
echo Library __itos(R16), R15>>codigo.tmp
echo Library __stringCat(R14, R15), R14>>codigo.tmp
echo Move str9, R15>>codigo.tmp
echo Library __stringCat(R14, R15), R14>>codigo.tmp
echo Library __print(R14), Rdummy>>codigo.tmp
echo Library __readln(), R14>>codigo.tmp
echo Move R14, R8>>codigo.tmp
echo Move str10, R14>>codigo.tmp
echo Library __println(R14), Rdummy>>codigo.tmp
echo Move R8, R15>>codigo.tmp
echo Library __stoa(R15), R14>>codigo.tmp
echo Move R14, R9>>codigo.tmp
echo Move R5, R15>>codigo.tmp
echo Move R9, R17>>codigo.tmp
echo Move 0, R18>>codigo.tmp
echo MoveArray R17[R18], R16>>codigo.tmp
echo Move 0, R17>>codigo.tmp
echo VirtualCall R15.0(this = R15, p1 = R16, p2 = R17), R14>>codigo.tmp
echo Move R14, R12>>codigo.tmp
echo Move str11, R14>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo Move 1, R17>>codigo.tmp
echo Add R17, R16>>codigo.tmp
echo Library __itos(R16), R15>>codigo.tmp
echo Library __stringCat(R14, R15), R14>>codigo.tmp
echo Move str12, R15>>codigo.tmp
echo Library __stringCat(R14, R15), R14>>codigo.tmp
echo Library __print(R14), Rdummy>>codigo.tmp
echo Library __readln(), R14>>codigo.tmp
echo Move R14, R10>>codigo.tmp
echo Move str13, R14>>codigo.tmp
echo Library __println(R14), Rdummy>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Library __stoa(R15), R14>>codigo.tmp
echo Move R14, R11>>codigo.tmp
echo Move R13, R14>>codigo.tmp
echo Move R12, R15>>codigo.tmp
echo Move R11, R16>>codigo.tmp
echo Move R5, R17>>codigo.tmp
echo Move R4, R18>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15, p2 = R16, p3 = R17, p4 = R18), Rdummy>>codigo.tmp
echo Move R13, R14>>codigo.tmp
echo Move R2, R15>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo MoveArray R14, R15[R16]>>codigo.tmp
echo Move R3, R14>>codigo.tmp
echo Move 0, R15>>codigo.tmp
echo Compare R15, R14>>codigo.tmp
echo JumpFalse _otro_1>>codigo.tmp
echo Move 1, R14>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R14>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R14>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Move R14, R6>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R3, R14>>codigo.tmp
echo Move 1, R15>>codigo.tmp
echo Add R15, R14>>codigo.tmp
echo Move R14, R3>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R6, R10>>codigo.tmp
echo MoveField R10.1, R9>>codigo.tmp
echo Move R7, R10>>codigo.tmp
echo VirtualCall R9.4(this = R9, p1 = R10), R8>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move 1, R8>>codigo.tmp
echo Compare 0, R8>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move 0, R9>>codigo.tmp
echo Move R9, R8>>codigo.tmp
echo Move 0, R9>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo Move R10, R9>>codigo.tmp
echo _while_2:>>codigo.tmp
echo Move R8, R10>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpGE _otro_2>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_while_2>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo Move R2, R12>>codigo.tmp
echo Move R8, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Move R11, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move R11, R3>>codigo.tmp
echo Move R10, R12>>codigo.tmp
echo VirtualCall R12.0(this = R12), R11>>codigo.tmp
echo Compare 0, R11>>codigo.tmp
echo JumpTrue _otro_3>>codigo.tmp
echo Move R10, R14>>codigo.tmp
echo MoveField R14.2, R13>>codigo.tmp
echo MoveField R13.3, R12>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo And R12, R11>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R11>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Move R11, R9>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Move R10, R13>>codigo.tmp
echo MoveField R13.2, R12>>codigo.tmp
echo MoveField R11, R12.3>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo _while_3:>>codigo.tmp
echo Move R3, R11>>codigo.tmp
echo Move R10, R13>>codigo.tmp
echo MoveField R13.1, R12>>codigo.tmp
echo ArrayLength R12, R12>>codigo.tmp
echo Compare R12, R11>>codigo.tmp
echo JumpGE _otro_5>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 0, R11>>codigo.tmp
echo JumpTrue _salida_while_3>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo Add R13, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpFalse _otro_6>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Compare 1, R12>>codigo.tmp
echo JumpTrue _otro_7>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Move 0, R16>>codigo.tmp
echo Move R3, R17>>codigo.tmp
echo Move 1, R18>>codigo.tmp
echo Sub R18, R17>>codigo.tmp
echo Move R5, R18>>codigo.tmp
echo Move R4, R19>>codigo.tmp
echo VirtualCall R15.1(this = R15, p1 = R16, p2 = R17, p3 = R18, p4 = R19), R14>>codigo.tmp
echo VirtualCall R14.0(this = R14), R13>>codigo.tmp
echo Or R13, R12>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move R10, R16>>codigo.tmp
echo MoveField R16.2, R15>>codigo.tmp
echo MoveField R15.4, R14>>codigo.tmp
echo Move R10, R18>>codigo.tmp
echo MoveField R18.1, R17>>codigo.tmp
echo Move R3, R18>>codigo.tmp
echo MoveArray R17[R18], R16>>codigo.tmp
echo MoveField R16.4, R15>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15), R13>>codigo.tmp
echo Move R13, R12>>codigo.tmp
echo Move R9, R13>>codigo.tmp
echo Compare 1, R13>>codigo.tmp
echo JumpTrue _otro_8>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Or R14, R13>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Move R13, R9>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move R10, R14>>codigo.tmp
echo MoveField R14.1, R13>>codigo.tmp
echo ArrayLength R13, R13>>codigo.tmp
echo Move 1, R14>>codigo.tmp
echo Sub R14, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpFalse _otro_9>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Compare 1, R12>>codigo.tmp
echo JumpTrue _otro_10>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo Move 1, R17>>codigo.tmp
echo Add R17, R16>>codigo.tmp
echo Move R10, R18>>codigo.tmp
echo MoveField R18.1, R17>>codigo.tmp
echo ArrayLength R17, R17>>codigo.tmp
echo Move 1, R18>>codigo.tmp
echo Sub R18, R17>>codigo.tmp
echo Move R5, R18>>codigo.tmp
echo Move R4, R19>>codigo.tmp
echo VirtualCall R15.1(this = R15, p1 = R16, p2 = R17, p3 = R18, p4 = R19), R14>>codigo.tmp
echo VirtualCall R14.0(this = R14), R13>>codigo.tmp
echo Or R13, R12>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_if_3>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move R10, R17>>codigo.tmp
echo MoveField R17.1, R16>>codigo.tmp
echo Move R3, R17>>codigo.tmp
echo MoveArray R16[R17], R15>>codigo.tmp
echo MoveField R15.1, R14>>codigo.tmp
echo Move R10, R17>>codigo.tmp
echo MoveField R17.2, R16>>codigo.tmp
echo MoveField R16.1, R15>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15), R13>>codigo.tmp
echo Move R13, R12>>codigo.tmp
echo Move R9, R13>>codigo.tmp
echo Compare 1, R13>>codigo.tmp
echo JumpTrue _otro_11>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Or R14, R13>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Move R13, R9>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo _while_4:>>codigo.tmp
echo Move R11, R12>>codigo.tmp
echo Move R10, R14>>codigo.tmp
echo MoveField R14.1, R13>>codigo.tmp
echo ArrayLength R13, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpGE _otro_12>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_while_4>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpFalse _otro_13>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Compare 1, R12>>codigo.tmp
echo JumpTrue _otro_14>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Move 0, R16>>codigo.tmp
echo Move R3, R17>>codigo.tmp
echo Move 1, R18>>codigo.tmp
echo Sub R18, R17>>codigo.tmp
echo Move R5, R18>>codigo.tmp
echo Move R4, R19>>codigo.tmp
echo VirtualCall R15.1(this = R15, p1 = R16, p2 = R17, p3 = R18, p4 = R19), R14>>codigo.tmp
echo VirtualCall R14.0(this = R14), R13>>codigo.tmp
echo Or R13, R12>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_if_4>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move R10, R16>>codigo.tmp
echo MoveField R16.2, R15>>codigo.tmp
echo MoveField R15.4, R14>>codigo.tmp
echo Move R10, R18>>codigo.tmp
echo MoveField R18.1, R17>>codigo.tmp
echo Move R3, R18>>codigo.tmp
echo MoveArray R17[R18], R16>>codigo.tmp
echo MoveField R16.4, R15>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15), R13>>codigo.tmp
echo Move R13, R12>>codigo.tmp
echo Move R9, R13>>codigo.tmp
echo Compare 1, R13>>codigo.tmp
echo JumpTrue _otro_15>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Or R14, R13>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Move R13, R9>>codigo.tmp
echo _salida_if_4:>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move R10, R14>>codigo.tmp
echo MoveField R14.1, R13>>codigo.tmp
echo ArrayLength R13, R13>>codigo.tmp
echo Move 1, R14>>codigo.tmp
echo Sub R14, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpFalse _otro_16>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_16>>codigo.tmp
echo _otro_16:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_16:>>codigo.tmp
echo Compare 1, R12>>codigo.tmp
echo JumpTrue _otro_17>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo Move 1, R17>>codigo.tmp
echo Add R17, R16>>codigo.tmp
echo Move R10, R18>>codigo.tmp
echo MoveField R18.1, R17>>codigo.tmp
echo ArrayLength R17, R17>>codigo.tmp
echo Move 1, R18>>codigo.tmp
echo Sub R18, R17>>codigo.tmp
echo Move R5, R18>>codigo.tmp
echo Move R4, R19>>codigo.tmp
echo VirtualCall R15.1(this = R15, p1 = R16, p2 = R17, p3 = R18, p4 = R19), R14>>codigo.tmp
echo VirtualCall R14.0(this = R14), R13>>codigo.tmp
echo Or R13, R12>>codigo.tmp
echo Jump _salida_otro_17>>codigo.tmp
echo _otro_17:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_17:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_if_5>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move R10, R17>>codigo.tmp
echo MoveField R17.1, R16>>codigo.tmp
echo Move R3, R17>>codigo.tmp
echo MoveArray R16[R17], R15>>codigo.tmp
echo MoveField R15.1, R14>>codigo.tmp
echo Move R10, R17>>codigo.tmp
echo MoveField R17.2, R16>>codigo.tmp
echo MoveField R16.1, R15>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15), R13>>codigo.tmp
echo Move R13, R12>>codigo.tmp
echo Move R9, R13>>codigo.tmp
echo Compare 1, R13>>codigo.tmp
echo JumpTrue _otro_18>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Or R14, R13>>codigo.tmp
echo Jump _salida_otro_18>>codigo.tmp
echo _otro_18:>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo _salida_otro_18:>>codigo.tmp
echo Move R13, R9>>codigo.tmp
echo _salida_if_5:>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo Add R13, R12>>codigo.tmp
echo Move R11, R13>>codigo.tmp
echo Compare R13, R12>>codigo.tmp
echo JumpFalse _otro_19>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Jump _salida_otro_19>>codigo.tmp
echo _otro_19:>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo _salida_otro_19:>>codigo.tmp
echo Compare 1, R12>>codigo.tmp
echo JumpTrue _otro_20>>codigo.tmp
echo Move R10, R15>>codigo.tmp
echo Move R3, R16>>codigo.tmp
echo Move 1, R17>>codigo.tmp
echo Add R17, R16>>codigo.tmp
echo Move R11, R17>>codigo.tmp
echo Move 1, R18>>codigo.tmp
echo Sub R18, R17>>codigo.tmp
echo Move R5, R18>>codigo.tmp
echo Move R4, R19>>codigo.tmp
echo VirtualCall R15.1(this = R15, p1 = R16, p2 = R17, p3 = R18, p4 = R19), R14>>codigo.tmp
echo VirtualCall R14.0(this = R14), R13>>codigo.tmp
echo Or R13, R12>>codigo.tmp
echo Jump _salida_otro_20>>codigo.tmp
echo _otro_20:>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo _salida_otro_20:>>codigo.tmp
echo Compare 0, R12>>codigo.tmp
echo JumpTrue _salida_if_6>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Move R10, R17>>codigo.tmp
echo MoveField R17.1, R16>>codigo.tmp
echo Move R3, R17>>codigo.tmp
echo MoveArray R16[R17], R15>>codigo.tmp
echo MoveField R15.1, R14>>codigo.tmp
echo Move R10, R18>>codigo.tmp
echo MoveField R18.1, R17>>codigo.tmp
echo Move R11, R18>>codigo.tmp
echo MoveArray R17[R18], R16>>codigo.tmp
echo MoveField R16.4, R15>>codigo.tmp
echo VirtualCall R14.2(this = R14, p1 = R15), R13>>codigo.tmp
echo Move R13, R12>>codigo.tmp
echo Move R9, R13>>codigo.tmp
echo Compare 1, R13>>codigo.tmp
echo JumpTrue _otro_21>>codigo.tmp
echo Move R12, R14>>codigo.tmp
echo Or R14, R13>>codigo.tmp
echo Jump _salida_otro_21>>codigo.tmp
echo _otro_21:>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo _salida_otro_21:>>codigo.tmp
echo Move R13, R9>>codigo.tmp
echo _salida_if_6:>>codigo.tmp
echo Move R11, R12>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo Add R13, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Jump _while_4>>codigo.tmp
echo _salida_while_4:>>codigo.tmp
echo Move R3, R12>>codigo.tmp
echo Move 1, R13>>codigo.tmp
echo Add R13, R12>>codigo.tmp
echo Move R12, R3>>codigo.tmp
echo Jump _while_3>>codigo.tmp
echo _salida_while_3:>>codigo.tmp
echo Move R8, R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Add R12, R11>>codigo.tmp
echo Move R11, R8>>codigo.tmp
echo Jump _while_2>>codigo.tmp
echo _salida_while_2:>>codigo.tmp
echo Move R9, R10>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _otro_22>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo Jump _salida_otro_22>>codigo.tmp
echo _otro_22:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo _salida_otro_22:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_7>>codigo.tmp
echo Jump _salida_while_1>>codigo.tmp
echo _salida_if_7:>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move R8, R3>>codigo.tmp
echo _while_5:>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo Move R5, R10>>codigo.tmp
echo MoveField R10.1, R9>>codigo.tmp
echo Compare R9, R8>>codigo.tmp
echo JumpGE _otro_23>>codigo.tmp
echo Move 1, R8>>codigo.tmp
echo Jump _salida_otro_23>>codigo.tmp
echo _otro_23:>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo _salida_otro_23:>>codigo.tmp
echo Compare 0, R8>>codigo.tmp
echo JumpTrue _salida_while_5>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move R5, R11>>codigo.tmp
echo MoveField R11.4, R10>>codigo.tmp
echo Move R3, R11>>codigo.tmp
echo MoveArray R10[R11], R9>>codigo.tmp
echo Move R9, R8>>codigo.tmp
echo Move 0, R9>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Mul 4, R10>>codigo.tmp
echo Library __allocateArray(R10), R10>>codigo.tmp
echo Move R10, R9>>codigo.tmp
echo Move R8, R11>>codigo.tmp
echo MoveField R11.2, R10>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R10, R11[R12]>>codigo.tmp
echo Move str14, R10>>codigo.tmp
echo Move R9, R12>>codigo.tmp
echo Library __atos(R12), R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Move str15, R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo Move str16, R10>>codigo.tmp
echo Move R8, R13>>codigo.tmp
echo MoveField R13.4, R12>>codigo.tmp
echo VirtualCall R12.3(this = R12), R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo Move str17, R10>>codigo.tmp
echo Move R8, R13>>codigo.tmp
echo MoveField R13.1, R12>>codigo.tmp
echo VirtualCall R12.3(this = R12), R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move R10, R3>>codigo.tmp
echo Jump _while_5>>codigo.tmp
echo _salida_while_5:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Solucion.solucionar>>codigo.tmp
echo+>>codigo.tmp
echo # Regla.iniciarRegla>>codigo.tmp
echo _Regla_iniciarRegla:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move p2, R2>>codigo.tmp
echo ArrayLength R2, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R2, R3.1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R2, R3.2>>codigo.tmp
echo _while_6:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo ArrayLength R3, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_24>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_24>>codigo.tmp
echo _otro_24:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_24:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_6>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move p2, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveArray R4[R5], R3>>codigo.tmp
echo Move 65, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpL _otro_25>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_25>>codigo.tmp
echo _otro_25:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_25:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _otro_26>>codigo.tmp
echo Move p2, R5>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Move 90, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_27>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_27>>codigo.tmp
echo _otro_27:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_27:>>codigo.tmp
echo And R4, R3>>codigo.tmp
echo Jump _salida_otro_26>>codigo.tmp
echo _otro_26:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_26:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _else_8>>codigo.tmp
echo Move p3, R4>>codigo.tmp
echo Move p2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo VirtualCall R4.0(this = R4, p1 = R5, p2 = R6), R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Jump _salida_if_8>>codigo.tmp
echo _else_8:>>codigo.tmp
echo Move p4, R4>>codigo.tmp
echo Move p2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo VirtualCall R4.0(this = R4, p1 = R5, p2 = R6), R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _salida_if_8:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveArray R3, R4[R5]>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Jump _while_6>>codigo.tmp
echo _salida_while_6:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Regla.iniciarRegla>>codigo.tmp
echo+>>codigo.tmp
echo # Regla.subRegla>>codigo.tmp
echo _Regla_subRegla:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p2, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Sub R3, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo _while_7:>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move p2, R6>>codigo.tmp
echo Compare R6, R5>>codigo.tmp
echo JumpG _otro_28>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Jump _salida_otro_28>>codigo.tmp
echo _otro_28:>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo _salida_otro_28:>>codigo.tmp
echo Compare 0, R5>>codigo.tmp
echo JumpTrue _salida_while_7>>codigo.tmp
echo Move this, R8>>codigo.tmp
echo MoveField R8.1, R7>>codigo.tmp
echo Move R3, R8>>codigo.tmp
echo MoveArray R7[R8], R6>>codigo.tmp
echo MoveField R6.2, R5>>codigo.tmp
echo Move R1, R6>>codigo.tmp
echo Move R4, R7>>codigo.tmp
echo MoveArray R5, R6[R7]>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R3>>codigo.tmp
echo Move R4, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Jump _while_7>>codigo.tmp
echo _salida_while_7:>>codigo.tmp
echo Library __allocateObject(12), R5>>codigo.tmp
echo MoveField _DV_Regla, R5.0>>codigo.tmp
echo Move R5, R2>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Move this, R7>>codigo.tmp
echo MoveField R7.2, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo Move p3, R8>>codigo.tmp
echo Move p4, R9>>codigo.tmp
echo VirtualCall R5.2(this = R5, p1 = R6, p2 = R7, p3 = R8, p4 = R9), Rdummy>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Return R5>>codigo.tmp
echo # Fin de Regla.subRegla>>codigo.tmp
echo+>>codigo.tmp
echo # Regla.esAnulable>>codigo.tmp
echo _Regla_esAnulable:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_8:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo ArrayLength R3, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_29>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_29>>codigo.tmp
echo _otro_29:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_29:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_8>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveArray R4[R5], R3>>codigo.tmp
echo MoveField R3.3, R2>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _otro_30>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Jump _salida_otro_30>>codigo.tmp
echo _otro_30:>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo _salida_otro_30:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_if_9>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo _salida_if_9:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_8>>codigo.tmp
echo _salida_while_8:>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de Regla.esAnulable>>codigo.tmp
echo+>>codigo.tmp
echo # Simbolo.iniciar>>codigo.tmp
echo _Simbolo_iniciar:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Simbolo.iniciar>>codigo.tmp
echo+>>codigo.tmp
echo # Terminal.iniciar>>codigo.tmp
echo _Terminal_iniciar:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Library __allocateObject(20), R1>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R1.0>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.4, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo VirtualCall R1.1(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.4, R2>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo VirtualCall R2.4(this = R2, p1 = R3), R1>>codigo.tmp
echo Library __allocateObject(20), R1>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R1.0>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo VirtualCall R1.1(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Terminal.iniciar>>codigo.tmp
echo+>>codigo.tmp
echo # NoTerminal.iniciar>>codigo.tmp
echo _NoTerminal_iniciar:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Library __allocateObject(20), R1>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R1.0>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.4, R1>>codigo.tmp
echo Move 20, R2>>codigo.tmp
echo VirtualCall R1.1(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Library __allocateObject(20), R1>>codigo.tmp
echo MoveField _DV_ConjuntoSimbolos, R1.0>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 20, R2>>codigo.tmp
echo VirtualCall R1.1(this = R1, p1 = R2), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de NoTerminal.iniciar>>codigo.tmp
echo+>>codigo.tmp
echo # ConjuntoSimbolos.iniciar>>codigo.tmp
echo _ConjuntoSimbolos_iniciar:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Mul 4, R1>>codigo.tmp
echo Library __allocateArray(R1), R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.4>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.2>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.3>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ConjuntoSimbolos.iniciar>>codigo.tmp
echo+>>codigo.tmp
echo # ConjuntoSimbolos.buscarAgregar>>codigo.tmp
echo _ConjuntoSimbolos_buscarAgregar:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_9:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_31>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_31>>codigo.tmp
echo _otro_31:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_31:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_9>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.4, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveArray R4[R5], R3>>codigo.tmp
echo MoveField R3.2, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpFalse _otro_32>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_32>>codigo.tmp
echo _otro_32:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_32:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_if_10>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.4, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo _salida_if_10:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_9>>codigo.tmp
echo _salida_while_9:>>codigo.tmp
echo Move p2, R2>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _otro_33>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Jump _salida_otro_33>>codigo.tmp
echo _otro_33:>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo _salida_otro_33:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _else_11>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Library __allocateObject(20), R3>>codigo.tmp
echo MoveField _DV_NoTerminal, R3.0>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo VirtualCall R4.4(this = R4, p1 = R5), R3>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo Jump _salida_if_11>>codigo.tmp
echo _else_11:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Library __allocateObject(20), R3>>codigo.tmp
echo MoveField _DV_Terminal, R3.0>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo VirtualCall R3.0(this = R3, p1 = R4), Rdummy>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo VirtualCall R4.4(this = R4, p1 = R5), R3>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo _salida_if_11:>>codigo.tmp
echo # Fin de ConjuntoSimbolos.buscarAgregar>>codigo.tmp
echo+>>codigo.tmp
echo # ConjuntoSimbolos.agregar>>codigo.tmp
echo _ConjuntoSimbolos_agregar:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_10:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.1, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_34>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_34>>codigo.tmp
echo _otro_34:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_34:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_10>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.4, R4>>codigo.tmp
echo Move R1, R5>>codigo.tmp
echo MoveArray R4[R5], R3>>codigo.tmp
echo MoveField R3.2, R2>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpFalse _otro_35>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_35>>codigo.tmp
echo _otro_35:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_35:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_if_12>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo _salida_if_12:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_10>>codigo.tmp
echo _salida_while_10:>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpLE _otro_36>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_36>>codigo.tmp
echo _otro_36:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_36:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _else_13>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.2, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Mul R5, R4>>codigo.tmp
echo Mul 4, R4>>codigo.tmp
echo Library __allocateArray(R4), R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.2, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Mul R5, R4>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R4, R5.2>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R4, R5.1>>codigo.tmp
echo _while_11:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.4, R5>>codigo.tmp
echo ArrayLength R5, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_37>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_37>>codigo.tmp
echo _otro_37:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_37:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_while_11>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.4, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo MoveArray R5[R6], R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _while_11>>codigo.tmp
echo _salida_while_11:>>codigo.tmp
echo Move p1, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move R2, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move R3, R4>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R4, R5.4>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Return R4>>codigo.tmp
echo Jump _salida_if_13>>codigo.tmp
echo _else_13:>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.4, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R3.1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo MoveField R2, R3.1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo _salida_if_13:>>codigo.tmp
echo # Fin de ConjuntoSimbolos.agregar>>codigo.tmp
echo+>>codigo.tmp
echo # ConjuntoSimbolos.unir>>codigo.tmp
echo _ConjuntoSimbolos_unir:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move this, R4>>codigo.tmp
echo MoveField R4.3, R3>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_14>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo _salida_if_14:>>codigo.tmp
echo _while_12:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move p1, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_38>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_38>>codigo.tmp
echo _otro_38:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_38:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_12>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo Move p1, R8>>codigo.tmp
echo MoveField R8.4, R7>>codigo.tmp
echo Move R1, R8>>codigo.tmp
echo MoveArray R7[R8], R6>>codigo.tmp
echo VirtualCall R5.4(this = R5, p1 = R6), R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Compare 1, R4>>codigo.tmp
echo JumpTrue _otro_39>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Or R5, R4>>codigo.tmp
echo Jump _salida_otro_39>>codigo.tmp
echo _otro_39:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo _salida_otro_39:>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Jump _while_12>>codigo.tmp
echo _salida_while_12:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de ConjuntoSimbolos.unir>>codigo.tmp
echo+>>codigo.tmp
echo # ConjuntoSimbolos.tostring>>codigo.tmp
echo _ConjuntoSimbolos_tostring:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move str18, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _while_13:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move this, R5>>codigo.tmp
echo MoveField R5.1, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_40>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_40>>codigo.tmp
echo _otro_40:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_40:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_13>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Mul 4, R4>>codigo.tmp
echo Library __allocateArray(R4), R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move this, R7>>codigo.tmp
echo MoveField R7.4, R6>>codigo.tmp
echo Move R1, R7>>codigo.tmp
echo MoveArray R6[R7], R5>>codigo.tmp
echo MoveField R5.2, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo MoveArray R4, R5[R6]>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move this, R6>>codigo.tmp
echo MoveField R6.1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Sub R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_41>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_41>>codigo.tmp
echo _otro_41:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_41:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_15>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Library __atos(R6), R5>>codigo.tmp
echo Library __stringCat(R4, R5), R4>>codigo.tmp
echo Move str19, R5>>codigo.tmp
echo Library __stringCat(R4, R5), R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Jump _salida_if_15>>codigo.tmp
echo _else_15:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R3, R6>>codigo.tmp
echo Library __atos(R6), R5>>codigo.tmp
echo Library __stringCat(R4, R5), R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo _salida_if_15:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Jump _while_13>>codigo.tmp
echo _salida_while_13:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de ConjuntoSimbolos.tostring>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
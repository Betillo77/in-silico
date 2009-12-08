@ECHO OFF
echo str0: "Checking boolean literals">codigo.tmp
echo str1: " ">>codigo.tmp
echo str2: "">>codigo.tmp
echo str3: "Checking '!'">>codigo.tmp
echo str4: " ">>codigo.tmp
echo str5: "">>codigo.tmp
echo str6: "Checking '=='">>codigo.tmp
echo str7: " ">>codigo.tmp
echo str8: "">>codigo.tmp
echo str9: "Checking '!='">>codigo.tmp
echo str10: " ">>codigo.tmp
echo str11: "">>codigo.tmp
echo str12: "Checking '<'">>codigo.tmp
echo str13: " ">>codigo.tmp
echo str14: "">>codigo.tmp
echo str15: "Checking '>'">>codigo.tmp
echo str16: " ">>codigo.tmp
echo str17: "">>codigo.tmp
echo str18: "Checking '<='">>codigo.tmp
echo str19: " ">>codigo.tmp
echo str20: " ">>codigo.tmp
echo str21: "">>codigo.tmp
echo str22: "Checking '>='">>codigo.tmp
echo str23: " ">>codigo.tmp
echo str24: " ">>codigo.tmp
echo str25: "">>codigo.tmp
echo str26: "Checking '&&'">>codigo.tmp
echo str27: " ">>codigo.tmp
echo str28: " ">>codigo.tmp
echo str29: " ">>codigo.tmp
echo str30: "">>codigo.tmp
echo str31: "Checking '||'">>codigo.tmp
echo str32: " ">>codigo.tmp
echo str33: " ">>codigo.tmp
echo str34: " ">>codigo.tmp
echo str35: "">>codigo.tmp
echo str36: " ">>codigo.tmp
echo str37: " ">>codigo.tmp
echo str38: " ">>codigo.tmp
echo str39: " ">>codigo.tmp
echo str40: " ">>codigo.tmp
echo str41: " ">>codigo.tmp
echo str42: " ">>codigo.tmp
echo str43: " ">>codigo.tmp
echo str44: "">>codigo.tmp
echo str45: "">>codigo.tmp
echo str46: "Statring the last block of tests">>codigo.tmp
echo str47: " ">>codigo.tmp
echo str48: " ">>codigo.tmp
echo str49: " ">>codigo.tmp
echo str50: " ">>codigo.tmp
echo str51: "">>codigo.tmp
echo str52: " ">>codigo.tmp
echo str53: " ">>codigo.tmp
echo str54: " ">>codigo.tmp
echo str55: " ">>codigo.tmp
echo str56: "">>codigo.tmp
echo str57: " ">>codigo.tmp
echo str58: " ">>codigo.tmp
echo str59: " ">>codigo.tmp
echo str60: " ">>codigo.tmp
echo str61: "">>codigo.tmp
echo str62: " ">>codigo.tmp
echo str63: " ">>codigo.tmp
echo str64: " ">>codigo.tmp
echo str65: " ">>codigo.tmp
echo+>>codigo.tmp
echo _DV_ArrayBub: []>>codigo.tmp
echo _DV_ComplexTester: [_ComplexTester_test,_ComplexTester_addToCounter]>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move str0, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str1, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str2, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str3, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_0>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str4, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str5, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str6, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpFalse _otro_2>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str7, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpFalse _otro_3>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str8, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str9, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str10, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 4, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_5>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str11, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str12, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpGE _otro_6>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str13, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpGE _otro_7>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str14, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str15, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpLE _otro_8>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str16, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpGE _otro_9>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str17, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str18, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpG _otro_10>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str19, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpG _otro_11>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str20, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpG _otro_12>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str21, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str22, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpL _otro_13>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str23, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 2, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpL _otro_14>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str24, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpL _otro_15>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str25, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str26, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_16>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_16>>codigo.tmp
echo _otro_16:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_16:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str27, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_17>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_17>>codigo.tmp
echo _otro_17:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_17:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str28, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_18>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_18>>codigo.tmp
echo _otro_18:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_18:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str29, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_19>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_19>>codigo.tmp
echo _otro_19:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_19:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str30, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str31, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_20>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_20>>codigo.tmp
echo _otro_20:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_20:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str32, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_21>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_21>>codigo.tmp
echo _otro_21:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_21:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str33, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_22>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_22>>codigo.tmp
echo _otro_22:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_22:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str34, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_23>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_23>>codigo.tmp
echo _otro_23:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_23:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move str35, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Library __allocateObject(8), R1>>codigo.tmp
echo MoveField _DV_ComplexTester, R1.0>>codigo.tmp
echo VirtualCall R1.0(this = R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayBub.main>>codigo.tmp
echo+>>codigo.tmp
echo # ComplexTester.addToCounter>>codigo.tmp
echo _ComplexTester_addToCounter:>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de ComplexTester.addToCounter>>codigo.tmp
echo+>>codigo.tmp
echo # ComplexTester.test>>codigo.tmp
echo _ComplexTester_test:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R1, R2.1>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_24>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_24>>codigo.tmp
echo _otro_24:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_24:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str36, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_25>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_25>>codigo.tmp
echo _otro_25:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_25:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str37, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_26>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_26>>codigo.tmp
echo _otro_26:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_26:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str38, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_27>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_27>>codigo.tmp
echo _otro_27:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_27:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str39, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_28>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_28>>codigo.tmp
echo _otro_28:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_28:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str40, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_29>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_29>>codigo.tmp
echo _otro_29:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_29:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str41, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_30>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_30>>codigo.tmp
echo _otro_30:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_30:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str42, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_31>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_31>>codigo.tmp
echo _otro_31:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_31:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str43, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move str44, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str45, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move str46, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_32>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_32>>codigo.tmp
echo _otro_32:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_32:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str47, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_33>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_33>>codigo.tmp
echo _otro_33:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_33:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str48, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_34>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_34>>codigo.tmp
echo _otro_34:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_34:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str49, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_35>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_35>>codigo.tmp
echo _otro_35:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_35:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str50, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move str51, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_36>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_36>>codigo.tmp
echo _otro_36:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_36:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str52, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_37>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_37>>codigo.tmp
echo _otro_37:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_37:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str53, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_38>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_38>>codigo.tmp
echo _otro_38:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_38:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str54, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_39>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_39>>codigo.tmp
echo _otro_39:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_39:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str55, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move str56, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_40>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_40>>codigo.tmp
echo _otro_40:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_40:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str57, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_41>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_41>>codigo.tmp
echo _otro_41:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_41:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str58, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_42>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_42>>codigo.tmp
echo _otro_42:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_42:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str59, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_43>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_43>>codigo.tmp
echo _otro_43:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_43:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str60, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move str61, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_44>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_44>>codigo.tmp
echo _otro_44:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_44:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str62, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_45>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_45>>codigo.tmp
echo _otro_45:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_45:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str63, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_46>>codigo.tmp
echo Move this, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo VirtualCall R3.1(this = R3, p1 = R4), R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_46>>codigo.tmp
echo _otro_46:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_46:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str64, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo VirtualCall R2.1(this = R2, p1 = R3), R1>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_47>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo And R2, R1>>codigo.tmp
echo Jump _salida_otro_47>>codigo.tmp
echo _otro_47:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_47:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Move this, R2>>codigo.tmp
echo MoveField R2.1, R1>>codigo.tmp
echo Library __printi(R1), Rdummy>>codigo.tmp
echo Move str65, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de ComplexTester.test>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
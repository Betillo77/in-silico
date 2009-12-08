@ECHO OFF
echo str0: "error1 in +">codigo.tmp
echo str1: "error1 in -">>codigo.tmp
echo str2: "error1 in *">>codigo.tmp
echo str3: "error1 in /">>codigo.tmp
echo str4: "error1 in %">>codigo.tmp
echo str5: "error1 in the unary operator -">>codigo.tmp
echo str6: "error2.1 in +">>codigo.tmp
echo str7: "error2.2 in +">>codigo.tmp
echo str8: "error2 in -">>codigo.tmp
echo str9: "error2 in *">>codigo.tmp
echo str10: "error2 in *">>codigo.tmp
echo str11: "error2 in /">>codigo.tmp
echo str12: "error2 in %">>codigo.tmp
echo str13: "error2 in the unary operator -">>codigo.tmp
echo str14: "error2.2 in -">>codigo.tmp
echo str15: "error3 in +">>codigo.tmp
echo str16: "error3 in -">>codigo.tmp
echo str17: "error3 in *">>codigo.tmp
echo str18: "error3 in /">>codigo.tmp
echo str19: "error3 in %">>codigo.tmp
echo str20: "error3 in the unary operator -">>codigo.tmp
echo str21: "error4 in +">>codigo.tmp
echo str22: "error4 in -">>codigo.tmp
echo str23: "error4 in *">>codigo.tmp
echo str24: "error4 in /">>codigo.tmp
echo str25: "error4 in %">>codigo.tmp
echo str26: "error4 in the unary operator -">>codigo.tmp
echo str27: "Testing long Expretions">>codigo.tmp
echo str28: "error1 in complex operators">>codigo.tmp
echo str29: "error2 in complex operators">>codigo.tmp
echo str30: "Hello World">>codigo.tmp
echo+>>codigo.tmp
echo _DV_ArrayBub: []>>codigo.tmp
echo _DV_TestClass: []>>codigo.tmp
echo+>>codigo.tmp
echo # ArrayBub.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 7, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 5, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Move R6, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo Move 3, R7>>codigo.tmp
echo Move R7, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Move 4, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move 5, R9>>codigo.tmp
echo Move R9, R8>>codigo.tmp
echo Move 0, R9>>codigo.tmp
echo Library __allocateObject(8), R10>>codigo.tmp
echo MoveField _DV_TestClass, R10.0>>codigo.tmp
echo Move R10, R9>>codigo.tmp
echo Move 6, R10>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo MoveField R10, R11.1>>codigo.tmp
echo Move 7, R10>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R10, R11[R12]>>codigo.tmp
echo Move 9, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move 13, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_0>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move 9, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 5, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Sub R11, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 1, R10>>codigo.tmp
echo JumpTrue _otro_2>>codigo.tmp
echo Move 9, R11>>codigo.tmp
echo Move 4, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Move 5, R12>>codigo.tmp
echo Compare R12, R11>>codigo.tmp
echo JumpTrue _otro_3>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Or R11, R10>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move str1, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move 9, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mul R11, R10>>codigo.tmp
echo Move 36, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_2>>codigo.tmp
echo Move str2, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Move 12, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Div R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_5>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_3>>codigo.tmp
echo Move str3, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move 21, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mod R11, R10>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_6>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_4>>codigo.tmp
echo Move str4, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_4:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Sub R10, R11>>codigo.tmp
echo Move R11, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_7>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_5>>codigo.tmp
echo Move str5, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_5:>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move 11, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_8>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_6>>codigo.tmp
echo Move str6, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_6:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move 11, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_9>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_7>>codigo.tmp
echo Move str7, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_7:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Sub R11, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_10>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Compare 1, R10>>codigo.tmp
echo JumpTrue _otro_11>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo Move 4, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Move 3, R12>>codigo.tmp
echo Compare R12, R11>>codigo.tmp
echo JumpTrue _otro_12>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Or R11, R10>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_8>>codigo.tmp
echo Move str8, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_8:>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mul R11, R10>>codigo.tmp
echo Move 28, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_13>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_9>>codigo.tmp
echo Move str9, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_9:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo Mul R11, R10>>codigo.tmp
echo Move 28, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_14>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_10>>codigo.tmp
echo Move str10, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_10:>>codigo.tmp
echo Move 22, R10>>codigo.tmp
echo Move R2, R11>>codigo.tmp
echo Div R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_15>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_11>>codigo.tmp
echo Move str11, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_11:>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mod R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_16>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_16>>codigo.tmp
echo _otro_16:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_16:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_12>>codigo.tmp
echo Move str12, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_12:>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Sub R10, R11>>codigo.tmp
echo Move R11, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move 7, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_17>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_17>>codigo.tmp
echo _otro_17:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_17:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_13>>codigo.tmp
echo Move str13, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_13:>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move R3, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 2, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_18>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_18>>codigo.tmp
echo _otro_18:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_18:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_14>>codigo.tmp
echo Move str14, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_14:>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move 11, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_19>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_19>>codigo.tmp
echo _otro_19:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_19:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_15>>codigo.tmp
echo Move str15, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_15:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move R1, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Sub R11, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_20>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_20>>codigo.tmp
echo _otro_20:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_20:>>codigo.tmp
echo Compare 1, R10>>codigo.tmp
echo JumpTrue _otro_21>>codigo.tmp
echo Move R1, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Move 4, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Move 3, R12>>codigo.tmp
echo Compare R12, R11>>codigo.tmp
echo JumpTrue _otro_22>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Jump _salida_otro_22>>codigo.tmp
echo _otro_22:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_22:>>codigo.tmp
echo Or R11, R10>>codigo.tmp
echo Jump _salida_otro_21>>codigo.tmp
echo _otro_21:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo _salida_otro_21:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_16>>codigo.tmp
echo Move str16, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_16:>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mul R11, R10>>codigo.tmp
echo Move 28, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_23>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_23>>codigo.tmp
echo _otro_23:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_23:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_17>>codigo.tmp
echo Move str17, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_17:>>codigo.tmp
echo Move 22, R10>>codigo.tmp
echo Move R1, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Div R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_24>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_24>>codigo.tmp
echo _otro_24:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_24:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_18>>codigo.tmp
echo Move str18, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_18:>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mod R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_25>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_25>>codigo.tmp
echo _otro_25:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_25:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_19>>codigo.tmp
echo Move str19, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_19:>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Sub R10, R11>>codigo.tmp
echo Move R11, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move 7, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_26>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_26>>codigo.tmp
echo _otro_26:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_26:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_20>>codigo.tmp
echo Move str20, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_20:>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo MoveField R11.1, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move 10, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_27>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_27>>codigo.tmp
echo _otro_27:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_27:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_21>>codigo.tmp
echo Move str21, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_21:>>codigo.tmp
echo Move 4, R10>>codigo.tmp
echo Move R9, R12>>codigo.tmp
echo MoveField R12.1, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 2, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo Sub R11, R12>>codigo.tmp
echo Move R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_28>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_28>>codigo.tmp
echo _otro_28:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_28:>>codigo.tmp
echo Compare 1, R10>>codigo.tmp
echo JumpTrue _otro_29>>codigo.tmp
echo Move R9, R12>>codigo.tmp
echo MoveField R12.1, R11>>codigo.tmp
echo Move 4, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Move 2, R12>>codigo.tmp
echo Compare R12, R11>>codigo.tmp
echo JumpTrue _otro_30>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo Jump _salida_otro_30>>codigo.tmp
echo _otro_30:>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo _salida_otro_30:>>codigo.tmp
echo Or R11, R10>>codigo.tmp
echo Jump _salida_otro_29>>codigo.tmp
echo _otro_29:>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo _salida_otro_29:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_22>>codigo.tmp
echo Move str22, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_22:>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo MoveField R11.1, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mul R11, R10>>codigo.tmp
echo Move 24, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_31>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_31>>codigo.tmp
echo _otro_31:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_31:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_23>>codigo.tmp
echo Move str23, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_23:>>codigo.tmp
echo Move 22, R10>>codigo.tmp
echo Move R9, R12>>codigo.tmp
echo MoveField R12.1, R11>>codigo.tmp
echo Div R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_32>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_32>>codigo.tmp
echo _otro_32:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_32:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_24>>codigo.tmp
echo Move str24, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_24:>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo MoveField R11.1, R10>>codigo.tmp
echo Move 4, R11>>codigo.tmp
echo Mod R11, R10>>codigo.tmp
echo Move 2, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_33>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_33>>codigo.tmp
echo _otro_33:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_33:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_25>>codigo.tmp
echo Move str25, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_25:>>codigo.tmp
echo Move R9, R11>>codigo.tmp
echo MoveField R11.1, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Sub R10, R11>>codigo.tmp
echo Move R11, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo Move 6, R12>>codigo.tmp
echo Sub R12, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_34>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_34>>codigo.tmp
echo _otro_34:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_34:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_26>>codigo.tmp
echo Move str26, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_26:>>codigo.tmp
echo Move str27, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo Move 6, R10>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R10, R11[R12]>>codigo.tmp
echo Move R4, R10>>codigo.tmp
echo Move R5, R11>>codigo.tmp
echo Move R6, R12>>codigo.tmp
echo Mul R12, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move R7, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 3, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_35>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_35>>codigo.tmp
echo _otro_35:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_35:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_27>>codigo.tmp
echo Move str28, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_27:>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move 0, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Move 5, R11>>codigo.tmp
echo Move 5, R12>>codigo.tmp
echo Mul R12, R11>>codigo.tmp
echo Move R5, R12>>codigo.tmp
echo Mul R12, R11>>codigo.tmp
echo Add R11, R10>>codigo.tmp
echo Move R1, R12>>codigo.tmp
echo Move 0, R13>>codigo.tmp
echo MoveArray R12[R13], R11>>codigo.tmp
echo Move R5, R12>>codigo.tmp
echo Div R12, R11>>codigo.tmp
echo Sub R11, R10>>codigo.tmp
echo Move 53, R11>>codigo.tmp
echo Compare R11, R10>>codigo.tmp
echo JumpTrue _otro_36>>codigo.tmp
echo Move 1, R10>>codigo.tmp
echo Jump _salida_otro_36>>codigo.tmp
echo _otro_36:>>codigo.tmp
echo Move 0, R10>>codigo.tmp
echo _salida_otro_36:>>codigo.tmp
echo Compare 0, R10>>codigo.tmp
echo JumpTrue _salida_if_28>>codigo.tmp
echo Move str29, R10>>codigo.tmp
echo Library __println(R10), Rdummy>>codigo.tmp
echo _salida_if_28:>>codigo.tmp
echo Move str30, R10>>codigo.tmp
echo Library __print(R10), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ArrayBub.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
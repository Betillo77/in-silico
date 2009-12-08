@ECHO OFF
echo str0: "first success">codigo.tmp
echo str1: "first fail">>codigo.tmp
echo str2: "second success">>codigo.tmp
echo str3: "second fail">>codigo.tmp
echo str4: "third fail">>codigo.tmp
echo str5: "third success">>codigo.tmp
echo str6: "fourth success">>codigo.tmp
echo str7: "fifth success">>codigo.tmp
echo str8: "sixeth success">>codigo.tmp
echo str9: "!= fail 1">>codigo.tmp
echo str10: "!= fail 2">>codigo.tmp
echo str11: "!= fail 3">>codigo.tmp
echo str12: "== fail 1">>codigo.tmp
echo str13: "== fail 2">>codigo.tmp
echo str14: "== fail 3">>codigo.tmp
echo str15: "== fail 4">>codigo.tmp
echo str16: "== fail 5">>codigo.tmp
echo str17: "!= fail 4">>codigo.tmp
echo str18: "!= fail 5">>codigo.tmp
echo str19: "!= fail 6">>codigo.tmp
echo str20: "== fail 6">>codigo.tmp
echo str21: "> fail 1">>codigo.tmp
echo str22: "> fail 2">>codigo.tmp
echo str23: "> fail 3">>codigo.tmp
echo str24: "< fail 1">>codigo.tmp
echo str25: "< fail 2">>codigo.tmp
echo str26: "< fail 3">>codigo.tmp
echo str27: ">= fail 1">>codigo.tmp
echo str28: ">= fail 2">>codigo.tmp
echo str29: ">= fail 3">>codigo.tmp
echo str30: "<= fail 1">>codigo.tmp
echo str31: "<= fail 2">>codigo.tmp
echo str32: "<= fail 3">>codigo.tmp
echo str33: "<= fail 4">>codigo.tmp
echo str34: "<= fail 5">>codigo.tmp
echo str35: ">= fail 4">>codigo.tmp
echo str36: ">= fail 5">>codigo.tmp
echo str37: "< fail 4">>codigo.tmp
echo str38: "< fail 5">>codigo.tmp
echo str39: "> fail 4">>codigo.tmp
echo str40: "> fail 5">>codigo.tmp
echo str41: "else if fail 1">>codigo.tmp
echo str42: "else if fail 2">>codigo.tmp
echo str43: "else if fail 3">>codigo.tmp
echo str44: "else if pass 1">>codigo.tmp
echo str45: "else if fail 4">>codigo.tmp
echo str46: "else if fail 5">>codigo.tmp
echo str47: "else if pass 2">>codigo.tmp
echo str48: "else if fail 6">>codigo.tmp
echo str49: "else if fail 7">>codigo.tmp
echo str50: "else if fail 8">>codigo.tmp
echo str51: "else if fail 9">>codigo.tmp
echo str52: "else if pass 3">>codigo.tmp
echo+>>codigo.tmp
echo _DV_IfTest: []>>codigo.tmp
echo+>>codigo.tmp
echo # IfTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 3, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move str1, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_2>>codigo.tmp
echo Move str2, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_2>>codigo.tmp
echo _else_2:>>codigo.tmp
echo Move str3, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_2:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_3>>codigo.tmp
echo Move str4, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_3>>codigo.tmp
echo _else_3:>>codigo.tmp
echo Move str5, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_3:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_4>>codigo.tmp
echo Move str6, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_4:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_0>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Move R4, R1>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_5>>codigo.tmp
echo Move str7, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_5:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_1>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_6>>codigo.tmp
echo Move str8, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_6:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_7>>codigo.tmp
echo Move str9, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_7:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_8>>codigo.tmp
echo Move str10, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_8:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 3, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_9>>codigo.tmp
echo Move str11, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_9:>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_5>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_10>>codigo.tmp
echo Move str12, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_10:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_6>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_11>>codigo.tmp
echo Move str13, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_11:>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_7>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_12>>codigo.tmp
echo Move str14, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_12:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_8>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_13>>codigo.tmp
echo Move str15, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_13:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_9>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_14>>codigo.tmp
echo Move str16, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_14:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_10>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_15>>codigo.tmp
echo Move str17, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_15:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_11>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_16>>codigo.tmp
echo Move str18, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_16:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpTrue _otro_12>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_17>>codigo.tmp
echo Move str19, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_17:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move R3, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_13>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_18>>codigo.tmp
echo Move str20, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_18:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpLE _otro_14>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_19>>codigo.tmp
echo Move str21, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_19:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpLE _otro_15>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_20>>codigo.tmp
echo Move str22, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_20:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 3, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpLE _otro_16>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_16>>codigo.tmp
echo _otro_16:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_16:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_21>>codigo.tmp
echo Move str23, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_21:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_17>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_17>>codigo.tmp
echo _otro_17:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_17:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_22>>codigo.tmp
echo Move str24, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_22:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_18>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_18>>codigo.tmp
echo _otro_18:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_18:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_23>>codigo.tmp
echo Move str25, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_23:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 3, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_19>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_19>>codigo.tmp
echo _otro_19:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_19:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_24>>codigo.tmp
echo Move str26, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_24:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpL _otro_20>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_20>>codigo.tmp
echo _otro_20:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_20:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_25>>codigo.tmp
echo Move str27, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_25:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpL _otro_21>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_21>>codigo.tmp
echo _otro_21:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_21:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_26>>codigo.tmp
echo Move str28, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_26:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 5, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpL _otro_22>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_22>>codigo.tmp
echo _otro_22:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_22:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_27>>codigo.tmp
echo Move str29, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_27:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 5, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_23>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_23>>codigo.tmp
echo _otro_23:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_23:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_28>>codigo.tmp
echo Move str30, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_28:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 5, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_24>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_24>>codigo.tmp
echo _otro_24:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_24:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_29>>codigo.tmp
echo Move str31, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_29:>>codigo.tmp
echo Move 8, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 3, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_25>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_25>>codigo.tmp
echo _otro_25:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_25:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_30>>codigo.tmp
echo Move str32, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_30:>>codigo.tmp
echo Move 3, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 5, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_26>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_26>>codigo.tmp
echo _otro_26:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_26:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_31>>codigo.tmp
echo Move str33, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_31:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpG _otro_27>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_27>>codigo.tmp
echo _otro_27:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_27:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_32>>codigo.tmp
echo Move str34, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_32:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpL _otro_28>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_28>>codigo.tmp
echo _otro_28:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_28:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_33>>codigo.tmp
echo Move str35, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_33:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpL _otro_29>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_29>>codigo.tmp
echo _otro_29:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_29:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_34>>codigo.tmp
echo Move str36, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_34:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_30>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_30>>codigo.tmp
echo _otro_30:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_30:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_35>>codigo.tmp
echo Move str37, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_35:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_31>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_31>>codigo.tmp
echo _otro_31:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_31:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_36>>codigo.tmp
echo Move str38, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_36:>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpLE _otro_32>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_32>>codigo.tmp
echo _otro_32:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_32:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_37>>codigo.tmp
echo Move str39, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_37:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Move 2, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpGE _otro_33>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_33>>codigo.tmp
echo _otro_33:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_33:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _salida_if_38>>codigo.tmp
echo Move str40, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_38:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_34>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_34>>codigo.tmp
echo _otro_34:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_34:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_39>>codigo.tmp
echo Move str41, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_39>>codigo.tmp
echo _else_39:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 5, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_35>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_35>>codigo.tmp
echo _otro_35:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_35:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_40>>codigo.tmp
echo Move str42, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_40>>codigo.tmp
echo _else_40:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_36>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_36>>codigo.tmp
echo _otro_36:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_36:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_41>>codigo.tmp
echo Move str43, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_41>>codigo.tmp
echo _else_41:>>codigo.tmp
echo Move str44, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_41:>>codigo.tmp
echo _salida_if_40:>>codigo.tmp
echo _salida_if_39:>>codigo.tmp
echo Move 6, R4>>codigo.tmp
echo Move R4, R2>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_37>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_37>>codigo.tmp
echo _otro_37:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_37:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_42>>codigo.tmp
echo Move str45, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_42>>codigo.tmp
echo _else_42:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 5, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_38>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_38>>codigo.tmp
echo _otro_38:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_38:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_43>>codigo.tmp
echo Move str46, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_43>>codigo.tmp
echo _else_43:>>codigo.tmp
echo Move R2, R4>>codigo.tmp
echo Move 6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_39>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_39>>codigo.tmp
echo _otro_39:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_39:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_44>>codigo.tmp
echo Move str47, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_44>>codigo.tmp
echo _else_44:>>codigo.tmp
echo Move str48, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_44:>>codigo.tmp
echo _salida_if_43:>>codigo.tmp
echo _salida_if_42:>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 4, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_40>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_40>>codigo.tmp
echo _otro_40:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_40:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_45>>codigo.tmp
echo Move str49, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_45>>codigo.tmp
echo _else_45:>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo Move 2, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_41>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_41>>codigo.tmp
echo _otro_41:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_41:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_46>>codigo.tmp
echo Move str50, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_46>>codigo.tmp
echo _else_46:>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Move 1, R5>>codigo.tmp
echo Add R5, R4>>codigo.tmp
echo Move 3, R5>>codigo.tmp
echo Move 1, R6>>codigo.tmp
echo Add R6, R5>>codigo.tmp
echo Compare R5, R4>>codigo.tmp
echo JumpFalse _otro_42>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Jump _salida_otro_42>>codigo.tmp
echo _otro_42:>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo _salida_otro_42:>>codigo.tmp
echo Compare 0, R4>>codigo.tmp
echo JumpTrue _else_47>>codigo.tmp
echo Move str51, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo Jump _salida_if_47>>codigo.tmp
echo _else_47:>>codigo.tmp
echo Move str52, R4>>codigo.tmp
echo Library __println(R4), Rdummy>>codigo.tmp
echo _salida_if_47:>>codigo.tmp
echo _salida_if_46:>>codigo.tmp
echo _salida_if_45:>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de IfTest.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
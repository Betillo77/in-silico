@ECHO OFF
echo str0: "Hello">codigo.tmp
echo str1: " ">>codigo.tmp
echo str2: "World">>codigo.tmp
echo str3: "d">>codigo.tmp
echo str4: "e">>codigo.tmp
echo str5: "f">>codigo.tmp
echo str6: "g">>codigo.tmp
echo str7: "Hello ">>codigo.tmp
echo str8: "World">>codigo.tmp
echo str9: "Hello ">>codigo.tmp
echo str10: "World">>codigo.tmp
echo str11: "Hello ">>codigo.tmp
echo str12: " World">>codigo.tmp
echo str13: "Hello">>codigo.tmp
echo str14: "World">>codigo.tmp
echo str15: "World">>codigo.tmp
echo str16: "a">>codigo.tmp
echo str17: "b">>codigo.tmp
echo str18: "c">>codigo.tmp
echo str19: "Hello ">>codigo.tmp
echo str20: "world">>codigo.tmp
echo+>>codigo.tmp
echo _DV_StringCatTest: []>>codigo.tmp
echo+>>codigo.tmp
echo # StringCatTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move str1, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move str2, R4>>codigo.tmp
echo Move R4, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo Move str3, R5>>codigo.tmp
echo Move R5, R4>>codigo.tmp
echo Move 0, R5>>codigo.tmp
echo Move str4, R6>>codigo.tmp
echo Move R6, R5>>codigo.tmp
echo Move 0, R6>>codigo.tmp
echo Move str5, R7>>codigo.tmp
echo Move R7, R6>>codigo.tmp
echo Move 0, R7>>codigo.tmp
echo Move str6, R8>>codigo.tmp
echo Move R8, R7>>codigo.tmp
echo Move 0, R8>>codigo.tmp
echo Move 2, R9>>codigo.tmp
echo Mul 4, R9>>codigo.tmp
echo Library __allocateArray(R9), R9>>codigo.tmp
echo Move R9, R8>>codigo.tmp
echo Move str7, R9>>codigo.tmp
echo Move R8, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo MoveArray R9, R10[R11]>>codigo.tmp
echo Move str8, R9>>codigo.tmp
echo Move R8, R10>>codigo.tmp
echo Move 1, R11>>codigo.tmp
echo MoveArray R9, R10[R11]>>codigo.tmp
echo Move str9, R9>>codigo.tmp
echo Move str10, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move str11, R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move R1, R9>>codigo.tmp
echo Move str12, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move str13, R9>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move str14, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo StaticCall _StringCatTest_hello(), R9>>codigo.tmp
echo Move str15, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo StaticCall _StringCatTest_hello(), R9>>codigo.tmp
echo Move R8, R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move R8, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo MoveArray R10[R11], R9>>codigo.tmp
echo Move R8, R11>>codigo.tmp
echo Move 1, R12>>codigo.tmp
echo MoveArray R11[R12], R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move R1, R9>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R3, R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move str16, R9>>codigo.tmp
echo Move R9, R1>>codigo.tmp
echo Move str17, R9>>codigo.tmp
echo Move R9, R2>>codigo.tmp
echo Move str18, R9>>codigo.tmp
echo Move R9, R3>>codigo.tmp
echo Move R8, R10>>codigo.tmp
echo Move 0, R11>>codigo.tmp
echo MoveArray R10[R11], R9>>codigo.tmp
echo Move R1, R11>>codigo.tmp
echo Move R2, R12>>codigo.tmp
echo Library __stringCat(R11, R12), R11>>codigo.tmp
echo Move R2, R12>>codigo.tmp
echo Move R3, R13>>codigo.tmp
echo Library __stringCat(R12, R13), R12>>codigo.tmp
echo Move R3, R13>>codigo.tmp
echo Move R4, R14>>codigo.tmp
echo Library __stringCat(R13, R14), R13>>codigo.tmp
echo StaticCall _StringCatTest_world(p1 = R11, p2 = R12, p3 = R13), R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __println(R9), Rdummy>>codigo.tmp
echo Move R1, R9>>codigo.tmp
echo Move R2, R10>>codigo.tmp
echo Move R3, R11>>codigo.tmp
echo Move R4, R12>>codigo.tmp
echo Library __stringCat(R11, R12), R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Move R5, R10>>codigo.tmp
echo Move R6, R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Move R7, R11>>codigo.tmp
echo Library __stringCat(R10, R11), R10>>codigo.tmp
echo Library __stringCat(R9, R10), R9>>codigo.tmp
echo Library __print(R9), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de StringCatTest.main>>codigo.tmp
echo+>>codigo.tmp
echo # StringCatTest.hello>>codigo.tmp
echo _StringCatTest_hello:>>codigo.tmp
echo Move str19, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de StringCatTest.hello>>codigo.tmp
echo+>>codigo.tmp
echo # StringCatTest.world>>codigo.tmp
echo _StringCatTest_world:>>codigo.tmp
echo Move str20, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de StringCatTest.world>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
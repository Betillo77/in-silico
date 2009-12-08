@ECHO OFF
echo str0: "Error 1">codigo.tmp
echo str1: "Error 2">>codigo.tmp
echo str2: "End of StaticFunctionCallTest">>codigo.tmp
echo+>>codigo.tmp
echo _DV_StaticFunctionCallTest: []>>codigo.tmp
echo _DV_Foo: []>>codigo.tmp
echo+>>codigo.tmp
echo # StaticFunctionCallTest.add1>>codigo.tmp
echo _StaticFunctionCallTest_add1:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de StaticFunctionCallTest.add1>>codigo.tmp
echo+>>codigo.tmp
echo # StaticFunctionCallTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 5, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo StaticCall _StaticFunctionCallTest_add1(p1 = R4), R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo StaticCall _StaticFunctionCallTest_add1(p1 = R4), R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 7, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_0>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move str0, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo StaticCall _Foo_add6(p1 = R4), R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 12, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_if_1>>codigo.tmp
echo Move str1, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo _salida_if_1:>>codigo.tmp
echo Move str2, R3>>codigo.tmp
echo Library __print(R3), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de StaticFunctionCallTest.main>>codigo.tmp
echo+>>codigo.tmp
echo # Foo.add6>>codigo.tmp
echo _Foo_add6:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move 6, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de Foo.add6>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
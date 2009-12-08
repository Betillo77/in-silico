@ECHO OFF
echo str0: "error1">codigo.tmp
echo str1: "error2">>codigo.tmp
echo str2: "error3">>codigo.tmp
echo str3: "error4">>codigo.tmp
echo str4: "error5">>codigo.tmp
echo str5: "error6">>codigo.tmp
echo str6: "error7">>codigo.tmp
echo str7: "error8">>codigo.tmp
echo str8: "error9">>codigo.tmp
echo str9: "error11">>codigo.tmp
echo str10: "End of Test">>codigo.tmp
echo str11: "Hello World">>codigo.tmp
echo+>>codigo.tmp
echo _DV_ReturnTest: []>>codigo.tmp
echo _DV_HelpClass: []>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo StaticCall _ReturnTest_returnVarible(), R1>>codigo.tmp
echo Move 13, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Sub R2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
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
echo StaticCall _ReturnTest_returnImmidate(), R1>>codigo.tmp
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
echo StaticCall _ReturnTest_returnArrayElem(), R1>>codigo.tmp
echo Move 9, R2>>codigo.tmp
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
echo StaticCall _ReturnTest_return8InField(), R1>>codigo.tmp
echo Move 8, R2>>codigo.tmp
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
echo StaticCall _ReturnTest_returnArrayInField(), R1>>codigo.tmp
echo ArrayLength R1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_5>>codigo.tmp
echo StaticCall _ReturnTest_returnArrayInField(), R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move 5, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpTrue _otro_6>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_6>>codigo.tmp
echo _otro_6:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_6:>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_5>>codigo.tmp
echo _otro_5:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_5:>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_7>>codigo.tmp
echo StaticCall _ReturnTest_returnArrayInField(), R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move 6, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpTrue _otro_8>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_8>>codigo.tmp
echo _otro_8:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_8:>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_7>>codigo.tmp
echo _otro_7:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_7:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_4>>codigo.tmp
echo Move str4, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_4:>>codigo.tmp
echo StaticCall _ReturnTest_returnObject(), R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpFalse _otro_9>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_9>>codigo.tmp
echo _otro_9:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_9:>>codigo.tmp
echo Compare 1, R1>>codigo.tmp
echo JumpTrue _otro_10>>codigo.tmp
echo StaticCall _ReturnTest_returnObject(), R3>>codigo.tmp
echo MoveField R3.3, R2>>codigo.tmp
echo Move 97, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpTrue _otro_11>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_11>>codigo.tmp
echo _otro_11:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_11:>>codigo.tmp
echo Or R2, R1>>codigo.tmp
echo Jump _salida_otro_10>>codigo.tmp
echo _otro_10:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_10:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_5>>codigo.tmp
echo Move str5, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_5:>>codigo.tmp
echo StaticCall _ReturnTest_returnClassNull(), R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_12>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_12>>codigo.tmp
echo _otro_12:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_12:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_6>>codigo.tmp
echo Move str6, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_6:>>codigo.tmp
echo StaticCall _ReturnTest_returnNotInitializedNull(), R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_13>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_13>>codigo.tmp
echo _otro_13:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_13:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_7>>codigo.tmp
echo Move str7, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_7:>>codigo.tmp
echo StaticCall _ReturnTest_returnNullAsString(), R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_14>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_14>>codigo.tmp
echo _otro_14:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_14:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_8>>codigo.tmp
echo Move str8, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_8:>>codigo.tmp
echo StaticCall _ReturnTest_returnString(), R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo StaticCall _ReturnTest_returnNullAsArray(), R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpTrue _otro_15>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_15>>codigo.tmp
echo _otro_15:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_15:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_9>>codigo.tmp
echo Move str9, R1>>codigo.tmp
echo Library __println(R1), Rdummy>>codigo.tmp
echo _salida_if_9:>>codigo.tmp
echo Move str10, R1>>codigo.tmp
echo Library __print(R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ReturnTest.main>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnVarible>>codigo.tmp
echo _ReturnTest_returnVarible:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 13, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Sub R2, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnVarible>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnImmidate>>codigo.tmp
echo _ReturnTest_returnImmidate:>>codigo.tmp
echo Move 3, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de ReturnTest.returnImmidate>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnArrayElem>>codigo.tmp
echo _ReturnTest_returnArrayElem:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 3, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 9, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnArrayElem>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.return8InField>>codigo.tmp
echo _ReturnTest_return8InField:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_HelpClass, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 8, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R2, R3.3>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R3.3, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.return8InField>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnArrayInField>>codigo.tmp
echo _ReturnTest_returnArrayInField:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_HelpClass, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R2, R3.2>>codigo.tmp
echo Move 5, R2>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Move 0, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move 6, R2>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveField R4.2, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R3.2, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnArrayInField>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnObject>>codigo.tmp
echo _ReturnTest_returnObject:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_HelpClass, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 97, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R2, R3.3>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnObject>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnClassNull>>codigo.tmp
echo _ReturnTest_returnClassNull:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_HelpClass, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnClassNull>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnNotInitializedNull>>codigo.tmp
echo _ReturnTest_returnNotInitializedNull:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Library __allocateObject(20), R2>>codigo.tmp
echo MoveField _DV_HelpClass, R2.0>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo MoveField R3.4, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnNotInitializedNull>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnNullAsString>>codigo.tmp
echo _ReturnTest_returnNullAsString:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnNullAsString>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnString>>codigo.tmp
echo _ReturnTest_returnString:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move str11, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnString>>codigo.tmp
echo+>>codigo.tmp
echo # ReturnTest.returnNullAsArray>>codigo.tmp
echo _ReturnTest_returnNullAsArray:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 5, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Move 4, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Return R2>>codigo.tmp
echo # Fin de ReturnTest.returnNullAsArray>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
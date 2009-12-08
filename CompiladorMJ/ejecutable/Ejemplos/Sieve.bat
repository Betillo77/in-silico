@ECHO OFF
echo str0: "Primes less than ">codigo.tmp
echo str1: ": ">>codigo.tmp
echo str2: " ">>codigo.tmp
echo str3: "Provide the number to sieve.">>codigo.tmp
echo str4: "">>codigo.tmp
echo str5: "">>codigo.tmp
echo+>>codigo.tmp
echo _DV_Sieve: []>>codigo.tmp
echo+>>codigo.tmp
echo # Sieve.initArray>>codigo.tmp
echo _Sieve_initArray:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Mul 4, R2>>codigo.tmp
echo Library __allocateArray(R2), R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo _while_0:>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo ArrayLength R4, R4>>codigo.tmp
echo Compare R4, R3>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R3>>codigo.tmp
echo JumpTrue _salida_while_0>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo Move R2, R5>>codigo.tmp
echo MoveArray R3, R4[R5]>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Move 1, R4>>codigo.tmp
echo Add R4, R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Jump _while_0>>codigo.tmp
echo _salida_while_0:>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo Return R3>>codigo.tmp
echo # Fin de Sieve.initArray>>codigo.tmp
echo+>>codigo.tmp
echo # Sieve.sieveAll>>codigo.tmp
echo _Sieve_sieveAll:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_1:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo ArrayLength R3, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_1>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_1>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move R1, R3>>codigo.tmp
echo StaticCall _Sieve_sieve(p1 = R2, p2 = R3), Rdummy>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_1>>codigo.tmp
echo _salida_while_1:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Sieve.sieveAll>>codigo.tmp
echo+>>codigo.tmp
echo # Sieve.sieve>>codigo.tmp
echo _Sieve_sieve:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo Mul R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo _while_2:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo ArrayLength R3, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_2>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_2>>codigo.tmp
echo _otro_2:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_2:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_2>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveArray R2, R3[R4]>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p2, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_2>>codigo.tmp
echo _salida_while_2:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Sieve.sieve>>codigo.tmp
echo+>>codigo.tmp
echo # Sieve.printPrimes>>codigo.tmp
echo _Sieve_printPrimes:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Move str0, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo ArrayLength R2, R2>>codigo.tmp
echo Library __printi(R2), Rdummy>>codigo.tmp
echo Move str1, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo _while_3:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo ArrayLength R3, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpGE _otro_3>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_3>>codigo.tmp
echo _otro_3:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_3:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_while_3>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Move 0, R3>>codigo.tmp
echo Compare R3, R2>>codigo.tmp
echo JumpTrue _otro_4>>codigo.tmp
echo Move 1, R2>>codigo.tmp
echo Jump _salida_otro_4>>codigo.tmp
echo _otro_4:>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo _salida_otro_4:>>codigo.tmp
echo Compare 0, R2>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo MoveArray R3[R4], R2>>codigo.tmp
echo Library __printi(R2), Rdummy>>codigo.tmp
echo Move str2, R2>>codigo.tmp
echo Library __print(R2), Rdummy>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move R1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Add R3, R2>>codigo.tmp
echo Move R2, R1>>codigo.tmp
echo Jump _while_3>>codigo.tmp
echo _salida_while_3:>>codigo.tmp
echo Return 999>>codigo.tmp
echo # Fin de Sieve.printPrimes>>codigo.tmp
echo+>>codigo.tmp
echo # Sieve.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move str3, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Library __readi(), R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo StaticCall _Sieve_initArray(p1 = R4), R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move str4, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo StaticCall _Sieve_sieveAll(p1 = R3), Rdummy>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo StaticCall _Sieve_printPrimes(p1 = R3), Rdummy>>codigo.tmp
echo Move str5, R3>>codigo.tmp
echo Library __println(R3), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Sieve.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
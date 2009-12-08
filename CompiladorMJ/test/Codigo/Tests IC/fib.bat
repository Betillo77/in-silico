@ECHO OFF
echo str0: "The 11th fibinacci number is : ">codigo.tmp
echo+>>codigo.tmp
echo _DV_Fibonacci: []>>codigo.tmp
echo+>>codigo.tmp
echo # Fibonacci.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Move 0, R2>>codigo.tmp
echo Move 11, R3>>codigo.tmp
echo Move R3, R1>>codigo.tmp
echo Move R1, R4>>codigo.tmp
echo StaticCall _Fibonacci_fib(p1 = R4), R3>>codigo.tmp
echo Move R3, R2>>codigo.tmp
echo Move str0, R3>>codigo.tmp
echo Library __print(R3), Rdummy>>codigo.tmp
echo Move R2, R3>>codigo.tmp
echo Library __printi(R3), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de Fibonacci.main>>codigo.tmp
echo+>>codigo.tmp
echo # Fibonacci.fib>>codigo.tmp
echo _Fibonacci_fib:>>codigo.tmp
echo Move p1, R1>>codigo.tmp
echo Move 2, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpGE _otro_0>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _salida_if_0>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo _salida_if_0:>>codigo.tmp
echo Move p1, R2>>codigo.tmp
echo Move 1, R3>>codigo.tmp
echo Sub R3, R2>>codigo.tmp
echo StaticCall _Fibonacci_fib(p1 = R2), R1>>codigo.tmp
echo Move p1, R3>>codigo.tmp
echo Move 2, R4>>codigo.tmp
echo Sub R4, R3>>codigo.tmp
echo StaticCall _Fibonacci_fib(p1 = R3), R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Return R1>>codigo.tmp
echo # Fin de Fibonacci.fib>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
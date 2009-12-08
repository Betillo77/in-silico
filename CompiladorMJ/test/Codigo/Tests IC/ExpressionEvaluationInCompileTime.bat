@ECHO OFF
echo _DV_ExpressionEvaluationInCompileTime: []>codigo.tmp
echo+>>codigo.tmp
echo # ExpressionEvaluationInCompileTime.main>>codigo.tmp
echo _ic_main:>>codigo.tmp
echo Move 4, R1>>codigo.tmp
echo Move 6, R2>>codigo.tmp
echo Add R2, R1>>codigo.tmp
echo Move 10, R2>>codigo.tmp
echo Compare R2, R1>>codigo.tmp
echo JumpFalse _otro_0>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo Jump _salida_otro_0>>codigo.tmp
echo _otro_0:>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo _salida_otro_0:>>codigo.tmp
echo Compare 0, R1>>codigo.tmp
echo JumpTrue _otro_1>>codigo.tmp
echo Move 0, R1>>codigo.tmp
echo Jump _salida_otro_1>>codigo.tmp
echo _otro_1:>>codigo.tmp
echo Move 1, R1>>codigo.tmp
echo _salida_otro_1:>>codigo.tmp
echo Library __printb(R1), Rdummy>>codigo.tmp
echo Library __exit(0), Rdummy>>codigo.tmp
echo # Fin de ExpressionEvaluationInCompileTime.main>>codigo.tmp
echo+>>codigo.tmp
java -jar microLIR.jar codigo.tmp
echo+
pause
del codigo.tmp
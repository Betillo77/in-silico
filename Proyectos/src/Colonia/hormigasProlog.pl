  :- dynamic mejor/2.

  permutation([], []).
  permutation([ X | L], P) :- permutation(L, L1), insert(X, L1, P).
  insert(X, List, BiggerList) :- del(X, BiggerList, List).
  del(X, [X | Tail], Tail).
  del(X, [Y | Tail], [Y | Tail1]) :- del( X, Tail, Tail1).
  mejor(100, []).

  lista(0, 1, 3).
  lista(0, 2, 4).
  lista(0, 3, 14).
  lista(0, 4, 20).
  lista(0, 5, 12).
  lista(0, 6, 3).
  lista(0, 7, 4).
  lista(0, 8, 9).
  lista(0, 9, 10).
  lista(0, 10, 5).
  lista(0, 11, 4).
  lista(1, 0, 3).
  lista(1, 2, 6).
  lista(1, 3, 16).
  lista(1, 4, 7).
  lista(1, 5, 6).
  lista(1, 6, 6).
  lista(1, 7, 9).
  lista(1, 8, 8).
  lista(1, 9, 1).
  lista(1, 10, 4).
  lista(1, 11, 6).
  lista(2, 0, 5).
  lista(2, 1, 2).
  lista(2, 3, 17).
  lista(2, 4, 15).
  lista(2, 5, 10).
  lista(2, 6, 4).
  lista(2, 7, 15).
  lista(2, 8, 10).
  lista(2, 9, 2).
  lista(2, 10, 6).
  lista(2, 11, 12).
  lista(3, 0, 18).
  lista(3, 1, 10).
  lista(3, 2, 1).
  lista(3, 4, 10).
  lista(3, 5, 11).
  lista(3, 6, 12).
  lista(3, 7, 11).
  lista(3, 8, 9).
  lista(3, 9, 10).
  lista(3, 10, 5).
  lista(3, 11, 8).
  lista(4, 0, 11).
  lista(4, 1, 8).
  lista(4, 2, 9).
  lista(4, 3, 4).
  lista(4, 5, 2).
  lista(4, 6, 5).
  lista(4, 7, 11).
  lista(4, 8, 9).
  lista(4, 9, 7).
  lista(4, 10, 6).
  lista(4, 11, 7).
  lista(5, 0, 2).
  lista(5, 1, 6).
  lista(5, 2, 7).
  lista(5, 3, 1).
  lista(5, 4, 2).
  lista(5, 6, 4).
  lista(5, 7, 7).
  lista(5, 8, 8).
  lista(5, 9, 9).
  lista(5, 10, 1).
  lista(5, 11, 5).
  lista(6, 0, 7).
  lista(6, 1, 5).
  lista(6, 2, 6).
  lista(6, 3, 4).
  lista(6, 4, 9).
  lista(6, 5, 11).
  lista(6, 7, 12).
  lista(6, 8, 10).
  lista(6, 9, 10).
  lista(6, 10, 7).
  lista(6, 11, 6).
  lista(7, 0, 17).
  lista(7, 1, 15).
  lista(7, 2, 16).
  lista(7, 3, 18).
  lista(7, 4, 12).
  lista(7, 5, 14).
  lista(7, 6, 12).
  lista(7, 8, 11).
  lista(7, 9, 14).
  lista(7, 10, 12).
  lista(7, 11, 13).
  lista(8, 0, 17).
  lista(8, 1, 15).
  lista(8, 2, 16).
  lista(8, 3, 18).
  lista(8, 4, 12).
  lista(8, 5, 14).
  lista(8, 6, 12).
  lista(8, 7, 11).
  lista(8, 9, 14).
  lista(8, 10, 12).
  lista(8, 11, 14).
  lista(9, 0, 16).
  lista(9, 1, 13).
  lista(9, 2, 15).
  lista(9, 3, 10).
  lista(9, 4, 9).
  lista(9, 5, 11).
  lista(9, 6, 12).
  lista(9, 7, 14).
  lista(9, 8, 15).
  lista(9, 10, 6).
  lista(9, 11, 12).
  lista(10, 0, 5).
  lista(10, 1, 2).
  lista(10, 2, 17).
  lista(10, 3, 15).
  lista(10, 4, 10).
  lista(10, 5, 4).
  lista(10, 6, 15).
  lista(10, 7, 10).
  lista(10, 8, 2).
  lista(10, 9, 6).
  lista(10, 11, 10).
  lista(11, 0, 5).
  lista(11, 1, 2).
  lista(11, 2, 17).
  lista(11, 3, 15).
  lista(11, 4, 10).
  lista(11, 5, 4).
  lista(11, 6, 15).
  lista(11, 7, 10).
  lista(11, 8, 2).
  lista(11, 9, 6).
  lista(11, 10, 5).



  solucion:-
  permutation([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11], X1), X2 = [0|X1], append(X2, [0], X), peso(X, 0, Y), mejor(Z, W), Y =< Z, retract(mejor(Z, W)), assert(mejor(Y, X)), fail.

peso([_], A, A) :- !.
peso([A, B | Cola], Acum, Total) :-
lista(A, B, Actual), AcumN is Acum + Actual, peso([B | Cola], AcumN, Total).













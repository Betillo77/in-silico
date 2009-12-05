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
  lista(0, 6, 11).
  lista(0, 7, 1).
  lista(0, 8, 3).
  lista(0, 8, 3).
  lista(1, 0, 3).
  lista(1, 2, 6).
  lista(1, 3, 16).
  lista(1, 4, 7).
  lista(1, 5, 6).
  lista(2, 0, 5).
  lista(2, 1, 2).
  lista(2, 3, 17).
  lista(2, 4, 5).
  lista(2, 5, 10).
  lista(3, 0, 18).
  lista(3, 1, 10).
  lista(3, 2, 1).
  lista(3, 4, 10).
  lista(3, 5, 11).
  lista(4, 0, 11).
  lista(4, 1, 8).
  lista(4, 2, 9).
  lista(4, 3, 4).
  lista(4, 5, 2).
  lista(5, 0, 2).
  lista(5, 1, 6).
  lista(5, 2, 7).
  lista(5, 3, 1).
  lista(5, 4, 2).


  solucion:-
  permutation([1, 2, 3, 4, 5, 6, 7, 8 ,9], X1), X2 = [0|X1], append(X2, [0], X), print(X), nl, peso(X, 0, Y), mejor(Z, W), Y =< Z, retract(mejor(Z, W)), assert(mejor(Y, X)), fail.

peso([_], A, A) :- !.
peso([A, B | Cola], Acum, Total) :-
lista(A, B, Actual), AcumN is Acum + Actual, peso([B | Cola], AcumN, Total).













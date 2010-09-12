/* 
 * File:   algorithms.h
 * Author: seb
 *
 * Created on 25 de junio de 2010, 10:56 AM
 */

#ifndef COMBINATORIAL_H
#define COMBINATORIAL_H

#include <cstring>

/**
 * Receives a vector of n integers and does all possible permutations
 * of r elements. Once a new permutation if found, it calls the received
 * function func with the computed vector and its respective size (r)
 */
void permutations(int *vector, int n, int r, void (*func) (int*,int));

/**
 * Receives a vector of n integers and does all possible combinations
 * of r elements. Once a new combination if found, it calls the received
 * function func with the computed vector and its respective size (r)
 */
void combinations(int *vector, int n, int r, void (*func) (int*,int));

#endif
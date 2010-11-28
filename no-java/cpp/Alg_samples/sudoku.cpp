
#include <string>
#include <iostream>

using namespace std;

/* Representation of the problem:
 * This is a matrix representation, where each square is an int value containing
 * the number in that position (bits 3 downto 0), where 0 represents the square is
 * empty. It also contains the number of possible numbers to put in the square
 * (bits 7 downto 4) and which numbers can be put (bits 16 downto 8) where the bit
 * 8 tells if we can put 1, 9 bit if 2 is available, ..., bit 16 if we can put 9.
 * matriz[i][j] = (Possibles 16-8).(How many available 7-4).(Number 3-0).
 */
int matriz[9][9];

/* Dada la casilla, quita el numero num de la lista de posibles
 * valores */
void quitarPosible(int *val, int num) {
	int mascara = (128 << num) ^ 0xFFFFFFFF; //NOT bit a bit
	int tmp = *val;
	*val &= mascara;
	if (*val != tmp)
		*val -= 0x10;
}

/* Puts val number in matriz[fila][col] and tells the squares in the same
 * row, column and subsquare that the val value has already been used */
void llenarCuadro(int fila, int col, int val) {
	matriz[fila][col] |= val;
	for (int i=0; i<9; i++) {
		if ((i/3)!=(col/3)) quitarPosible(&matriz[fila][i], val);
		if ((i/3)!=(fila/3)) quitarPosible(&matriz[i][col], val);
	}
	int fb = fila/3;
	int cb = col/3;
	for (int i=0; i<3; i++)
		for (int j=0; j<3; j++)
			quitarPosible(&matriz[i+fb*3][j+cb*3], val);	
}

/* Fills matriz with all number being possible and value 0 */
void initMatriz() {
	for (int i=0; i<9; i++)
		for (int j=0; j<9; j++)
			matriz[i][j]=0x3FF90;
}

/* Returns the square that is more restricted and hasn't been used,
 * the result is returned in the parameters */
void masRestringida(int* fila, int* col) {
	*fila=*col=0;
	int min = 0xFFFF;
	for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
			int a = matriz[i][j] & 0xF0;
			if ((min > a) && ((matriz[i][j]&0x0F) == 0)) {
				*fila = i;
				*col = j;
				min = a;
			}
		}
	}
}

// Copia la matriz b en a
void copiar(int a[][9], int b[][9]) {
	for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
			b[i][j] = a[i][j];
		}
	}
}  

// Prints the matriz in the screen
void imprimir() {
	for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
			cout << hex << (matriz[i][j] & 0x0F) << " ";
		}
		cout << "\n";
	}
}

//Has the matrix been already finished (Is full)?
bool finish() {
	for (int i=0; i<9; i++) 
		for (int j=0; j<9; j++) 
			if ((matriz[i][j] & 0x0f) == 0)
				return false;
	return true;
}
	
/* It's the main method, once initiated the matrix with the correct values
 * this functions tries to solve the sudoku and returns if success */
bool backtrack() {
	int fila,col, mascara=0x100, cuadro;
	if (finish()) return true;
	int temp[9][9];
	copiar(matriz, temp);
	masRestringida(&fila,&col);
	cuadro = matriz[fila][col];
	for (int i=1; i<=9; i++) {
		if ((cuadro & mascara) != 0) {
			llenarCuadro(fila,col,i);
			if (backtrack())
				return true;
			copiar(temp, matriz);
		}
		mascara <<= 1;
	}
	return false;
}
	
// Reads the sudoku from stdin, solves it and prints
int main() {
	initMatriz();
	int val;
	for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
			cin >> val;
			if (val != 0)
				llenarCuadro(i,j,val);
		}	
	}
	backtrack();
	cout << "\n";
	imprimir();
}
	

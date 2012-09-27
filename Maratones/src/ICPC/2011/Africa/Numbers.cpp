
#include <iostream>
#include <cstring>

using namespace std;

	struct Respuesta
	{
		Respuesta *siguiente;
		char este;
		int tam;

		int compareTo(Respuesta o) 
		{
			if(o.tam == tam)
				return este - o.este;
			return tam - o.tam;
		}
	};
	
	char numero[1010];
	int r;
	int q;
	int tam;
	Respuesta dp[1002][1002];
	
	Respuesta* dpT(int n, int modulo)
	{
		if(dp[n][modulo].tam != -2)
			return &(dp[n][modulo]);
		if(n == tam)
		{
			if(modulo == r)
			{
				dp[n][modulo].siguiente = NULL;
				dp[n][modulo].este = '0';
				dp[n][modulo].tam = 0;
				return &(dp[n][modulo]);
			}
			else
			{
				dp[n][modulo].tam = -1;
				return &(dp[n][modulo]);
			}
		}
		else
		{
			Respuesta *posible = dpT(n + 1, (modulo * 10 + (numero[n] - '0')) % q);
			Respuesta actual;
			actual.tam = -1;
			if((*(posible)).tam != -1)
			{
				actual.siguiente = posible;
				actual.este = numero[n];
				actual.tam = (*(posible)).tam + 1;
			}
			Respuesta *siguiente = dpT(n + 1, modulo);
			if(actual.compareTo(*siguiente) < 0)
			{
				actual.siguiente = (*(siguiente)).siguiente;
				actual.este = (*(siguiente)).este;
				actual.tam = (*(siguiente)).tam;
			}
			dp[n][modulo] = actual;
			return &(dp[n][modulo]);
		}
	}
	
	Respuesta dpPoniendo(int n, int modulo)
	{
		Respuesta* siguiente = dpT(n + 1, (modulo * 10 + (numero[n] - '0')) % q);
		if((*(siguiente)).tam == -1)
		{
			Respuesta r;
			r.tam = -1;
			return r;
		}
		else
		{
			Respuesta r;
			r.siguiente = siguiente;
			r.este = numero[n];
			r.tam = (*(siguiente)).tam + 1;
			return r;
		}
	}
	int main()
	{
		int tests;
		cin >> tests;
		int valores[10][1000];
		int tams[10];
		for(int test = 0; test < tests; test++)
		{
			cin >> numero;
			tam = strlen(numero);
			cin >> r >> q;
			for(int i = 0; i <= tam; i++)
				for(int j = 0; j <= q; j++)
					dp[i][j].tam = -2;
			for(int i = 0; i < 10; i++)
				tams[i] = 0;
			for(int i = 0; i < tam; i++)
			{
				char actual = numero[i];
				valores[actual - '0'][tams[actual - '0']++] = i;
			}
			for(int i = 0; i < 10; i++)
				valores[i][tams[i]] = -1;
			int* numeros[10];
			for(int i = 0; i < 10; i++)
				numeros[i] = valores[i];
			int mejorTam = -1;
			for(int i = 0; i < tam; i++)
			{
				if(numero[i] != '0')
				{
					Respuesta t = dpPoniendo(i, 0);
					if(t.tam > mejorTam)
						mejorTam = t.tam;
				}
			}
			if(mejorTam <= 0)
				cout << "Not found" << endl;
			else
			{
				int tActual = mejorTam;
				int actual = -1;
				int modulo = 0;
				while(tActual > 0)
				{
					for(int i = 9; i >= 0; i--)
					{
						while(numeros[i][0] != -1 && ((numeros[i][0] <= actual) || (dpPoniendo(numeros[i][0], modulo).tam > tActual)))
							numeros[i]++;
						if(numeros[i][0] != -1 && (dpPoniendo(numeros[i][0], modulo).tam == tActual))
						{
							cout << (char) (i + '0');
							modulo *= 10;
							modulo += i;
							modulo %= q;
							actual = numeros[i][0];
							break;
						}		
					}
					tActual--;
				}
				cout << endl;
			}
		}
	}

#include <vector>
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

int main()
{
	int ncasos;
	cin >> ncasos;
	for(int i = 0; i < ncasos; i++)
	{
		int ncandidatos;
		cin >> ncandidatos;
		string candidatos[ncandidatos];
		string linea;
		for(int j = 0; j < ncandidatos; j++)
		{
			getline(cin, linea);
			istringstream tokens(linea);
			tokens >> candidatos[j];
		}
		getline(cin, linea);
		getline(cin, linea);
		vector < vector <int> > votantes(0);
		while(linea != "")
		{
			istringstream tokens(linea);
			vector <int> actual(ncandidatos);
			for(int j = 0; j < ncandidatos; j++)
			{
				int a;
				tokens >> a;
				actual.push_back(a);
			}
			votantes.push_back(actual);
			getline(cin, linea);
		}
		int ads = 2;
		for(int j = 0; i < votantes.size(); i++)
		{
			vector <int> actual = votantes.at(j);
			for(int k = 0; j < actual.size(); k++)
			{
				cout << actual.at(k) << endl;
			}
		}
	}
}

#include <iostream>
#include <string>

using namespace std;

int gcd(int a, int b)
{
	if(b == 0)
		return a;
	return gcd(b, a % b);
}

int main()
{
	while(true)
	{
		int p, n;
		cin >> p;
		cin >> n;
		if(p == 0 && n == 0)
			return 0;
		int ronda[p];
		int acum = 0;
		for(int i = 0; i < p; i++)
		{
			for(int j = 0; j < n - 1; j++)
			{
				int a;
				cin >> a;
			}
			cin >> ronda[i];
			acum += ronda[i];
		}
		for(int i = 0; i < p; i++)
		{
			int g = gcd(acum, ronda[i]);
			cout << ronda[i] / g << " / " << acum / g << endl;
		}
	}
}

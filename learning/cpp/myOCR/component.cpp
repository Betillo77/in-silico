#include "component.h"

using namespace MyOCR;
using namespace std;

int ConComponent::getNeighbors(Point* ans, Point act, Matrix* img) {
    int cuenta = 0;
    int delta[][2] = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
    for(int i = 0; i < 4; i++)
    {
        int iN = act.i + delta[i][0];
        int jN = act.j + delta[i][1];
        if(iN >= img->getHeight() || jN >= img->getWidth() || iN < 0 || jN < 0)
            continue;
        if((img->get(iN, jN) == 0))
            ans[cuenta++] = Point(iN, jN);
    }
    return cuenta;
}

ConComponent::ConComponent(int i, int j, Matrix* binImg) {
    Matrix & img = *binImg;
    left = 1 << 30, right = 0, top = 1 << 30, down = 0;
    vector <Point> s;
    s.push_back(Point(i, j));
    img(i, j) = 255;
    int act = 0;
    while(act < s.size())
    {
        Point p = s[act];
        Point vecinos[8];
        int N = getNeighbors(vecinos, p, binImg);
        for(int i = 0; i < N; i++)
        {
            img(vecinos[i].i, vecinos[i].j) = 255;
            s.push_back(vecinos[i]);
        }
        left = min(left, p.j);
        right = max(right, p.j);
        top = min(top, p.i);
        down = max(down, p.i);
        act++;
    }
    comp = new Matrix(right - left + 1, down - top + 1, 1);
    Matrix & ans = *comp;
    for(int i = 0; i < ans.getHeight(); i++)
        for(int j = 0; j < ans.getWidth(); j++)
            ans(i, j) = 0;
    for(int i = 0; i < s.size(); i++)
    {
        Point actual = s[i];
        ans(actual.i - top, actual.j - left) = 1;
    }
}

void ConComponent::printComponent() {
    for(int i = 0; i < down - top + 1; i++)
    {
        for(int j = 0; j < right - left + 1; j++)
        {
            cout << (comp->get(i, j) == 1 ? "+" : "-");
        }
        cout << endl;
    }
}
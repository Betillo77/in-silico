#include <string.h>

#include "component.h"
#include "config.h"

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

ConComponent::ConComponent(Matrix *imagen) {
    comp = imagen;
}

ConComponent::~ConComponent() {
    delete comp;
}

void ConComponent::printComponent() {
    for(int i = 0; i < comp->getHeight(); i++)
    {
        for(int j = 0; j < comp->getWidth(); j++)
        {
            cout << (comp->get(i, j) == 1 ? "+" : "-");
        }
        cout << endl;
    }
}

void ConComponent::saveComponent(int imageId) {
    Configuration *config = Configuration::getInstance();
    MYSQL *sql = config->connectDB();
    char *datos = (char*) comp->getData();
    int size = comp->getWidth() * comp->getHeight() * comp->getChannels();
    char salida[size * 2 + 1];
    mysql_real_escape_string(sql, salida, datos, size);
    char sqlQ[size * 2 + 200];
    int tam = sprintf(sqlQ, "insert into Components(imageId, width, height, data) VALUES('%i', '%i', '%i', '%s')", imageId, comp->getWidth(), comp->getHeight(), salida);
    std::cout << sqlQ << endl;
    mysql_real_query(sql, sqlQ, tam);
}

ConComponent *ConComponent::loadComponent(int componentId) {
    MYSQL *sql = Configuration::getInstance()->connectDB();
    char sqlQ[200];
    sprintf(sqlQ, "SELECT width, height, data FROM Components WHERE id=%i", componentId);
    mysql_query(sql, sqlQ);
    MYSQL_RES *result = mysql_store_result(sql);
    MYSQL_ROW row = mysql_fetch_row(result);
    int width = atoi(row[0]);
    int height = atoi(row[1]);
    Matrix *matrix = new Matrix(width, height, 1);
    char *datos = (char*) matrix->getData();
    memcpy(datos, row[2], width * height);
    mysql_free_result(result);
    return new ConComponent(matrix);
}
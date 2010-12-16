#include <string.h>

#include "component.h"
#include "config.h"

using namespace MyOCR;
using namespace std;

int ConComponent::getNeighbors(Point* ans, Point act, ImgMatrix* img) {
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

ImgMatrix* ConComponent::getMatrix() {
    return this->comp;
}

ConComponent::ConComponent(int i, int j, ImgMatrix* binImg) {
    ImgMatrix & img = *binImg;
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
    comp = new ImgMatrix(right - left + 1, down - top + 1, 1);
    ImgMatrix & ans = *comp;
    for(int i = 0; i < ans.getHeight(); i++)
        for(int j = 0; j < ans.getWidth(); j++)
            ans(i, j) = 0;
    for(int i = 0; i < s.size(); i++)
    {
        Point actual = s[i];
        ans(actual.i - top, actual.j - left) = 1;
    }
}

ConComponent::ConComponent(int l, int r, int t, int d, ImgMatrix *imagen) {
    left = l;
    right = r;
    top = t;
    down = d;
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

void ConComponent::saveComponent(const char *imageId) {
    Configuration *config = Configuration::getInstance();
    MYSQL *sql = config->connectDB();
    char *datos = (char*) comp->getData();
    int size = comp->getWidth() * comp->getHeight() * comp->getChannels();
    char salida[size * 2 + 1];
    mysql_real_escape_string(sql, salida, datos, size);
    char sqlQ[size * 2 + 200];
    int tam = sprintf(sqlQ, "insert into Components(imageId, leftP, rightP, topP, downP, data) VALUES('%s', '%i', '%i', '%i', '%i', '%s')", imageId, left, right, top, down, salida);
    //std::cout << sqlQ << endl;
    mysql_real_query(sql, sqlQ, tam);
}

ConComponent *ConComponent::loadComponent(int componentId) {
    MYSQL *sql = Configuration::getInstance()->connectDB();
    char sqlQ[200];
    sprintf(sqlQ, "SELECT leftP, rightP, topP, downP, data FROM Components WHERE id=%i", componentId);
    mysql_query(sql, sqlQ);
    MYSQL_RES *result = mysql_store_result(sql);
    MYSQL_ROW row = mysql_fetch_row(result);
    if (row == NULL) {
         mysql_free_result(result);
         return NULL;
    }
    int left = atoi(row[0]);
    int right = atoi(row[1]);
    int top = atoi(row[2]);
    int down = atoi(row[3]);
    ImgMatrix *matrix = new ImgMatrix(right - left + 1, down - top + 1, 1);
    char *datos = (char*) matrix->getData();
    memcpy(datos, row[4], (right - left + 1) * (down - top + 1));
    mysql_free_result(result);
    return new ConComponent(left, right, top, down, matrix);
}
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

Matrix* ConComponent::getMatrix() {
    return this->comp;
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
    for(int i = 0; i < 4; i++)
        for(int j = 0; j < 4; j++)
            dpN[i][j] = dpM[i][j] = dpU[i][j] = numeric_limits<double>::infinity();
    dpI = NULL;
}

double ConComponent::m(int iV, int jV) {
    if(dpM[iV][jV] != numeric_limits<double>::infinity())
        return dpM[iV][jV];
    double acum = 0;
    int height = down - top + 1;
    int width = right - left + 1;
    double deltaI = 1.0;
    double deltaJ = 1.0;
    double iAct = 0;
    double jAct = 0;
    for(int i = 0; i < height; i++)
    {
        for(int j = 0; j < width; j++)
        {
            pixel act = comp->get(i, j);
            acum += act == 0 ? 0 : act * pow(iAct, iV) * pow(jAct, jV);
            jAct += deltaJ;
        }
        iAct += deltaI;
        jAct = 0;
    }
    return dpM[iV][jV] = acum;
}

double ConComponent::u(int iV, int jV) {
    if(dpU[iV][jV] != numeric_limits<double>::infinity())
        return dpU[iV][jV];
    double xAvg = m(1, 0) / m(0, 0);
    double yAvg = m(0, 1) / m(0, 0);
    switch(iV)
    {
        case 0:
            switch(jV)
            {
                case 0: return dpU[iV][jV] = m(0, 0);
                case 1: return dpU[iV][jV] = 0;
                case 2: return dpU[iV][jV] = m(0, 2) - yAvg * m(0, 1);
                case 3: return dpU[iV][jV] = m(0, 3) - 3 * yAvg * m(0, 2) + 2 * pow(yAvg, 2) * m(0, 1);
            }
        case 1:
            switch(jV)
            {
                case 0: return dpU[iV][jV] = 0;
                case 1: return dpU[iV][jV] = m(1, 1) - xAvg * m(0, 1);
                case 2: return dpU[iV][jV] = m(1, 2) - 2 * yAvg * m(1, 1) - xAvg * m(0, 2) + 2 * pow(yAvg, 2) * m(1, 0);
                case 3: return dpU[iV][jV] = 0;
            }
        case 2:
            switch(jV)
            {
                case 0: return dpU[iV][jV] = m(2, 0) - xAvg * m(1, 0);
                case 1: return dpU[iV][jV] = m(2, 1) - 2 * xAvg * m(1, 1) - yAvg * m(2, 0) + 2 * pow(xAvg, 2) * m(0, 1);
                case 2: return dpU[iV][jV] = 0;
                case 3: return dpU[iV][jV] = 0;
            }
        case 3:
            switch(jV)
            {
                case 0: return dpU[iV][jV] = m(3, 0) - 3 * xAvg * m(2, 0) + 2 * pow(xAvg, 2) * m(1, 0);
                case 1: return dpU[iV][jV] = 0;
                case 2: return dpU[iV][jV] = 0;
                case 3: return dpU[iV][jV] = 0;
            }

    }
}

double ConComponent::n(int iV, int jV) {
    if(dpN[iV][jV] != numeric_limits<double>::infinity())
        return dpN[iV][jV];
    return dpN[iV][jV] = u(iV, jV) / pow(u(0, 0), 1 + ((double) (iV + jV)) / 2);
}

double *ConComponent::huMoments() {
    if(dpI != NULL)
        return dpI;
    dpI = new double[8];
    dpI[0] = n(2, 0) + n(0, 2);
    dpI[1] = pow(n(2, 0) - n(0, 2), 2) + pow(2 * n(1, 1), 2);
    dpI[2] = pow(n(3, 0) - 3 * n(1, 2), 2) + pow(3 * n(2, 1) - n(0, 3), 2);
    dpI[3] = pow(n(3, 0) + n(1, 2), 2) + pow(n(2, 1) + n(0, 3), 2);
    dpI[4] = (n(3, 0) - 3 * n(1, 2)) * (n(3, 0) + n(1, 2)) * (pow(n(3, 0) + n(1, 2), 2) - 3 * pow(n(2, 1) + n(0, 3), 2)) + (3 * n(2, 1) - n(0, 3)) * (n(2, 1) + n(0, 3)) * (3 * pow(n(3, 0) + n(1, 2), 2) - pow(n(2, 1) + n(0, 3), 2));
    dpI[5] = (n(2, 0) - n(0, 2)) * (pow(n(3, 0) + n(1, 2), 2) - pow(n(2, 1) + n(0, 3), 2)) + 4 * n(1, 1) * (n(3, 0) + n(1, 2)) * (n(2, 1) + n(0, 3));
    dpI[6] = (3 * n(2, 1) - n(0, 3)) * (n(3, 0) + n(1, 2)) * (pow(n(3, 0) + n(1, 2), 2) - 3 * pow(n(2, 1) + n(0, 3), 2)) - (n(3, 0) - 3 * n(1, 2)) * (n(2, 1) + n(0, 3)) * (3 * pow(n(3, 0) + n(1, 2), 2) - pow(n(2, 1) + n(0, 3), 2));
    dpI[7] = n(1, 1) * (pow(n(3, 0) + n(1, 2), 2) - pow(n(0, 3) + n(2, 1), 2)) - (n(2, 0) - n(0, 2)) * (n(3, 0) + n(1, 2)) * (n(0, 3) + n(2, 1));
    return dpI;
}

ConComponent::ConComponent(int l, int r, int t, int d, Matrix *imagen) {
    left = l;
    right = r;
    top = t;
    down = d;
    comp = imagen;
    for(int i = 0; i < 4; i++)
        for(int j = 0; j < 4; j++)
            dpN[i][j] = dpM[i][j] = dpU[i][j] = numeric_limits<double>::infinity();
    dpI = NULL;
}

ConComponent::~ConComponent() {
    delete comp;
    if(dpI != NULL)
        delete dpI;
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
    Matrix *matrix = new Matrix(right - left + 1, down - top + 1, 1);
    char *datos = (char*) matrix->getData();
    memcpy(datos, row[4], (right - left + 1) * (down - top + 1));
    mysql_free_result(result);
    return new ConComponent(left, right, top, down, matrix);
}
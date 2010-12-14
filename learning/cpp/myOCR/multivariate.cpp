
#include "multivariate.h"
/*#include "mysql.h"
#include <cstdio>
#include <cstdlib>

using namespace MyOCR;

Multivariate::Multivariate(double traingProb) {
    MYSQL* conn = Configuration::getInstance()->connectDB();
    char sql[100];
    sprintf(sql, "select id from Components where rand() < %lf", traingProb);
    mysql_query(conn, sql);
    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row;
    while ( (row = mysql_fetch_row(result)) != NULL ) {
        data.push_back(ConComponent::loadComponent( atoi(row[0]) ));
    }
}

Multivariate::~Multivariate() {

}*/
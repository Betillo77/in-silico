/* 
 * File:   config.h
 * Author: seb
 *
 * Created on 7 de diciembre de 2010, 04:03 PM
 */

#ifndef _CONFIG_H
#define	_CONFIG_H
#define CONF_FILE "config.txt"

#include <cstdio>
#include <map>
#include <mysql.h>
#include <string>

using namespace std;

namespace MyOCR {

    class Configuration {
        map<string,string> tabla;
        MYSQL *conn;        
    public:
        Configuration();
        MYSQL* connectDB();
        string getValue(string key);
        static Configuration* getInstance();
        static void executeEscalar(char *sql, char *ans);
    };

}
#endif	/* _CONFIG_H */


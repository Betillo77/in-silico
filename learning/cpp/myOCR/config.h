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

class Configuration {
    map<string,string> tabla;
public:
    Configuration();
    MYSQL connectDB();
};

#endif	/* _CONFIG_H */


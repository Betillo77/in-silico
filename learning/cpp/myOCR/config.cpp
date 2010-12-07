
#include "config.h"

using namespace MyOCR;

Configuration::Configuration() {
    FILE *f = fopen(CONF_FILE, "r");
    char act;
    char buf[256];
    do {
        act = fgetc(f);
        if (act == '#') {
            while (fgetc(f) != '\n');
        } else if (act!='\n' && act!=' ' && act!='\t' && act!=EOF) {
            int i=0;
            do {
                buf[i++]=act;
            } while ((act=fgetc(f))!='=' && act!=EOF);
            buf[i]='\0';
            string key(buf);

            i=0;
            while ((act=fgetc(f))!='\n' && act!=EOF) {
                buf[i++]=act;
            }
            buf[i]='\0';
            string value(buf);
            tabla[key] = value;
        }

    } while (act != EOF);

    conn=NULL;
}

MYSQL* Configuration::connectDB() {
    if (conn == NULL) {
        conn = mysql_init(NULL);
        const char* dbname = tabla["dbname"].c_str();
        const char* host = tabla["host"].c_str();
        const char* user = tabla["user"].c_str();
        const char* passwd = tabla["passwd"].c_str();
        mysql_real_connect(conn, host, user, passwd, dbname, 0, NULL, 0);
    }
    return conn;
}

Configuration* Configuration::getInstance() {
    static Configuration* myInst= new Configuration();
    return myInst;
}

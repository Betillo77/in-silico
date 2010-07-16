
#include "io_utils.h"

int listFiles(const char *directory, void (*func)(const char *)) {
    #ifdef LINUX
    DIR *dp;
    struct dirent *dfile;
    if( ( dp  = opendir(directory) ) == 0 ) return errno;

    while ((dfile = readdir(dp)) != 0) {
        func(dfile->d_name);
    }
    closedir(dp);
    return 0;
    #endif
}

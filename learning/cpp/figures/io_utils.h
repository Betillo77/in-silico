/* 
 * File:   io_utils.h
 * Author: seb
 *
 * Created on 15 de julio de 2010, 11:25 AM
 */

#ifndef _IO_UTILS_H
#define	_IO_UTILS_H

#if defined WIN32 || defined _WIN32 || defined WIN64 || defined _WIN64
    #include <windows.h>
#else 
#ifndef ANDROID
    #include <sys/types.h>
    #include <dirent.h>
    #include <errno.h>
    #define LINUX
#endif
#endif

//definitions

int listFiles(const char *directory, void (*func)(const char *));

#endif	/* _IO_UTILS_H */


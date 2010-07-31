
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
    #ifdef ANDROID
    jobjectArray arreglo;
    jmethodID mid = virtualMachine->GetStaticMethodID(thisClass, "listFiles", "()[Ljava/lang/String;");
    arreglo = (jobjectArray) virtualMachine->CallStaticObjectMethod(thisClass, mid);
    jstring file;
    for(int i = 0; i < 5; i++)
    {
	file = (jstring) virtualMachine->GetObjectArrayElement(arreglo, i);
	const char *fileName = virtualMachine->GetStringUTFChars(file, 0);
	func(fileName);
    }
    return 0;
    #endif
}

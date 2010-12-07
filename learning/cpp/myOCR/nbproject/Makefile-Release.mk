#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=
AS=

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_CONF=Release
CND_DISTDIR=dist

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/component.o \
	${OBJECTDIR}/matrix.o \
	${OBJECTDIR}/transform.o \
	${OBJECTDIR}/config.o \
	${OBJECTDIR}/android.o \
	${OBJECTDIR}/test.o \
	${OBJECTDIR}/documentLayout.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=-L/usr/include/opencv

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	${MAKE}  -f nbproject/Makefile-Release.mk dist/Release/GNU-Linux-x86/myocr

dist/Release/GNU-Linux-x86/myocr: ${OBJECTFILES}
	${MKDIR} -p dist/Release/GNU-Linux-x86
	${LINK.cc} -I/usr/local/include/opencv -L/usr/local/lib -lcxcore -lcv -lhighgui -lcvaux -lml -DBIG_JOINS=1 -fPIC -fno-strict-aliasing -Wl,-Bsymbolic-functions -rdynamic -L/usr/lib/mysql -lmysqlclient -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/myocr ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/component.o: nbproject/Makefile-${CND_CONF}.mk component.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/component.o component.cpp

${OBJECTDIR}/matrix.o: nbproject/Makefile-${CND_CONF}.mk matrix.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/matrix.o matrix.cpp

${OBJECTDIR}/transform.o: nbproject/Makefile-${CND_CONF}.mk transform.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/transform.o transform.cpp

${OBJECTDIR}/config.o: nbproject/Makefile-${CND_CONF}.mk config.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/config.o config.cpp

${OBJECTDIR}/android.o: nbproject/Makefile-${CND_CONF}.mk android.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/android.o android.cpp

${OBJECTDIR}/test.o: nbproject/Makefile-${CND_CONF}.mk test.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/test.o test.cpp

${OBJECTDIR}/documentLayout.o: nbproject/Makefile-${CND_CONF}.mk documentLayout.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -I/usr/include/mysql -MMD -MP -MF $@.d -o ${OBJECTDIR}/documentLayout.o documentLayout.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r build/Release
	${RM} dist/Release/GNU-Linux-x86/myocr

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc

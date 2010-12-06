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
AS=as

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
	${OBJECTDIR}/android.o \
	${OBJECTDIR}/component.o \
	${OBJECTDIR}/test.o \
	${OBJECTDIR}/matrix.o \
	${OBJECTDIR}/DocumentLayout.o \
	${OBJECTDIR}/transform.o

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
	${LINK.cc} -I/usr/local/include/opencv -L/usr/local/lib -lcxcore -lcv -lhighgui -lcvaux -lml -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/myocr ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/android.o: nbproject/Makefile-${CND_CONF}.mk android.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/android.o android.cpp

${OBJECTDIR}/component.o: nbproject/Makefile-${CND_CONF}.mk component.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/component.o component.cpp

${OBJECTDIR}/test.o: nbproject/Makefile-${CND_CONF}.mk test.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/test.o test.cpp

${OBJECTDIR}/matrix.o: nbproject/Makefile-${CND_CONF}.mk matrix.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/matrix.o matrix.cpp

${OBJECTDIR}/DocumentLayout.o: nbproject/Makefile-${CND_CONF}.mk DocumentLayout.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/DocumentLayout.o DocumentLayout.cpp

${OBJECTDIR}/transform.o: nbproject/Makefile-${CND_CONF}.mk transform.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -I/usr/local/include/opencv -MMD -MP -MF $@.d -o ${OBJECTDIR}/transform.o transform.cpp

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

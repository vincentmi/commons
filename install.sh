#!/bin/sh

mvn install:install-file  \
-DgroupId=com.vnzmi.commons \
-DartifactId=micro-service-utils \
-Dversion=1.0-SNAPSHOT \
-Dfile=./target/micro-service-utils-1.0-SNAPSHOT.jar \
-Dpackaging=jar \
-DgeneratePom=true
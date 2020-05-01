#!/usr/bin/env bash

python scripts/updateMavenSettings.py
mvn clean

if [[ $2 == "OFFLINE" ]]
then
	mvn -T1.5C -o -Dcmake.root.dir=$CMAKE_ROOT -P$1 package
else
	mvn -T1.5C -Dcmake.root.dir=$CMAKE_ROOT -P$1 package
fi

if [ $? -eq 0 ] 
then
	bash ./bin/updatePATHS.sh 
else
		
	echo -e "\n \e[91mCompilation failed\e[39m \n"
fi


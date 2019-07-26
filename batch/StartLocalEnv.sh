#!/bin/sh
cd ../
mkdir local_env
./gradlew jar
cp "build\libs\katana-1.0-SNAPSHOT.jar" "local_env\katana.jar"
cd local_env
java -jar katana.jar
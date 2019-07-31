#!/bin/sh
cd ../

mkdir local_env

chmod 0744 gradlew
./gradlew shadowJar

cp "build/libs/katana-1.0-SNAPSHOT-all.jar" "local_env/katana.jar"

cd local_env

java -jar katana.jar
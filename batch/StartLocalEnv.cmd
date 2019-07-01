cd ../
mkdir local_env
call gradlew jar
copy "build\libs\katana-1.0-SNAPSHOT.jar" "local_env\katana.jar"
cd local_env
start java -jar katana.jar
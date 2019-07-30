cd ../
mkdir local_env
call gradlew shadowJar
copy "build\libs\katana-1.0-SNAPSHOT-all.jar" "local_env\katana.jar"
cd local_env
start java -jar katana.jar
cd ../
call gradlew publish
cd build/repo
call git add .
call git commit -m "publish repo"
call git push
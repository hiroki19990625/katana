cd ../
call gradlew publish
cd repo
call git pull
call git add .
call git commit -m "publish repo"
call git push
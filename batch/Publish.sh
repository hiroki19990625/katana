#!/bin/sh
cd ../

chmod 0744 gradlew

repo_dir=repo
if [ ! -d "$repo_dir" ]; then
    git clone "https://github.com/hiroki19990625/maven-repo.git"
    mvdir -r maven-repo repo
fi

cd repo

git config --global user.email "ci_user@ci.com"
git config --global user.name "hiroki19990625"

git pull

../gradlew publish

git add .
git commit -m "publish repo"
git push
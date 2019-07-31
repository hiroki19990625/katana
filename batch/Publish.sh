#!/bin/sh
cd ../

chmod 0744 gradlew

git_dir=.git
if [ ! -d "$git_dir" ]; then
    git clone "https://github.com/hiroki19990625/maven-repo.git"
fi

cd maven-repo

git config --global user.email "ci_user@ci.com"
git config --global user.name "hiroki19990625"

git pull

../gradlew publish

git add .
git commit -m "publish repo"
git push
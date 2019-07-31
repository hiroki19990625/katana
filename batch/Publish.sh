#!/bin/sh
cd ../

chmod 0744 gradlew
./gradlew publish

cd repo

git_dir=.git
if [ ! -d "$git_dir" ]; then
    git clone "https://github.com/hiroki19990625/maven-repo.git"
fi

git config --global user.email "ci_user@ci.com"
git config --global user.name "hiroki19990625"

git pull
git add .
git commit -m "publish repo"
git push
#!/bin/bash
echo "start"
# chmod +x ./update.sh
funGit(){
    pathGit=$1
    path=${pathGit%.*}
    echo -e "path:\033[35m" $path    "\n\033[0m"  $(cd $path&&git pull)&
}

for i in $(find /home/ztftrue/Documents -name .git -type d); do # Not recommended, will break on whitespace
    funGit $i
done

wait



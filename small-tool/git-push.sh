#!/bin/bash
echo -n  "please enter file name, like . ->\n files:"
read  -e filename
git add $filename
echo -n  "please enter commit message ->\n type scope : subject \n message:  "
read -e message
git commit -m $message
echo -n  "开始推送"
git push origin master
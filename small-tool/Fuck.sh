#!/bin/bash
# 从Android上截图 转成PDF
echo "start"
# chmod +x ./update.sh

for i in {1..10000}
do
    adb exec-out screencap -p > ./img/$i'.png'
    # convert ./img/$i'.png' -crop 1080x2166+0+100 ./img/$i'.png'
    convert ./img/$i'.png' -crop 1080x2000+0+198 ./img/$i'.png'
    adb shell input swipe 1000 1000 0 1000
    sleep 1s
done

# convert  $(ls -v ./img/*.png ) output.pdf&&rm ./img/*
# rm ./img/*
# pdf convert image
# mkdir -p images && pdftoppm -jpeg -r 300 ztftrue-web-mark.pdf images/pg

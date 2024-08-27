#!/bin/bash
echo "start"
# chmod +x ./update.sh
funIterator(){
    pathGit=$1
    # -cover 新文件夹名称
    path="cover/${pathGit%/*}/"
    name="${pathGit##*/}"
    # path="${pathGit%/*}/"
	# https://blog.csdn.net/github_33736971/article/details/53980123
    pathCover="${path}${name%MP4}jpg"
    echo "$pathCover"
    echo "$path"
    if [ -d path  ];then
        echo "$DIR directory exists."
    else
    	`mkdir -p ${path}`
    fi
    if [ -f "$pathCover" ]; then
        echo 'exits'
        rm "$pathCover"
    fi
    # https://stackoverflow.com/questions/4425413/how-to-extract-the-1st-frame-and-restore-as-an-image-with-ffmpeg
    ffmpeg -hide_banner -loglevel error -i "$pathGit" -vf "select=eq(n\,0)" -q:v 3 "$pathCover"
}
myArray=()
while IFS= read -r -d $'\0' file; do
    echo "-----------------------"
    echo "$file"
    myArray+=("$file") 
# done < <(find `pwd -P` -type f -name *.mp4 -print0)
done < <(find ./ -type f -name *.MP4 -print0)

len=${#myArray[@]}
i=0
while [ $i -lt $len ]; do
    echo "${myArray[$i]}"
    funIterator "${myArray[$i]}"
    let i++
done

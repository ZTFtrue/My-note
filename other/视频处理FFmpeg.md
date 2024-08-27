#

## 压缩视频

```bash
ffmpeg -i origin.mp4 -codec:v libx264 -profile:v main -b:v 200k -maxrate 200k -bufsize 200k -threads 2 -codec:a aac -ar 8000 output.mp4
```

裁剪 <https://video.stackexchange.com/questions/4563/how-can-i-crop-a-video-with-ffmpeg>

```bash
ffmpeg -i in.mp4 -filter:v "crop=out_w:out_h:x:y" out.mp4
```

## 合并字幕和视频文件

ffmpeg -i input.mp4 -vf subtitles=subtitle.srt output_srt.mp4

## 移动视频信息到头部

./ffmpeg -y -i SourceFile.mp4 -c:a copy -c:v copy -movflags faststart DestFile.mp4
<https://stackoverflow.com/questions/48156306/html-5-video-tag-range-header>

<https://superuser.com/questions/856025/any-downsides-to-always-using-the-movflags-faststart-parameter>

### 检查转换成功

<https://superuser.com/questions/559372/using-ffmpeg-to-locate-moov-atom>
ffmpeg -v trace -i file.mp4 2>&1 | grep -e type:\'mdat\' -e type:\'moov\'
<https://superuser.com/questions/559372/using-ffmpeg-to-locate-moov-atom>

## 批量获取视频封面

```bash
#!/bin/bash
echo "start"
# chmod +x ./update.sh
funIterator(){
    pathGit=$1
    # -cover 新文件夹名称/ 批量获取视频封面
    path="cover/${pathGit%/*}/"
    name="${pathGit##*/}"
    # path="${pathGit%/*}/"
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
```

## ffplay

```sh
 ./ffplay -i '.\Waiting For A Girl Like You.m4a' -af asetrate=44100*10/8,atempo=8/10
```

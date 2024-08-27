#

## 搭建RTSP 服务

tag: 直播

1. 下载:<https://github.com/bluenviron/mediamtx> 和 `ffmpeg`
2. 运行`mediamtx`
3. 执行`ffmpeg -re -stream_loop -1 -i test.mp4 -c copy -f rtsp -rtsp_transport tcp rtsp://localhost:8554/mystream`
4. 使用播放器打开:`rtsp://localhost:8554/mystream`

## 大华rtsp地址

`rtsp://NAME:PASSWORD@IP:554/cam/realmonitor?channel=1&subtype=1`

## Android Rtsp 视频播放

NodeMedia:NodeMediaClient

opencv (需要二次开发)

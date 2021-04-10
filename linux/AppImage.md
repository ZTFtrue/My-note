# desktop文件解析 如何在gnome(untiy应该差不多)下自动启动 AppImage

此处以name.AppImage为例

## 创建后缀desktop文件，文件内容如下

```sh
touch eg.desktop
```

```sh
#!/usr/bin/env xdg-open
[Desktop Entry]
Encoding=UTF-8
Terminal=0
Exec=/home/username/bin/name.AppImage
Icon=/home/username/bin/ssr.png
Type=Application
Categories=Network;
StartupNotify=true
Name=Name
GenericName=Name
Name[en_US]=Name
```

desktop关键内容解析，特别注意Exec

```sh
Exec # 执行的文件路径 此处为, /home/ztftrue/bin/electron-ssr/electron-ssr-0.2.4.AppImage ，你应该使用自己的位置. Exec可以执行sh或者其它可执行文件

Icon # 图标，你可以不规定, 它不会在功能上有不好的影响

Name # desktop默认显示的名称 注意它和文件名不同，当你设置信任(<gnome3.30)或者放到桌面(gnome新版本已不支持)或者放到 ～/.local/share/applications 时在相应的显示界面比如所有应用菜单会显示 name 的内容，而非文件名
```

## 设置启动

1. 把文件放置到 ～/.local/share/applications/ 目录，在所有应用可以看到刚才放置的图标

2. 使用 gnome-tweak-tool 设置，或者把文件放置到 ～/.config/autostart/

3. 重启或者注销试试

## 附加

由于我屏幕是HDPI，设置开机立即启动，会使字体非常小(原因我不知道). 所以我使用 sh 延时启动

```sh
#!/bin/sh
sleep 10;
/home/ztftrue/bin/electron-ssr/electron-ssr-0.2.4.AppImage
```

> [注意 sh 不是 bash,记得bash也是可以的，但是现在在我这里不行](https://stackoverflow.com/questions/5725296/difference-between-sh-and-bash)

desktop支持sh文件，修改 Exec

```sh
Exec=/home/ztftrue/bin/electron-ssr/ssr.sh
```

> __以上内容只在我的机器上测试过__

```sh
Linux 4.18.8-Linux 5.6.13-arch1-1

Gnome version 3.30.0
```

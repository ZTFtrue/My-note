# 刷机

https://sspai.com/post/68192

需要`adb`工具.

需要解锁, 解锁和上锁都会清除数据.

需要工具: `UnSIN`, `UnSIN` 有 `linux` 版本.

## 下载镜像

使用 `XperiFirm` 选择对应型号下载镜像.

## 更新系统

将 `` 放入镜像文件价中, 按住音量下键. 运行 `` 文件等待更新完成.

`Xperia 5 iv` 这一步不需要清除数据, 可以保留原来的用户数据(建议备份还是数据), 即使之前安装过 Magisk.

## 刷入 Magisk

安装 `Magisk.apk` , 在镜像目录选取 `boot_X-FLASH-ALL-xxxx.sin` 文件, 如 `boot_X-FLASH-ALL-2389.sin`.

在镜像目录选取 `boot_X-FLASH-ALL-xxxx.sin` 文件, 放到和 `UnSIN` 同一目录中, 运行`unsin.exe`(根据系统选择), 获取到`img` 格式的镜像.

将这个镜像放入手机,使用 `magisk` -> `install` . 然后将生成文件移动到电脑.

按住音量下键, 连接电脑, 进入 `fastboot Mode`（指示灯蓝色,屏幕没有显示, 这一点和小米不一样). 有的是按住上键. sony 官网可以查到.

然后使用电脑刷入.

```bash
fastboot flash boot_a image-patch.img
fastboot flash boot_b image-patch.img
```

## Xperia 5 ii 和 Xperia  1 ii 的  Android 11 特殊处理

使用工具 `Android Image Kitchen` 将 `magisk` 修改后的镜像 `unpack`.

进入解压后的 `ramdisk` 文件夹. 

找到名为 `fstab` 的文件打开，能看到一行以 `system_ext` 开头的代码，将这行代码删掉并保存。
将修改后的 `ramdisk` 和 其它解包出来的文件 进行 repack， 得到 新的 `img` 文件.

然后进行刷入.

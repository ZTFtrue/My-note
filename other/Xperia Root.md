# 刷机

https://sspai.com/post/68192

需要`adb`工具.

**需要解锁, 解锁和上锁都会清除数据**.

需要工具:

1. [UnSIN](https://xdaforums.com/t/tool-unsin-sin-v3-v4-v5-unpacker-v2-0.3128106/)
2. [newflasher](https://xdaforums.com/t/tool-newflasher-xperia-command-line-flasher.3619426/
) [github-newflasher](https://github.com/munjeni/newflasher), 都有 `linux` 版本.
3. 驱动 https://developer.sony.com/file/download/xperia-5-iv-driver/ 可选（装不上）
4. Flashtool https://github.com/Androxyde/Flashtool/releases 可选 这里可以选择驱动安装

window 驱动安装，在`Flashtool` 的安装目录`C:\Flashtool\drivers`中找到驱动安装软件，安装`Flashmode drivers`和 `Fastboot drivers`(就在第一二个位置)。

安装失败的处理办法：

以管理员运行`bcdedit /set testsigning on`,重启

安装以后需要做的步骤(可选)

1. 关闭测试模式
`bcdedit /set testsigning off`
2. 检查系统完整性
`sfc /scannow`

## 下载镜像

使用 `XperiFirm` 选择对应型号下载镜像.

## 更新系统

`Xperia 5 iv` 这一步不需要清除数据, 可以保留原来的用户数据(建还是数据议备份).

将 `newflasher` 放入镜像文件夹中。手机关机， 按住音量下键，插入电脑，指示灯-亮起（不是红色就可以）。

运行 `newflasher`(linux示例： `./newflasher.x64`, window 直接运行exe文件 ) 等待更新完成.

~~`newflasher` 第一步询问刷机完成重启模式，第二步询问是否备份(选择不备份可以偷懒)，确认以后会进行更新。~~

## 刷入 Magisk

1. 在手机安装 `Magisk.apk`

2. 在镜像目录选取 `boot_X-FLASH-ALL-xxxx.sin` 文件, 如 `boot_X-FLASH-ALL-6348.sin`. 放到和 `UnSIN` 同一目录中, 打开终端运行`./unsin ./boot_X-FLASH-ALL-6348.sin`(根据系统选择), 获取到`img` 格式的镜像.

3. 将这个镜像放入手机,使用 `magisk` -> `install`. 然后将生成文件移动到电脑.

4. 关机，按住音量上键, 连接电脑, 进入 `fastboot Mode`（指示灯蓝(亮起就行)色,屏幕没有显示, 这一点和小米不一样). 有的是按住下键. sony 官网可以查到.

然后使用电脑刷入.

```bash
fastboot flash boot_a image-patch.img
fastboot flash boot_b image-patch.img
```

重启

```bash
fastboot reboot
```

注意：如果是magisk是隐藏模式，要选择隐藏的那个apk，不然状态还是未安装。

## Xperia 5 ii 和 Xperia  1 ii 的  Android 11 特殊处理

使用工具 `Android Image Kitchen` 将 `magisk` 修改后的镜像 `unpack`.

进入解压后的 `ramdisk` 文件夹.

找到名为 `fstab` 的文件打开，能看到一行以 `system_ext` 开头的代码，将这行代码删掉并保存。
将修改后的 `ramdisk` 和 其它解包出来的文件 进行 repack， 得到 新的 `img` 文件.

然后进行刷入.

# Arch Install

## 检查孤立安装包

```bash
pacman -Qdt
```

## systemed 分析

```sh
systemd-analyze

systemd-analyze blame
```

## other

```bash
mysql -u ztftrue -p

ssh-keygen

optirun glxspheres64

proxychains4 curl google.com

optirun glxgears -info

ssh -vvT git@106.14.239.47 \# 测试 key 是否正确

clamscan --recursive --infected /home

#kill -3 pid 重启进程

#imgcmp 获取图片信息

scp -r -P 2048 static username@ip:~/dir
# gpg --recv-keys 465022E743D71E39
```

## 解压乱码

```sh
# 解压
LANG=C 7za x a.zip
# 转码
convmv -f GBK -t utf8 --notest -r .
```

## yay 不能用修复

```sh
yay -G yay #clones new yay from git
yay -R yay #removes old yay
sudo pacman -Syu
cd yay/
makepkg -si #install the yay you cloned
yay #do your yay system upgrade you were trying to do in the first place
```

## 可能需要

```sh
yay -Syu gnome-shell-extension-appindicator
```

### Java 环境切换

```sh
# List compatible Java environments installed

archlinux-java status

# Change default Java environment
# archlinux-java set <JAVA_ENV_NAME>
# archlinux-java set java-8-openjdk/jre
# archlinux-java set java-14-openjdk
```

### [用户目录 as Desktop folder](https://unix.stackexchange.com/questions/167740/how-to-make-gnome-use-my-home-folder-as-desktop-folder)

## 安装缺少的包

 `mkinitcpio-firmware`

 <https://wiki.archlinux.org/title/Mkinitcpio#Possibly_missing_firmware_for_module_XXXX>

## Host 文件配置

`/etc/hosts`

```text
#
# hosts         This file describes a number of hostname-to-address
#               mappings for the TCP/IP subsystem.  It is mostly
#               used at boot time, when no name servers are running.
#               On small systems, this file can be used instead of a
#               "named" name server.
# Syntax:
#    
# IP-Address  Full-Qualified-Hostname  Short-Hostname
#

127.0.0.1 localhost

# special IPv6 addresses
::1             localhost ipv6-localhost ipv6-loopback

fe00::0         ipv6-localnet

ff00::0         ipv6-mcastprefix
ff02::1         ipv6-allnodes
ff02::2         ipv6-allrouters
ff02::3         ipv6-allhosts
```

## Sorry, try again.

有时`sudo`输入密码出现`Sorry, try again.`且一直不正确。

tag: sudo 密码错误

```
# su
# faillock --reset
```

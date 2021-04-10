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

scp -r -P 2048 static ztftrue@45.77.31.203:~/sireye
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

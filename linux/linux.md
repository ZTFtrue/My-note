# Linux

## 临时文件示例

打开文件后立即删除，程序退出的时候临时文件就会自动释放.

## rsync 同步工具

rsync 的增量更新机制, zip 压缩包也可以使用.

## 输入输出重定向

## Linux 权限解释

权限:

对于一组权限:

||读 |写 | 执行|
|---|---|---|---|
|字母|r|w |x|
|八进制数字|4|2|1|

```sh
r-- = 100
-w- = 010
--x = 001
--- = 000
转8进制
rwx = 111 = 7
rw- = 110 = 6
r-x = 101 = 5
r-- = 100 = 4
-wx = 011 = 3
-w- = 010 = 2
--x = 001 = 1
--- = 000 = 0
```

更改文件权限:

```sh
    chmod [可选项] <mode> <file...>
    # man chmod
```

Example:

```sh
  chmod 755 test.text
```

解释:

1. `755` 7 对应拥有者, 5 群组 ,5其它组

更改文件拥有者:

```sh
    chown [可选项] user[:group] file...
```

参考位运算的解析

## 查看文件类型

file

## 关机

```sh
# /sbin/shutdown [-t 秒] [-arkhncfF] 时间 [警告信息]  

选项与参数:   

-t sec :  -t 后面加秒数，亦即『过几秒后关机』的意思

-k     :  不要真的关机，只是发送警告信息出去！

-r     :  在将系统的服务停掉之后就重新启动(常用)

-h     :  将系统的服务停掉后，立即关机.  (常用)

-n     :  不经过 init 程序，直接以 shutdown 的功能来关机

-f     :  关机并启动之后，强制略过 fsck 的磁盘检查

-F     :  系统重新启动之后，强制进行 fsck 的磁盘检查

-c     :  取消已经在进行的 shutdown 命令内容. 

时间   :  这是一定要加入的参数！指定系统关机的时间！时间的范例底下会说明. 

范例: 

[root@www ~]# /sbin/shutdown -h 10 'I will shutdown after 10 mins'

# 告诉大家，这部机器会在十分钟后关机(真的会关掉)！并且会显示在目前登陆者的屏幕前方！

# 至于参数有哪些呢？以下介绍几个吧！

[root@www ~]# shutdown -h now

立刻关机，其中 now 相当于时间为 0 的状态

[root@www ~]# shutdown -h 20:25

系统在今天的 20:25 分会关机，若在21:25才下达此命令，则隔天才关机

[root@www ~]# shutdown -h +10

系统再过十分钟后自动关机

[root@www ~]# shutdown -r now

系统立刻重新启动

[root@www ~]# shutdown -r +30 'The system will reboot'  

再过三十分钟系统会重新启动，并显示后面的信息给所有在在线的使用者

[root@www ~]# shutdown -k now 'This system will reboot'  

仅发出警告信件的参数！系统并不会关机！
```

```reboot，halt与poweroff```

## Linux共有七种运行等级

run level 0: 关机
run level 3: 纯文本模式
run level 5: 含有图形接口模式
run level 6: 重新启动

## kill Command

kill -9 直接杀死
kill -3|-15 发送信号

Java 接受关闭信号: Runtime.getRuntime().addShutdownHook()

## 日志查看方式

- 正在滚动的日志

```sh
tail -f
```

- 大文件

```sh
less
# shift + f (即 F) 查看滚动
```

- tail 和 less 结合

```sh
tail -n + 10000|less 从第10000行开始使用less 查看
```

- vim 查看

搜索: `/`

## Linux 常用指令

### 性能分析

sar

### 文件/目录管理

|name|explain|useage|
|---|---|---|
|basename|get file name not contain path|basename(选项)(参数)|
|bzip2|create or manage .bz2 file |---|
|bunzip2|unzip .bz2 files|
|bzcat| show files in .bz2 files|
|bzcamp|compare  files in .bz2|
|bzdiff|compare the different  files in .bz2|
|bzdiff|compare the different  files in .bz2|
|chgrp|  Change  the  group of each FILE to GROUP|
|chmod| hanges the file mode bits of each given file according to mode||
|chown| changes the user and/or group ownership of each given file||
|cat| changes the user and/or group ownership of each given file||
|cut| changes the user and/or group ownership of each given file||
|cmp| changes the user and/or group ownership of each given file||
|compress| changes the user and/or group ownership of each given file||
|find| changes the user and/or group ownership of each given file||
|grep| changes the user and/or group ownership of each given file||
|join| changes the user and/or group ownership of each given file||
|ln| changes the user and/or group ownership of each given file||
|sln| changes the user and/or group ownership of each given file||
|less| changes the user and/or group ownership of each given file||
|look| changes the user and/or group ownership of each given file||
|move| changes the user and/or group ownership of each given file||
|more| changes the user and/or group ownership of each given file||
|rename| changes the user and/or group ownership of each given file||
|rmdir| changes the user and/or group ownership of each given file||
|sed| changes the user and/or group ownership of each given file||
|touch| changes the user and/or group ownership of each given file||
|unexpand| changes the user and/or group ownership of each given file||
|tail| changes the user and/or group ownership of each given file||
|head| changes the user and/or group ownership of each given file||
|whereis| changes the user and/or group ownership of each given file||
|pushd| changes the user and/or group ownership of each given file|浏览过的目录记录,并切换 `pushd +1`|
|popd| changes the user and/or group ownership of each given file|浏览过的目录记录,并切换|
|dirs| 显示目录堆找  |---|
|locate| 在保存文档和目录名称的数据库内，查找合乎范本样式条件的文档或目录.   |---|

### Shell

|name|explain|useage|
|---|---|---|
|alias|  设置命令别名 |---|
|unalias|  取消命令别名 |---|
|bg|  后台执行作业 |---|
|fg|  将后台作业放到前台执行 |---|
|fc|  修改历史命令并执行 |---|
|bind| 显示内部命令的帮助信息  |---|
|builtin|执行shell内部命令   |---|
|command| 调用指定的指令执行  |---|
|declare|  声明shell变量 |---|
|echo  |  打印变量或字符串  |---|
|evn|  在定义的环境中执行指令 |---|
|export |将变量输出为环境变量 |---|
|enable|  激活或关闭内部命令 |---|
|exec|  调用并执行指令 |---|
|history|  显示历史命令 |---|
|kill|  杀死进程 |---|
|logout|  退出登录 |---|
|read|  从键盘读取变量值 |---|
|readonly|  定义只读shell变量或函数 |---|
|set|显示或设置shell特性及shell变量|---|
|unset|取消显示或设置shell特性及shell变量|---|
|shopt|显示和设置shell行为选项|---|
|type|判断内部指令和外部指令|---|
|umask|设置权限掩码|---|
|wait|等待进程执行完后返回终端|---|
|sleep|暂停指定的时间|---|
|wall|向所有终端发送信息|---|
|write|向所有终端发送信息|---|
|wall|向指定用户终端发送信息|---|
|yes|重复打印字符串直到被系死|---|
|whatis|显示某个命令的简单描述信息|---|

### 系统管理

|name|explain|useage|
|---|---|---|
|ctrlaltdel| |不通发行版,硬件设置是不一样的|
|chpasswd|以批处理模式更改密码|---|
|chroot|在指定时间执行任务|---|
|batch|在指定时间执行任务|---|
|chroot|在指定时间执行任务|---|
|depmod|产生模块依赖于映射文件|---|
|free|显示内存使用|---|
|fuser|包括进程使用的文件或套接字|---|
|groupadd|包括进程使用的文件或套接字|---|
|groupdel|包括进程使用的文件或套接字|---|
|groupmod|修改工作组信息|---|
|gpasswd|工作组文件管理工具|---|
|grpck|验证组文件的完整性|---|
|grpconv|创建组密码文件|---|
|grpunconv|还原组密码到group文件|---|
|halt|关闭当前计算机|shutdown|
|poweroff|关闭当前计算机并切断电源|shutdown|
|reboot|重新启动计算机|shutdown|
|hostid|打印当前主机数字标识| |
|init|初始化linux 进程| |
|ipcs|报告进程间通信设施状态 | |
|iostat|报告CPU状态和设备及分区的ID状态 | |
|mpstat|报告CPU状态| |
|insmod|加载模块到内核 | |
|rmmod|从内核移除模块 | |
|killall|按照名称杀死进程 | |
|pkill|按照名称杀死进程 | |
|kexec|直接启动另一Linux内核 | |
|lsmod|显示所有已打开文件列表 | |
|last|显示用户最近登录列表 | |
|lastb|显示错误登录列表 | |
|lastlog|显示用户最近一次登录信息 | |
|logsave|将指令自息保存到日志 | |
|logwatch|分析报告系统日志 | |
|logrotate|日志轮转工具 | |
|newusers|批处理创建用户 | |
|nologin|日志礼貌的拒绝用户登求轮转工具 | |
|nice|以指定优先级运行程序 | |
|renice|  | |
|nohup|以略挂起信号方式运行相序 | |
|passwd|设置用户密码 | |
|ps|  | |
|pstree| 以树形显示进程派生关系  | |
|pgrep| 基于名称查找进程 | |
|top|   | |
|time |  统计指令运行时间 | |
|useradd| 创建新用户|
|userdel| 删除用户及相关文件|
|usermod| 修改用户|
|watch |全屏方式显示周期性执行的指令|
|w     |显示已登录用户正在执行的指令|

### 工具

|name|explain|useage|
|---|---|---|
|bc| 任意精度的计算器语言| |
|clear|清屏指令 | |
|consoletype|打印已连接的终端类型 | |
|date|显示与设置系统日期时间 | |
|dircolors|ls指令显示颜色设置 | |
|mdSsum|  sha/md5 | |
|mesg|控制终端是否可写 | |
|dircolors|ls指令显示颜色设置 | |

### 硬件

|name|explain|useage|
|---|---|---|
|badblocks| 查找磁盘坏块| |
|blockdev|命令行中调用磁盘的ioctl | |
|df |报告磁盘空间使用情况 | |
|lsusb|显示USB设备列表 | |
|lspci|显示PCI设备列表 | |
|mdSsum|  sha/md5 | |
|mesg|控制终端是否可写 | |
|dircolors|ls指令显示颜色设置 | |

```sh
du # 计算出单个文件或者文件夹的磁盘空间占用.
sort # 对文件行或者标准输出行记录排序后输出.
head # 输出文件内容的前面部分.
```

du:
-a: 显示目录占用空间的大小，还要显示其下目录占用空间的大小

sort:
-n  : 按照字符串表示的数字值来排序  
-r : 按照反序排列

head :  
-n : 取出前多少行  

```sh
du -a | sort -n -r | head -n 1
```

### 网络管理相关指令

|name|explain|useage|
|---|---|---|
| traceroute|  追踪router| |

## where the applictaion for command

```sh
strace
```

## 锁 `PID` 文件

`flock` 命令锁`pid` ,

<https://linux.die.net/man/1/flock>

GTG: 锁文件, 文件上锁, 防止修改

## fork 炸弹

通过不断的生成进程,占用资源.

Jaromil所作的在类`UNIX`系统的`shell`环境下触发`for`k炸弹的脚本代码

```shell
:(){ :|:& };:
```

解析:

```sh
:()      # 定义函数,函数名为":",即每当输入":"时就会自动调用{}内代码
  {      
    :    # 用递归方式调用":"函数本身
    |    # 並用管線(pipe)將其輸出引至...（因为有一个管線操作字元，因此會生成一個新的進程）
    :    # 另一次递归调用的":"函数
         # 综上,":|:"表示的即是每次調用函数":"的時候就會產生兩份拷貝
    &    # 在后台开启新进程运行
  };
:        # 调用":"函数,"引爆"fork炸弹
```

另一种形式:

```sh
bomb()
  {
    bomb|bomb&
  };
bomb
```

### 预防

由于fork炸弹透过不断的开新进程来瘫痪系统，一个防止其严重影响系统的方法就是限定一个用户能够创建的进程数的上限.

在Linux系统上，可以透过`ulimit`这个指令达到相应的效果，

`ulimit -Hu 30`

这个指令可以限制每一个用户最多只能创建`30`个进程.

还可以通过修改配置文件`/etc/security/limits.conf`来限制可生成的最大进程数来避开这枚炸弹。

## Diplayport

修复 DP 接口不能输出的问题

```bash
yay -Syu lightdm
```

<https://askubuntu.com/a/1134981>

注：当时记得是这么做的，但是记录的时候发现并没有 安装 `lightdm`

## Wayland

TGA: GDM slow, GDM blank, GDM can't work.

其它按照文档处理。

一定要屏蔽 `nvidiafb`，加在grub 启动项里 `module_blacklist=nvidiafb`

这个方案也可以解决某些情况 `GDM` 不能使用的问题, 似乎是因为 `GDM` 使用了 `Wayland`, 在解决 `Diplayport` 后 也会触发这个问题.

### 缓慢

但是也没太大效果

```sh
yay -Rsc xdg-desktop-portal-gnome
```

## 休眠

<https://wiki.archlinux.org/title/Power_management/Suspend_and_hibernate>
grub 添加 `resume=UUID=swap_device_uuid`

按照文档配置`/etc/mkinitcpio.conf.` `resume` 必须添加

```conf
MODULES=(nvidia nvidia_modeset nvidia_uvm nvidia_drm)

HOOKS=(base udev resume autodetect modconf  keyboard keymap consolefont block filesystems fsck)
```

AMD 特别注意 [PC will not wake from sleep on A520I and B550I motherboards](https://wiki.archlinux.org/title/Power_management#PC_will_not_wake_from_sleep_on_A520I_and_B550I_motherboards) ，而且这篇文档和上一篇地址不一样。

## Grep 替换文本内容

不要用， 用`sed`

- 使用grep 搜索

```sh
grep 'keyword' log.txt -A 10 列出包括匹配行之后的10行
grep 'keyword' log.txt -B 10 列出包括匹配行之前的10行
grep 'keyword' log.txt -C 10 列出包括匹配行前后的10行
```

## | 与 |&

|-|-|
|---|---|
|\|| 只接收标准输出，不接收错误|
|\|&| 接收所有输出|

## 重映射目录,沙箱环境

tag: 安全, QQ防护

```bash
yay -Syu bubblewrap
```

```bash
bwrap --unshare-all --ro-bind /
```

## chroot

chroot namespace

## Gnome命令行设置壁纸

```sh
gsettings set org.gnome.desktop.background picture-uri 'file:///home/ztftrue/Pictures/wallpaper/99.jpg'
```

## Grub丢失

更新 `bois` 后 `Grub` 丢失.

Tag: bois grub 消失

<https://askubuntu.com/questions/1036980/grub-does-not-load-after-bios-update#:~:text=If%20in%20the%20Grub%20menu%20you%20could%20not,Grub%20menu%20there%20will%20be%20Windows%20boot%20loader>.

Log-in to Windows using your Administrative account.

Right click on windows icon and select command prompt(admin).
Run this from the command prompt:

it seems bcdedit accept anything, you have to mount the volume mountvol `P: /S` then list all directories using dir `P:\EFI` and make sure grubx64.efi exists in the selected folder. change the command to `bcdedit /set {bootmgr} path \EFI\neon\grubx64.efi` because the ubuntu folder doesn't have the `grubx64.efi`

```bash
mountvol P: /S
dir P:\EFI
# 注意文件是否存在
bcdedit /set {bootmgr} path \EFI\ubuntu\grubx64.efi
```

`\EFI\ubuntu\grubx64.efi` 就是文件的真实路径

## MMAP

`mmap` is a powerful system call in Linux used to map files or devices into memory. It allows a process to access files and devices as if they were part of its own memory space, which can lead to more efficient file I/O operations and memory usage.

`mmap`的基本原理是在用户空间和内核空间之间建立一个直接映射，使得文件或设备可以像内存一样被访问。这种映射通过页表（page tables）来实现，页表将用户空间地址与物理内存地址进行关联。

进程可以采用指针的方式读写操作这一段内存，而系统会自动回写脏页面到对应的文件磁盘上，即完成了对文件的操作而不必再调用read,write等系统调用函数。相反，内核空间对这段区域的修改也直接反映用户空间，从而可以实现不同进程间的文件共享。

以下是`mmap`的工作原理的详细解释：

### 1. 文件与内存的映射

当调用`mmap`时，操作系统会执行以下步骤：

1. **检查参数**：验证传递的参数是否有效，包括文件描述符、映射长度、保护标志等。
2. **确定映射区域**：如果用户没有指定特定的地址（即`addr`参数为`NULL`），操作系统会选择一个合适的地址进行映射。
3. **更新页表**：操作系统将文件或设备的物理地址与用户空间的虚拟地址进行关联，并在页表中记录这些映射关系。
4. **处理缺页异常**：当进程访问映射区域时，如果相应的物理页尚未加载，CPU会产生缺页异常，操作系统捕捉到该异常后会将所需的页加载到内存。

### 2. 页面保护和共享

- **页面保护（Protection）**：可以使用不同的保护标志来控制映射区域的访问权限，例如`PROT_READ`（可读）、`PROT_WRITE`（可写）、`PROT_EXEC`（可执行）等。操作系统会根据这些标志来设置页表中的权限位，确保访问控制。
  
- **共享映射与私有映射**：通过`MAP_SHARED`和`MAP_PRIVATE`标志，可以控制映射区域的共享属性。
  - **共享映射（MAP_SHARED）**：多个进程可以共享同一个映射区域，对该区域的修改会反映到实际文件或设备中。
  - **私有映射（MAP_PRIVATE）**：每个进程都有自己的副本，对该区域的修改不会影响实际文件或其他进程。

### 3. 性能优化

- **减少I/O操作**：传统的文件读写操作需要在用户空间和内核空间之间复制数据，而`mmap`则避免了这种数据复制，因为文件内容直接映射到用户空间。
- **按需加载**：`mmap`通过按需加载页面（demand paging）的方式，仅在实际访问时才将文件内容加载到内存，这样可以减少不必要的内存占用。
 
```c
 #include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <file>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    const char *filename = argv[1];
    int fd = open(filename, O_RDONLY);
    if (fd == -1) {
        perror("open");
        exit(EXIT_FAILURE);
    }

    struct stat sb;
    if (fstat(fd, &sb) == -1) {
        perror("fstat");
        exit(EXIT_FAILURE);
    }

    size_t length = sb.st_size;
    // mmap函数创建一个新的vm_area_struct结构，并将其与文件的物理磁盘地址相连.
    char *addr = mmap(NULL, length, PROT_READ, MAP_PRIVATE, fd, 0);
    if (addr == MAP_FAILED) {
        perror("mmap");
        exit(EXIT_FAILURE);
    }

    write(STDOUT_FILENO, addr, length);

    if (munmap(addr, length) == -1) {
        perror("munmap");
        exit(EXIT_FAILURE);
    }

    close(fd);
    return 0;
}
```
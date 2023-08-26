#

## 关闭驱动验证

打开组策略,`gpedit.msc`

计算机配置——用户配置——管理模板--系统——驱动程序安装——设备驱动程序的代码签名

选择 已启用——忽略——确定

重启

## win UEFI 分区修复方法

需要windows 启动盘

### 启动到终端模式

在终端(命令提示符)输入 `diskpart` 运行磁盘程序.

给`系统分区`分配`磁盘符号`:

1. `list disk` 查看磁盘列表
2. `select disk [num]` 选择磁盘, 选择系统所在磁盘
3. `list partition` 查看选定的磁盘分区
4. `select partition [num]` 选择分区, 选择系统所在分区
5. `assign letter = [letter]` 挂载选定的分区到目标字母下
    特别注意: 发现不能分配字母`C`, 必须从 `A` 开始分配
    移除分配的字母可以用`remove letter = [letter]`

按照同样的方法给 `UEFI` 分区分配盘符

然后, 输入`exit`退出`diskpart`程序

复制 Windows 分区的 EFI 文件到目标 EFI 分区:

```bat
bcdboot A:\windows /s B: /f uefi /l zh-cn
```

重启.

## u盘制作系统后不能格式化

用U 盘 制作了一个镜像, 后来一直不能格式化, 即使我在`linux`下格式化了, 到win还是不能格式化.

坑真多. 后来查了一下, 发现要`clean` 磁盘, 在cmd 中 输入 diskpart

```bat
list disk
select disk u盘号
clean
exit
```

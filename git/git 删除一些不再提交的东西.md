#

## 方法一

这里以删除 .setting 文件夹为案例

```sh
git rm -r --cached  .setting #--cached不会把本地的.setting删除
git commit -m 'delete .setting dir'
git push -u origin master
```

### 方法二
  
直接修改.gitignore文件,将不需要的文件过滤掉，然后执行命令

```sh
git rm -r --cached .
git add .
git commit
git push  -u origin master
```

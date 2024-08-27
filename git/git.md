#

在`~/.gitconfig`添加

```con
[commit]
  template = ~/.gitmessage
```

新建.gitmessage 文件(在`~/`)

添加内容:

```con
type:#  feat ,fix ,docs : subject:
description:
```

## Git提交规范

```xml
<type>(必需)(<scope>): <subject>(必需) #header
// 空一行
<body> # 详细信息
// 空一行
<footer> 
```

### type

refere: <https://github.com/angular/angular/blob/master/CONTRIBUTING.md#commit-header>

用于说明本次commit的类别，只允许使用下面7个标识

- feat: 新功能(feature)
- fix: 修补bug
- docs: 文档(documentation)
- style:  格式(不影响代码运行的变动)
- refactor: 重构(即不是新增功能，也不是修改bug 的代码变动)
- test: 增加测试
- chore: 构建过程或辅助工具的变动
- perf: 性能

### scope

用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同.

### subject

是 commit 目的的简短描述，不超过50个字符.

## When to rebase? When to Merge?

如果要查看与发生的历史完全相同的历史记录，则应使用合并. Merge保留历史记录，而rebase重写历史记录

rebase更好地简化复杂的历史记录，您可以通过更改提交历史记录. 您可以删除不需要的提交，将两个或多个提交压缩为一个或编辑提交消息. Rebase将一次呈现一个提交的冲突，而merge将同时呈现它们.

## git merge和git merge --no-ff

<https://stackoverflow.com/questions/9069061/what-is-the-difference-between-git-merge-and-git-merge-no-ff>

<https://blog.developer.atlassian.com/pull-request-merge-strategies-the-great-debate/>

fast-forward
带上另外一个分支的信息, 如果另外一个分支删，则会丢失分支信息。
和rebase 相同, 不同点在于, ff 合并，当前分支的 HEAD 必须是您要合并的提交的祖先

–squash
把一些不必要commit进行压缩

–no-ff

关闭fast-forward模式，在提交的时候，会创建一个merge的commit信息(丢弃合并分支的信息,需要单独提交一个信息)，然后合并的和master分支

## git hook

Git 能在特定的重要动作发生时触发自定义脚本。
客户端的和服务器端都可以执行自定义脚本。

钩子都被存储在项目中的 .git/hooks ，默认存在的都是示例，其名字都是以 `.sample` 结尾，移除`.sample`后缀, 即可激活该钩子脚本. 当然自定义的脚本也要防在这里.

客户端和服务器都可以执行钩子.

如果放在客户端可以自定义编译脚本拉取`hook`.

## gir 删除提交历史记录 ,并且减小pack

<https://stackoverflow.com/questions/13716658/how-to-delete-all-commit-history-in-github>

<https://stackoverflow.com/questions/11050265/remove-large-pack-file-created-by-git>

```sh
git checkout --orphan latest_branch
git add -A
git commit -am "commit message"
git branch -D main
git branch -m main
git push -f origin main
# clane package
git filter-branch --index-filter 'git rm -r --cached --ignore-unmatch unwanted_filename_or_folder' --prune-empty
# git reflog expire --expire-unreachable=all --all
```

## git 删除一些不再提交的东西

### 方法一

这里以删除 .setting 文件夹为案例

```sh
git rm -r --cached  .setting #--cached不会把本地的.setting删除
git commit -m 'delete .setting dir'
git push -u origin master
```

### 方法二

如果误提交的文件夹比较多，方法一也较繁琐.  
直接修改.gitignore文件,将不需要的文件过滤掉，然后执行命令

```sh
git rm -r --cached .
git add .
git commit
git push  -u origin master
```

## git commit 加上 GPG 签名

<https://docs.github.com/en/authentication/managing-commit-signature-verification/about-commit-signature-verification>

记得以前有一个人用Linus 的名义(username)提交linux代码.

只在github 或者邮箱登录的站点上有用

生成 GPG Key

```bash
gpg --gen-key
```

必须选择rsa,4096

这里的邮箱必须时github 登录(认证)邮箱, 其它信息你看着办.

结果：

```bash
pub   rsa4096 2022-12-05 [SC]
      2****************************7
uid                      ztftrue <ztftrue@gmail.com>
sub   rsa4096 2022-12-05 [E]
```

列出GPG key

```bash
 gpg --list-secret-keys --keyid-format=long
#-----------------------------
sec   rsa4096/GPGKEY 2022-12-05 [SC]
      2****************************7
uid                 [ultimate] ztftrue <ztftrue@gmail.com>
ssb   rsa4096/C5E2E89A60BF185E 2022-12-05 [E]

```

然后导出

```bash
gpg --armor --export GPGKEY

# -----------------
-----BEGIN PGP PUBLIC KEY BLOCK-----
公钥
-----END PGP PUBLIC KEY BLOCK-----
```

然后把GPG key 公钥 导入github。

配置git

```bash
git config --global user.signingkey 2****7
git config --global commit.gpgsign true
```

提交代码，点击对应的提交记录，就能看道效果了。

## ~~同步一个 fork~~ (现在会自动同步)

搜索 fork sync，就可以看到 GitHub 自己的帮助文档 Syncing a fork 点进去看这篇的时候，注意到有一个 Tip: Before you can sync your fork with an upstream repository, you must configure a remote that points to the upstream repository in Git.

### Configuring a remote for a fork

给 fork 配置一个 remote
使用 git remote -v 查看远程状态.

```sh
git remote -v
# origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
# origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
```

添加一个将被同步给 fork 远程的上游仓库

```sh
git remote add upstream https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git
```

再次查看状态确认是否配置成功.

```sh
git remote -v
# origin    https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
# origin    https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
# upstream  https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git (fetch)
# upstream  https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git (push)
```

Syncing a fork
从上游仓库 fetch 分支和提交点，传送到本地，并会被存储在一个本地分支 upstream/master

```sh
git fetch upstream
# remote: Counting objects: 75, done.
# remote: Compressing objects: 100% (53/53), done.
# remote: Total 62 (delta 27), reused 44 (delta 9)
# Unpacking objects: 100% (62/62), done.
# From https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY
#  * [new branch]      master     -> upstream/master
```

切换到本地主分支(如果不在的话)

```sh
git checkout master
git checkout master
# Switched to branch 'master'
```

把 upstream/master 分支合并到本地 master 上，这样就完成了同步，并且不会丢掉本地修改的内容.

```sh
git merge upstream/master
git merge upstream/master
# Updating a422352..5fdff0f
# Fast-forward
#  README                    |    9 -------
#  README.md                 |    7 ++++++
#  2 files changed, 7 insertions(+), 9 deletions(-)
#  delete mode 100644 README
#  create mode 100644 README.md
```

如果想更新到 GitHub 的 fork 上，直接 git push origin master 就好了.

## git dubmodule

Often a code repository will depend upon external code.


<https://www.atlassian.com/git/tutorials/git-submodule>

```sh
git submodule add https://bitbucket.org/jaredw/awesomelibrary

```

 will add file `.gitmodules` and `awesomelibrary` directory

```sh
git submodule init
git submodule update
# git submodule update --init
```

Change or pointed submodule commit:

```sh
cd <path_to_submodule>
git checkout <branch_name>
git reset --hard <commit_hash>
```

Then, run `git add .`, `git commit -m "Your message"`. Otherwise, shen you excute git submodule update, the submodule will reset.

## git flow

[git flow atlassian](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)

## git cherry-pick

[git cherry-pick atlassian](https://www.atlassian.com/git/tutorials/cherry-pick)

[stackoverflow](https://stackoverflow.com/a/57966432)

## git -C

The git -C command is a shorthand way to specify a directory in which you want to run a Git command. It allows you to execute Git commands in a specific directory without having to navigate to that directory first.

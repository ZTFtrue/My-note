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

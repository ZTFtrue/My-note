#

## Code Review

## 单元测试

## Git提交规范

```xml
<type>(必需)(<scope>): <subject>(必需) #header
// 空一行
<body> # 详细信息
// 空一行
<footer> 
```

### type

用于说明本次commit的类别，只允许使用下面7个标识

- feat: 新功能(feature)
- fix: 修补bug
- docs: 文档(documentation)
- style:  格式(不影响代码运行的变动)
- refactor: 重构(即不是新增功能，也不是修改bug 的代码变动)
- test: 增加测试
- chore: 构建过程或辅助工具的变动

### scope

用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同.

### subject

是 commit 目的的简短描述，不超过50个字符.

### 监控服务

## 灰度测试

正式发布前，给一部分人试用，逐步扩大试用人群，以便及时发现和纠正其中的问题.
 灰度期: 灰度测试开始到结束期间的这一段时间，称为灰度期.

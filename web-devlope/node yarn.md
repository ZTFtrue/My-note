#

## npm

npm ci  干净,重新install

npm publish --access public

npm login

## yarn

`yarn install` 执行的操作

1. 将依赖包的版本区间解析为某个具体的版本号
2. 下载对应版本依赖的 tar 包到本地离线镜像
3. 将依赖从离线镜像解压到本地缓存
4. 将依赖从缓存拷贝到当前目录的 node_modules 目录

### PnP 特性

开启 PnP 后, Yarn 不执行第三步 , 而是维护一张静态映射表. 包含以下信息：

- 当前依赖树中包含了哪些依赖包的哪些版本
- 这些依赖包是如何互相关联的
- 这些依赖包在文件系统中的具体位置
- 创建 `.pnp.js` , 记录该依赖在缓存中的具体位置

这样就避免了大量的 I/O 操作, 也不会有 node_modules 目录生成。

同时 `.pnp.js` 还包含了一个特殊的 resolver，Yarn 会利用这个特殊的 resolver 来处理 `require()` 请求，该 `resolver` 会根据 .pnp.js 文件中包含的静态映射表直接确定依赖在文件系统中的具体位置，从而避免了现有实现在处理依赖引用时的 I/O 操作。

#### 使用

```sh
# 新项目
npx create-react-app testapp --use-pnp 
# 已有项目中开启 PnP
yarn --pnp

# 运行
yarn run
# 或
yarn node

#将某个指定依赖拷贝到项目中的 .pnp/unplugged 目录下, 在这个目录下可以修改库文件,用来调试
yarn unplug packageName
# 移除本地 .pnp/unplugged 中的对应依赖
yarn unplug --clear packageName

```


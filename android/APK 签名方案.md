
# APK 签名方案

可以同时使用 v1 和 v2

## v1

### MANIFEST.MF

该文件中保存的内容就是把APK 所有的文件(没有文件夹), 用 SHA256 消息摘要算法提取出该文件的摘要然后进行 BASE64 编码后, 作为 SHA-256-Digest 属性的值写入到 MANIFEST.MF 文件中的一个块中. 该块有一个「Name」属性,  其值就是该文件在 APK 包中的路径.

### CERT.SF

SHA1-Digest-Manifest: 对MANIFEST.MF(也就是上边的那个文件)整改个文件做校验文件做SHA256后再用 Base64 编码

SHA-256-Digest: 对 MANIFEST.MF 的各个条目做 SHA256 后再用 Base64 编码

### CERT.RSA

把 CERT.SF 文件用私钥(RSA)计算出签名, 然后将签名以及包含 __公钥__ 信息的数字证书一同写入 CERT.RSA 中保存.

### CA 颁发私钥证书

APK 也可以采用由 CA 颁发私钥证书进行签名. 采用非自签名时, 最终 APK 的公钥证书中就会包含证书链, 并且会存在多余一个证书, 证书间通过 Issuer 与 Subject进行关联, Issuer 负责对 Subject 进行认证. 当安装 APK 时, 系统只会用位于证书链 中最底层的证书对 APK 进行校验, 但并不会验证证书链的有效性.

------------------------------

### 缺陷

签名校验速度慢

校验过程中需要对apk中所有文件进行摘要计算, 在 APK 资源很多、性能较差的机器上签名校验会花费较长时间, 导致安装速度慢.

完整性保障不够

META-INF 目录用来存放签名, 自然此目录本身是不计入签名校验过程的, 可以随意在这个目录中添加文件.

## v2 (7.0 引入)

将 APK 中文件 ZIP 条目的内容、ZIP 中央目录、ZIP 中央目录结尾按照 1MB 大小分割成一些小块.

计算每个小块的数据摘要, 数据内容是 0xa5 + 块字节长度 + 块内容.

计算整体的数据摘要, 数据内容是 0x5a + 数据块的数量 + 每个数据块的摘要内容

然后将 APK 的摘要 + 数字证书 + 其他属性生成签名数据写入到 APK Signing Block 区块.

v2 签名将验证归档中的所有字节, 而不是单个 ZIP 条目, 因此, 在签署后无法再运行 ZIPalign(必须在签名之前执行). 正因如此, 现在, 在编译过程中, Google 将压缩、调整和签署合并成一步完成.

v2 签名模式在原先 APK 块中增加了一个新的块(签名块), 新的块存储了签名, 摘要, 签名算法, 证书链, 额外属性等信息.

<https://stackoverflow.com/questions/42648499/difference-between-signature-versions-v1-jar-signature-and-v2-full-apk-sign#:~:text=In%20Android%207.0%2C%20APKs%20can,and%20only%20verify%20v1%20signatures.&text=APK%20Signature%20Scheme%20v2%20offers,Faster%20app%20install%20times>

<https://source.android.com/security/apksigning/v2#verification>

## v3 (9.0 引入)

格式大体和 v2 类似, 在 v2 插入的签名块(Apk Signature Block v2)中, 又添加了一个新快(Attr块).

在这个新块中, 会记录我们之前的签名信息以及新的签名信息(告诉原来的APK, 知道旧的密钥), 以密钥转轮的方案, 来做签名的替换和升级.

v3 签名新增的新块(attr)存储了所有的签名信息, 由更小的 Level 块, 以链表的形式存储.

其中每个节点都包含用于为之前版本的应用签名的签名证书, 最旧的签名证书对应根节点, 系统会让每个节点中的证书为列表中下一个证书签名, 从而为每个新密钥提供证据来证明它应该像旧密钥一样可信.

## 查看密钥

```sh
keytool -list -v -keystore 密钥
```

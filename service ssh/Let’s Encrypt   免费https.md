# Let’s Encrypt   免费https

## 安装配置

* 下载.

```sh
git clone https://github.com/certbot/certbot.git
```

或者用

```sh
wget ‘下载路径’\n$ unzip  '下载的文件名'
```

* 进入工作目录.

```sh
cd certbot
# 生成证书，两个命令选择其中一个即可，第一个在某些情况会有错误，选择第二个要注意有效期
# 使用-d追加多个域名
./certbot-auto certonly --standalone --email ztftrue@gmail.com -d ztftrue.com -d www.ztftrue.com
./certbot-auto certonly --standalone --email ztftrue@gmail.com -d ztftrue.com -d www.ztftrue.com --debug
```

> 如本站域名为ztftrue.com. 证书文件位置 /etc/letsencrypt/live/example.com

```sh
# ls /etc/letsencrypt/live/
# cd /etc/letsencrypt/live/ztftrue.com && ls
```

## Spring Booot 特殊处理

  [spring boot要求的正书为pkcs12 格式,要转格式，执行命令时要求输入密码和邮箱](https://stackoverflow.com/questions/36991562/how-can-i-set-up-a-letsencrypt-ssl-certificate-and-use-it-in-a-spring-boot-appli)

```sh
  openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root
 ```

   keystore.p12 为你的文件名 tomact 是别名napplication.properties配置  443 为https端口   80为http端口

```sh
   server.port: 443 security.require-ssl=true
   server.ssl.key-store:/etc/letsencrypt/live/example.com/keystore.p12  
   server.ssl.key-store-password: 你的密码
   server.ssl.keyStoreType: PKCS12
   server.ssl.keyAlias: tomcat
```

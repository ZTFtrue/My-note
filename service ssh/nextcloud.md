# 使用nextcloud搭建自己的云存储(NGINX版)

## 前言

nextcloud 是owncloud的一个分支，[对比参考](https://civihosting.com/blog/nextcloud-vs-owncloud/).
此教程为在Arch Linux平台上搭建的教程，Arch Linux 软件源包含nextcloud. 安装Arch时推荐格式化一个分区作为数据分区，日常使用也推荐将home单独一个分区. 我只是用小主机搭建了可以内网使用的，外网使用需要公共ip，使用**云的不要拍砖(千万不要够买 __orico 奥睿科__ 的任何产品).  
ssh服务启用，更多参考wiki

```sh
pacman -Syu openssh
systemctl start sshd.service
systemctl enable sshd.service
```

## 安装

nextcloud需要的软件为:
Nextcloud requires several components:[1]

* A web server: Apache or nginx
* A database: MariaDB/MySQL, PostgreSQL or Oracle
* PHP with additional modules

这里使用nginx 作为web server, 数据库推荐MariaDB，MariaDB是MySQL的一个变种. php为必须安装，你可以编译安装nextcloud.

执行命令安装相关包:

```sh
pacman -S mariadb php php-intl php-admin php-apache
```

<!-- [Pacman (hook)钩子](https://wiki.archlinux.org/index.php/Nextcloud#Setup)
要在更新时自动升级Nextcloud数据库，需要创建pacman钩子: 

```sh
/etc/pacman.d/hooks/nextcloud.hook
``` -->

<!-- --- -->

<!-- ```sh
[Trigger]
Operation = Install
Operation = Upgrade
Type = Package
Target = nextcloud
Target = nextcloud-app-*
  
[Action]
Description = Update Nextcloud installation
When = PostTransaction
Exec = /usr/bin/runuser -u http -- /usr/bin/php /usr/share/webapps/nextcloud/occ upgrade
``` -->

## 配置mysql/mariadb

mariadb需要初始化目录

```sh
sudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
```

```sh
sudo systemctl enable mariadb.service
sudo systemctl start mariadb.service
```

> systemctl简单介绍，enable 开机自动启动，start 启动.

如果你是新安装mysql需要先创建root用户，然后再创建其它用户和分配权限. 创建用户和分配权限不是必须操作，如果不创建用户和分配权限，它可以方便你和其他人删库跑路.

```sh

mysql -u root -p # 登录

mysql> CREATE DATABASE `nextcloud` DEFAULT CHARACTER SET `utf8` COLLATE `utf8_unicode_ci`;

mysql> CREATE USER `yourname`@'localhost' IDENTIFIED BY 'password';

# 授权允许访问
mysql> GRANT ALL PRIVILEGES ON `nextcloud`.* TO `nextcloud`@`localhost`;

```

## 配置PHP

编辑 __/etc/php/php.ini__

取消注释以下行或内容，删除前边的 #

```ini
extension=gd
iconv
#mysql
extension=pdo_mysql
extension=mysqli
```

## 配置Ngnix

```config
user http;
worker_processes  2;

error_log  /var/log/nginx/error.log;
#error_log  /var/logs/error.log  notice;
#error_log  /var/logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}

http {
    include mime.types;
    default_type application/octet-stream;
    types_hash_max_size 4096;
    upstream php-handler {
      # server 127.0.0.1:9000;
      server unix:/run/php-fpm/php-fpm.sock;
    }

    fastcgi_connect_timeout 120;
    fastcgi_send_timeout 3600;
    fastcgi_read_timeout 3600;
    server {
        listen 80;
        listen [::]:80;
        server_name cloud.ztftrue.com;
        # enforce https
        return 301 https://$server_name:443$request_uri;
        access_log off;
    }

    server {
    listen 443 ssl http2;
    listen [::]:443 ssl http2;
    server_name cloud.ztftrue.com;

    # Use Mozilla's guidelines for SSL/TLS settings
    # https://mozilla.github.io/server-side-tls/ssl-config-generator/
    # NOTE: some settings below might be redundant
    ssl_certificate /home/ztftrue/pem/fullchain.pem;
    ssl_certificate_key /home/ztftrue/pem/privkey.pem;

    # Add headers to serve security related headers
    # Before enabling Strict-Transport-Security headers please read into this
    # topic first.
    add_header Strict-Transport-Security "max-age=15768000; includeSubDomains; preload;" always;
    #
    # WARNING: Only add the preload option once you read about
    # the consequences in https://hstspreload.org/. This option
    # will add the domain to a hardcoded list that is shipped
    # in all major browsers and getting removed from this list
    # could take several months.
    add_header Referrer-Policy "no-referrer" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-Download-Options "noopen" always;
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Permitted-Cross-Domain-Policies "none" always;
    add_header X-Robots-Tag "none" always;
    add_header X-XSS-Protection "1; mode=block" always;

    # Remove X-Powered-By, which is an information leak
    fastcgi_hide_header X-Powered-By;

    # Path to the root of your installation
    root /var/www/nextcloud;

    location = /robots.txt {
        allow all;
        log_not_found off;
        access_log off;
    }

    # The following 2 rules are only needed for the user_webfinger app.
    # Uncomment it if you're planning to use this app.
    #rewrite ^/.well-known/host-meta /public.php?service=host-meta last;
    #rewrite ^/.well-known/host-meta.json /public.php?service=host-meta-json last;

    # The following rule is only needed for the Social app.
    # Uncomment it if you're planning to use this app.
    #rewrite ^/.well-known/webfinger /public.php?service=webfinger last;

    location = /.well-known/carddav {
      return 301 $scheme://$host:$server_port/remote.php/dav;
    }
    location = /.well-known/caldav {
      return 301 $scheme://$host:$server_port/remote.php/dav;
    }

    # set max upload size
    client_max_body_size 512M;
    fastcgi_buffers 64 4K;

    # Enable gzip but do not remove ETag headers
    gzip on;
    gzip_vary on;
    gzip_comp_level 4;
    gzip_min_length 256;
    gzip_proxied expired no-cache no-store private no_last_modified no_etag auth;
    gzip_types application/atom+xml application/javascript application/json application/ld+json application/manifest+json application/rss+xml application/vnd.geo+json application/vnd.ms-fontobject application/x-font-ttf application/x-web-app-manifest+json application/xhtml+xml application/xml font/opentype image/bmp image/svg+xml image/x-icon text/cache-manifest text/css text/plain text/vcard text/vnd.rim.location.xloc text/vtt text/x-component text/x-cross-domain-policy;

    # Uncomment if your server is build with the ngx_pagespeed module
    # This module is currently not supported.
    #pagespeed off;

    location / {
        rewrite ^ /index.php;
    }

    location ~ ^\/(?:build|tests|config|lib|3rdparty|templates|data)\/ {
        deny all;
    }
    location ~ ^\/(?:\.|autotest|occ|issue|indie|db_|console) {
        deny all;
    }

    location ~ ^\/(?:index|remote|public|cron|core\/ajax\/update|status|ocs\/v[12]|updater\/.+|oc[ms]-provider\/.+)\.php(?:$|\/) {
        fastcgi_split_path_info ^(.+?\.php)(\/.*|)$;
        set $path_info $fastcgi_path_info;
        try_files $fastcgi_script_name =404;
        include fastcgi_params;
        fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
        fastcgi_param PATH_INFO $path_info;
        fastcgi_param HTTPS on;
        # Avoid sending the security headers twice
        fastcgi_param modHeadersAvailable true;
        # Enable pretty urls
        fastcgi_param front_controller_active true;
        fastcgi_pass php-handler;
        fastcgi_intercept_errors on;
        fastcgi_request_buffering off;
    }

    location ~ ^\/(?:updater|oc[ms]-provider)(?:$|\/) {
        try_files $uri/ =404;
        index index.php;
    }

    # Adding the cache control header for js, css and map files
    # Make sure it is BELOW the PHP block
    location ~ \.(?:css|js|woff2?|svg|gif|map)$ {
        try_files $uri /index.php$request_uri;
        add_header Cache-Control "public, max-age=15778463";
        # Add headers to serve security related headers (It is intended to
        # have those duplicated to the ones above)
        # Before enabling Strict-Transport-Security headers please read into
        # this topic first.
        add_header Strict-Transport-Security "max-age=15768000; includeSubDomains; preload;" always;
        #
        # WARNING: Only add the preload option once you read about
        # the consequences in https://hstspreload.org/. This option
        # will add the domain to a hardcoded list that is shipped
        # in all major browsers and getting removed from this list
        # could take several months.
        add_header Referrer-Policy "no-referrer" always;
        add_header X-Content-Type-Options "nosniff" always;
        add_header X-Download-Options "noopen" always;
        add_header X-Frame-Options "SAMEORIGIN" always;
        add_header X-Permitted-Cross-Domain-Policies "none" always;
        add_header X-Robots-Tag "none" always;
        add_header X-XSS-Protection "1; mode=block" always;

        # Optional: Don't log access to assets
        access_log off;
    }

      #location ~ \.(?.css)$ {
     #   add_header  Content-Type    text/css;
    #}
    location ~ \.(?:png|html|ttf|ico|jpg|jpeg|bcmap)$ {
        try_files $uri /index.php$request_uri;
        # Optional: Don't log access to other assets
        access_log off;
    }
    }

}
```

## 储存目录设置

可以看到有http用户组，这个就是webserver 的用户组，这一点和nextcloud官方文档的www不相同(跟随发行版而变化).

初始化存储目录

```bash
mkdir -p /usr/share/webapps/nextcloud/data
chown http:http /usr/share/webapps/nextcloud/data
chown http:http /usr/share/webapps/nextcloud/apps
chmod 750 /usr/share/webapps/nextcloud/data
chmod 750 /usr/share/webapps/nextcloud/apps
```

创建数据目录，默认是在: /usr/share/webapps/nextcloud/data. 之前说过单独分区，我是把单独分区挂载到home上，所以在home创建文件夹nextcloud.

```sh
mkdir nextcloud
chown http:http /home/nextcloud/
systemctl start httpd.service
systemctl enable httpd.service
```

输入你的服务器IP地址，例如192.168.1.101,可以看到让你配置数据库、用户名、和数据(文件)存放地址,填进去就好了. 你的用户名(管理员)和密码(非明文)存放在/usr/share/webapps/nextcloud/data的php配置中.
> 由于是在安装后一个多月写的此文，所以可能部分细节忘记了,[发起issues](https://github.com/ZTFtrue/blog-file/issues).

## 优化

进入nextcloud 点击头像，设置→管理→ 基本设置有优化提示，按照操作完成.
点击头像→用户，创建用户和管理用户

## PHP7.4 不能访问home目录

编辑 ```/etc/systemd/system/multi-user.target.wants/php-fpm.service```

```sh
sudo vim  /etc/systemd/system/multi-user.target.wants/php-fpm.service
```

设置```ProtectHome=false```

```sh
systemctl daemon-reload
systemctl restart nginx.service
systemctl restart php-fpm.service
```

```sh
sudo chown -R http:http /var/www/nextcloud/*
```

# Security

// 服务器在设置Cookie时可以使用httpOnly，设定了httpOnly的Cookie将不能被JavaScript读取.
// 这个行为由浏览器实现，主流浏览器均支持httpOnly选项，IE从IE6 SP1开始支持.
// 为了确保安全，服务器端在设置Cookie时，应该始终坚持使用httpOnly.

## CSP

[来自MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP)

Example 1:

```text
Content-Security-Policy: policy
Content-Security-Policy: default-src 'self' *.trusted.com
```

Example 2:

```text
Content-Security-Policy: default-src 'self'; img-src *; media-src media1.com media2.com; script-src userscripts.example.com
```

1. 图像可以从任何地方加载(请注意通配符“ *”).
2. 仅允许来自media1.com和media2.com的媒体(不允许来自这些站点的子域).
3. 可执行脚本仅来自`userscripts.example.com`

## 缓存设置

在使用`CDN`, 设置不要缓存用户信息, 不要缓存头部某些信息. 一般只缓存静态文件.

## 404

搜不到内容必须返回`404`代码. 如果返回`200`搜索引擎认为返回的是正常的内容，会记录这个内容。

如果有黑产搜索不存在的内容，搜索引擎记录下后，当其它人搜索站内内容时会出现黑产广告。

## fail2ban

## git 文件泄露

nginx 设置阻止访问以 `.` 开头的文件和一些其它文件

```conf
location ~(\.user\.ini|\.ht|\.git|\.svn|README\.md|\.js\.map){
    deny all;
}
; 这些文件一般包含源代码，开发信息等等
```

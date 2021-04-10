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

# Nginx load css and js files

```
server {
    listen  80;
    server_name sibdomain.domain.com;
    root  /var/www/multiapp/dist;

    location ~* \.(?:css|js|map|jpe?g|gif|png)$ { }

    location / {
        index  index.html index.htm;
        try_files $uri $uri/ /index.html?path=$uri&$args;
    }

    error_page  500 502 503 504  /50x.html;
}
```

## Disable http range

<https://nginx.org/en/docs/http/ngx_http_core_module.html#max_ranges>

```config
location ~ \.mp4$ {
    max_ranges 0;
}
```

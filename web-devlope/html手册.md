#

```html
    <meta charset="UTF-8">
    <meta name="keywords" content="HTML, CSS, XML, XHTML, JavaScript">
    <!-- 为网页定义描述内容: -->
    <meta name="description" content="免费 Web & 编程 教程">
    <!-- 定义网页作者: -->
    <meta name="author" content="Runoob">
    <!-- 每2秒中刷新当前页面: -->
    <meta http-equiv="refresh" content="2">
```

## HTML5 中异步加载脚本

html5 中 script元素具有局部属性: type，src，defer，async，charset.

* type 指定引用或定义的脚本的类型. 对于JavaScript脚本，可以省略此属性.
* src 指定外部脚本文件的URL.
* defer 属性指示浏览器在页面加载和解析之前不执行脚本,和将script元素放在文档的末尾类似. 当浏览器直到DOM元素全部解析完. 才会加载和执行具有defer属性的脚本.

```html
   <script defer src="example.js"></script>
```

* async  浏览器会异步加载和执行脚本，同时继续解析HTML中的其他元素，包括其他脚本元素.
* charset  指定外部脚本文件的字符编码.

> defer,async,charset 只能和有src的属性结合使用.

## link 和import

```html
<!-- 只能加载css 引用的css 会等页面加载完才会加载 -->
<style type="text/css">
@imoprt  url(css_file_url)
</style>
```

link 会同步加载
<https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/rel>
link 属性
|name||
|--|--|
| Alternate | 定义交替出现的链接|
| Stylesheet | 定义一个外部加载的样式表|
| Start | 通知搜索引擎,文档的开始 |
| Next | 记录文档的下一页.(浏览器可以提前加载此页) |
| Prev | 记录文档的上一页.(定义浏览器的后退键) |
| Bookmark | 书签 |

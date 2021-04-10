# 浏览器的工作原理与虚拟DOM

## 浏览器是如何渲染html的

首先渲染引擎从网络层获取文件的内容, 然后解析`html`, 并将各标签(如:`div`,`table`)逐个转化成DOM节点,并将这些节点附加到Document根对象上,这便是DOM 树. 与此同时浏览器也会将`CSS`解析`CSSOM`, 并将其附加到DOM树上, 然后构建渲染树,最后由渲染树处理和显示.如果修改了DOM或CSSOM，则会重新构建DOM树即重排(reflow),重新渲染(Repaint), [发生重排不是必然性的, 有些情况仅需要重绘](https://developers.google.com/speed/docs/insights/browser-reflow).

[关于更多](https://www.html5rocks.com/zh/tutorials/internals/howbrowserswork/),[也可以看看这个(推荐看这个)](https://developers.google.com/web/fundamentals/performance/critical-rendering-path/render-tree-construction)

## 为什么要有虚拟DOM

重排对性能影响非常大, 每次一个节点重排时,都会让整个父节点甚至DOM树重排.

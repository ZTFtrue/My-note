#

## Canvas 工具, 游戏引擎

Canvas: fabricjs,EaselJS

Engine: Babylon.js

## 浏览器的工作原理与虚拟 DOM

### 浏览器是如何渲染 html 的

首先渲染引擎从网络层获取文件的内容, 然后解析`html`, 并将各标签(如:`div`,`table`)逐个转化成 DOM 节点,并将这些节点附加到 Document 根对象上,这便是 DOM 树. 与此同时浏览器也会将`CSS`解析`CSSOM`, 并将其附加到 DOM 树上, 然后构建渲染树,最后由渲染树处理和显示.如果修改了 DOM 或 CSSOM，则会重新构建 DOM 树即重排(reflow),重新渲染(Repaint), [发生重排不是必然性的, 有些情况仅需要重绘](https://developers.google.com/speed/docs/insights/browser-reflow).

[关于更多](https://www.html5rocks.com/zh/tutorials/internals/howbrowserswork/),[也可以看看这个(推荐)](https://developers.google.com/web/fundamentals/performance/critical-rendering-path/render-tree-construction)

### 为什么要有虚拟 DOM

重排对性能影响非常大, 每次一个节点重排时,都会让整个父节点甚至 DOM 树重排.

可以使用 `DocumentFragment` 实现一次性更换节点,避免多次重排重绘

The key difference is due to the fact that the document fragment isn't part of the active document tree structure. Changes made to the fragment don't affect the document.

```js
let languages = ["JS", "TypeScript", "Elm", "Dart", "Scala"];
let langEl = document.querySelector("#language");
let fragment = new DocumentFragment();
languages.forEach((language) => {
  let li = document.createElement("li");
  li.innerHTML = language;
  fragment.appendChild(li);
});
langEl.appendChild(fragment);
```

## Html video 标签 视频 播放

```html
<video
  id="video"
  autoplay
  controls
  disablePictureInPicture // 关闭画中画
  controlsList="nodownload nofullscreen noremoteplayback noplaybackrate" // 取消下载按钮, 这里可以设置多个选项, 用空格隔开.
  disableRemotePlayback
></video>
```

<https://stackoverflow.com/questions/69100753/how-to-disable-html-video-player-playback-speed-three-dots#:~:text=Add%20the%20parameter%20'noplaybackrate'%20to,It%20works%20for%20me.>

<https://stackoverflow.com/questions/17678302/how-to-hide-full-screen-button-of-the-video-tag-in-html5>

```css
video::-webkit-media-controls-fullscreen-button {
  display: none;
}
video::-webkit-media-controls-play-button {
}
video::-webkit-media-controls-timeline {
}
video::-webkit-media-controls-current-time-display {
}
video::-webkit-media-controls-time-remaining-display {
}
video::-webkit-media-controls-mute-button {
}
video::-webkit-media-controls-toggle-closed-captions-button {
}
video::-webkit-media-controls-volume-slider {
}
```

## ??判断运算符

## CSS

### css 自定义属性（有兼容性问题）

<https://developer.mozilla.org/zh-CN/docs/Web/CSS/--*>

带有前缀--的属性名，比如`--example--name`，表示的是带有值的自定义属性，其可以通过 `var` 函数在全文档范围内复用的。

```css
:root {
  --first-color: #488cff;
}

#firstParagraph {
  background-color: var(--first-color);
  color: var(--second-color, black); /* second-color 不存在， 则使用 black */
}
```

<https://developer.mozilla.org/zh-CN/docs/Web/CSS/@property>

@property 规则提供了一个直接在样式表中注册自定义属性的方式，而无需运行任何 JS 代码。
有效的 @property 规则会注册一个自定义属性，就像 CSS.registerProperty (en-US) 函数被使用同样的参数调用了一样。

```css
@property --property-name {
  syntax: "<color>";
  inherits: false;
  initial-value: #c0ffee;
}
@property --property-name {
  syntax: "<number>"; /* 这里定义类型 */
  initial-value: 1;
  inherits: false;
}
```

### CSS 特殊属性

#### mask（有兼容性问题）

允许使用者通过遮罩或者裁切特定区域的图片的方式来隐藏一个元素的部分或者全部可见区域

可以实现各种显示/隐藏特效，比如方块形式显示图片内容

### 颜色控制/灰色/

```css
backdrop-filter: grayscale()
pointer-events: none
mix-blend-mode
```

### clip-path

剪切图片<https://css-tricks.com/clipping-masking-css/>

### mix-blender

### @container 容器查询

### @scroll-timeline

The scroll-timeline-axis CSS property can be used to specify the scrollbar that will be used to provide the timeline for a scroll-timeline animation

指定 `scrollbar` 滚动决定内容

### 伪元素加符号`|`

```css
.cell-title::before {
  content: "";
  display: inline-block;
  background-color: #3674e1;
  width: 4px;
  height: 12px;
  border-radius: 4px;
  margin-right: 4px;
}
```

## Echart

- [牛肉结构](https://echarts.apache.org/examples/zh/editor.html?c=geo-beef-cuts)
- [导览](https://echarts.apache.org/examples/zh/editor.html?c=geo-svg-lines)
- [表盘](https://echarts.apache.org/examples/zh/editor.html?c=gauge-clock)
- [人体组织](https://echarts.apache.org/examples/zh/editor.html?c=geo-organ)

### 双轴间距相等

```js
 let data01 =
        parseInt(getMax(dataSource.data[0] as number[]).toFixed(0)) + 10;
      let data02 =
        parseInt(getMax(dataSource.data[1] as number[]).toFixed(0)) + 10;
      let inte0 = Math.ceil(data01 / 5);
      let inte1 = Math.ceil(data02 / 5);
      option.yAxis[0].interval = inte0;
      option.yAxis[1].interval = inte1;
      option.yAxis[0].splitNumber = 5;
      option.yAxis[1].splitNumber = 5;
      // 最大值为 间距 *  数量, 不是每类别的最大值 ; 这里 5 为间距
      option.yAxis[0].max = inte0 * 5;// 重点在这里
      option.yAxis[1].max = inte1 * 5;// 重点在这里
```

### position

`position: static|absolute|fixed|relative|sticky|initial|inherit;`

```css
/* 可以让状态栏悬浮*/
position: sticky;
```

例子:[https://www.w3schools.com/cssref/tryit.asp?filename=trycss_position_sticky](https://www.w3schools.com/cssref/tryit.asp?filename=trycss_position_sticky)

[https://developer.mozilla.org/en-US/docs/Web/CSS/position](https://developer.mozilla.org/en-US/docs/Web/CSS/position)

| position              | description                                                                                                                                                                                                                                                    |
| --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| static                | every element has a static position by default, so the element will stick to the normal page flow. So if there is a                                                                                                                                            |
| relative              | an element’s original position remains in the flow of the document, just like the static value. But now left/right/top/bottom/z-index will work. The positional properties “nudge” the element from the original position in that direction.                   |
| absolute              | the element is removed from the flow of the document and other elements will behave as if it’s not even there whilst all the other positional properties will work on it.                                                                                      |
| fixed                 | the element is removed from the flow of the document like absolutely positioned elements. In fact they behave almost the same, only fixed positioned elements are always relative to the document, not any particular parent, and are unaffected by scrolling. |
| sticky (experimental) | the element is treated like a relative value until the scroll location of the viewport reaches a specified threshold, at which point the element takes a fixed position where it is told to stick.                                                             |
| inherit               | the position value doesn’t cascade, so this can be used to specifically force it to, and inherit the positioning value from its parent.                                                                                                                        |

relative: 定位是相对于自身位置定位(设置偏移量的时候，会相对于自身所在的位置偏移). 设置了 relative 的元素仍然处在文档流中，元素的宽高不变，设置偏移量也不会影响其他元素的位置. 最外层容器设置为 relative 定位，在没有设置宽度的情况下，宽度是整个浏览器的宽度.

absolute: 定位是相对于离元素最近的设置了绝对或相对定位的父元素决定的，**如果没有父元素设置绝对或相对定位**，则元素相对于根元素即 html 元素定位. 设置了 absolute 的元素脱了了文档流，元素在没有设置宽度的情况下，宽度由元素里面的内容决定. 脱离后原来的位置相当于是空的，下面的元素会来占据位置.

### 盒子模型 `box-sizing`

```css
/* 元素实际占用的宽度等于style里的width+margin+border+padding, 即width就是能显示内容的宽度 */
box-sizing: content-box   //标准盒模型
/*元素实际占用的宽度等于style里的width, 这样可以让 margin,border,padding 不占用宽度 */
box-sizing: border-box    //怪异盒模型
```

### rem 与 em 的区别

rem 是根据 html 的 font-size 变化，而 em 是根据父级的 font-size 变化

```css
1(r)em = font-size 设置的px
```

### CSS 选择器

#### css 常用选择器

通配符: \*
ID 选择器: #ID
类选择器: .class
元素选择器: p、a 等
后代选择器: p span、div a 等
伪类选择器: a:hover 等
属性选择器: input[type="text"] 等

#### css 选择器权重

!important -> 行内样式 -> #id -> .class -> 元素和伪元素 -> \* -> 继承 -> 默认

### mouseover 和 mouseenter 的区别

1. mouseover: 当鼠标移入元素或其子元素都会触发事件，所以有一个重复触发，冒泡的过程. 对应的移除事件是 mouseout

2. mouseenter: 当鼠标移除元素本身(不包含元素的子元素)会触发事件，也就是不会冒泡，对应的移除事件是 mouseleave

### BFC(Block Formatting Contexts )

BFC 是页面中的一个元素, 而且决定了其子元素将如何定位，以及和其他元素的关系和相互作用.

### 触发条件

- body 根元素
- 浮动元素: float 除 none 以外的值
- 绝对定位元素: position (absolute、fixed)
- display 为 inline-block、table-cells、flex
- overflow 除了 visible 以外的值 (hidden、auto、scroll)

### BFC 特性

1. 同一个 BFC 下外边距会发生重叠. 如果想要避免外边距的重叠，可以将其放在不同的 BFC 容器中.
2. BFC 可以包含浮动的元素,清除浮动 让元素在盒子内部.
3. BFC 可以阻止元素被浮动元素覆盖.这特性可以用来实现两列(栏)自适应布局，一侧的宽度固定，另一侧的内容自适应宽度.
4. 内部的原始会在垂直方向，一个接一个地放置。
5. 计算 BFC 的高度时，浮动元素也参与计算

总结: 清除浮动,阻止覆盖,外边距重叠

## CSS 动画

### Transition 过渡

```css
transition: 1s; // 指定时间1s
transition: 1s height; // 指定适用于height
transition: 1s height, 1s width; // 指定多个属性
transition: 1s height, 1s 2s width; // 为width指定一个延迟(delay)属性,延迟2s

img {
  height: 15px;
  width: 15px;
}

img:hover {
  height: 450px;
  width: 450px;
}
```

#### transition-timing-function

- transition 的状态变化速度(又称 timing function)，默认不是匀速的，而是逐渐放慢，这叫做 ease.

```css
transition: 1s ease;
```

| 名称         | 效果           |
| ------------ | -------------- |
| linear       | 匀速           |
| ease-in      | 加速           |
| ease-out     | 减速           |
| cubic-bezier | 自定义速度模式 |

> _自定义速度模式 cubic-bezier，可以使用这个[网站](http://cubic-bezier.com/)来制定._

#### transition 定义单个属性

```css
img {
  transition-property: height;
  transition-duration: 1s;
  transition-delay: 1s;
  transition-timing-function: ease;
}
```

#### \*

- 各大浏览器(包括 IE 10)都已经支持无前缀的 transition，所以 transition 已经可以很安全地不加浏览器前缀.
- 不是所有 css 属性都支持 transition，具体看[这里](http://oli.jp/2010/css-animatable-properties/).

### Animation 动画

```css
div:hover {
  animation: 1s rainbow; //持续时间是1s rainbow是动画名称 默认动画只播放一次
}
// 通过 keyframes 指定名称
@keyframes rainbow {
  0% {
    background: #c00;
  }
  50% {
    background: orange;
  }
  100% {
    background: yellowgreen;
  }
}
```

```css
div:hover {
  animation: 1s rainbow infinite; //持续时间是1s rainbow是动画名称 默认动画只播放一次 infinite让动画无数次播放
}
```

| 关键字           | 效果                                 |
| ---------------- | ------------------------------------ |
| infinite         | 动画无数次播放                       |
| 数字(1、2、3 等) | 指定动画具体播放的次数(1 次、2 次等) |

#### 动画结束之后状态(一次播放)

- 动画结束以后，会立即从结束状态跳回到起始状态.
- 如果想让动画保持在结束状态，需要使用 animation-fill-mode 属性.

| animation-fill-mode 值 |                             效果                              |
| :--------------------: | :-----------------------------------------------------------: |
|          none          |                默认值，回到动画没开始时的状态.                |
|        forwards        |                     让动画停留在结束状态                      |
|       backwards        |           让动画回到第一帧的状态. (测试是开始状态)            |
|          both          | 根据 animation-direction 轮流应用 forwards 和 backwards 规则. |

#### animation-direction 动画指引(循环播放)

动画循环播放时，每次都是从结束状态跳回到起始状态，再开始播放. animation-direction 属性，可以改变这种行为.

```css
@keyframes rainbow {
  0% {
    background-color: yellow;
  }
  100% {
    background: blue;
  }
}
// 默认情况 animation-direction 是 normal

div:hover {
  animation: 1s rainbow 3 normal;
}
```

| animation-direction 属性 |          效果           |
| :----------------------: | :---------------------: |
|          normal          |    下次从第一帧开始     |
|         reverse          | 倒置动画 和 normal 相反 |
|        alternate         |        渐变播放         |
|    alternate-reverse     |    方向相反渐变播放     |

```css
//这是一个简写形式
div:hover {
  animation: 1s 1s rainbow linear 3 forwards normal;
}
//  delay 开始是延迟
div:hover {
  animation-name: rainbow;
  animation-duration: 1s;
  animation-timing-function: linear;
  animation-delay: 1s;
  animation-fill-mode: forwards;
  animation-direction: normal;
  animation-iteration-count: 3;
}
```

#### animation-play-state 控制动画终止状态

> 让动画保持突然终止时的状态

```css
div {
  animation: spin 1s linear infinite;
  animation-play-state: paused;
}

div:hover {
  animation-play-state: running;
}
```

#### 贝塞尔曲线动画

animation-timing-function: cubic-bezier(1, 0.25, 0.75, 0.75);

(x1, y1, x2, y2)
x1,y1 表示起点, x2,y2 终点

#### step 分步动画

> 浏览器从一个状态向另一个状态过渡，是平滑过渡. steps 函数可以实现分步过渡.

_[step 例子](http://dabblet.com/gist/1745856)，可以看到 steps 函数的用处._

### 附录: transform 属性

> [w3school](http://www.w3school.com.cn/cssref/pr_transform.asp) 解释

|                   名称                    | 效果                                   |
| :---------------------------------------: | :------------------------------------- |
|                   none                    | 定义不进行转换                         |
|            matrix(n,n,n,n,n,n)            | 定义 2D 转换，使用六个值的矩阵.        |
| matrix3d(n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n) | 定义 3D 转换，使用 16 个值的 4x4 矩阵. |
|              translate(x,y)               | 定义 2D 转换.                          |
|            translate3d(x,y,z)             | 定义 3D 转换.                          |
|               translateX(x)               | 定义转换，只是用 X 轴的值.             |
|               translateY(y)               | 定义转换，只是用 Y 轴的值.             |
|               translateZ(z)               | 定义 3D 转换，只是用 Z 轴的值.         |
|                scale(x,y)                 | 定义 2D 缩放转换.                      |
|              scale3d(x,y,z)               | 定义 3D 缩放转换.                      |
|                 scaleX(x)                 | 通过设置 X 轴的值来定义缩放转换.       |
|                 scaleY(y)                 | 通过设置 Y 轴的值来定义缩放转换.       |
|                 scaleZ(z)                 | 通过设置 Z 轴的值来定义 3D 缩放转换.   |
|               rotate(angle)               | 定义 2D 旋转，在参数中规定角度.        |
|           rotate3d(x,y,z,angle)           | 定义 3D 旋转.                          |
|              rotateX(angle)               | 定义沿着 X 轴的 3D 旋转.               |
|              rotateY(angle)               | 定义沿着 Y 轴的 3D 旋转.               |
|              rotateZ(angle)               | 定义沿着 Z 轴的 3D 旋转.               |
|           skew(x-angle,y-angle)           | 定义沿着 X 和 Y 轴的 2D 倾斜转换.      |
|               skewX(angle)                | 定义沿着 X 轴的 2D 倾斜转换.           |
|               skewY(angle)                | 定义沿着 Y 轴的 2D 倾斜转换.           |
|              perspective(n)               | 为 3D 转换元素定义透视视图.            |

[阮一峰地址](http://www.ruanyifeng.com/blog/2014/02/css_transition_and_animation.html)

一个进度条的效果

```css
.circle-progress-dialog {
  height: 100px;
  width: 100px;
  z-index: 100;
  position: absolute;
  background: rgba(156, 156, 156, 0.192);
  animation: spin 5s linear infinite forwards;
  border-radius: 50px;
  -webkit-animation: spin 5s linear infinite forwards;
  border-color: rgb(0, 153, 255);
  border-style: solid;
  border-width: 2px 2px 2px 2px;
}
@keyframes spin {
  0% {
    border-width: 0px 0px 0px 0px;
  }
  50% {
    border-width: 20px 20px 20px 20px;
  }
  100% {
    border-width: 0px 0px 0px 0px;
  }
}
```

### CSS Root

:root 是一个伪类选择器，它是一个能够匹配文档根元素的选择器，通常指的是 html 元素。 我们在 :root 里创建变量在全局都可用，即在任何选择器里都生效。

### CSS 变量

````css
:root {
  --bird-skin: yellow;
}
@media (max-width: 400px) {
  :root {
    --bird-skin: grey;
  }
}
.a {
  --bird-skin: black;
}
.b {
  background: var(--bird-skin);
}

### type="checkbox" ```css [type="checkbox"] {
  margin: 20px 0px 20px 0px;
}
````

## html 不使用 input 输入(富文本输入)

### contenteditable 属性

```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <title>
    Editable Div
  </title>
  <meta charset="UTF-8">
  </meta>
  <style >
    .edit-div {
      height: 300px;
      width: 100vw;
    }
  </style>
</head>

<body>
  <div id="edit-div" contenteditable="true">
    1. 譬如厨子做菜，有人品评他坏，他固不应该将厨刀铁斧交给批评者，说道你来做一碗好的看；
    2. 从来如此便对吗？
    3. 损着别人的牙眼，却反对报复，主张宽容的人，万勿和他接近
    4. 他们都叫你学好，好自个使坏.
  </div>
</body>

</html>
```

### 编辑 style

```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <title>
    Editable Div
  </title>
  <meta charset="UTF-8">
  </meta>

</head>

<body>
  <!-- 可以编辑 background-color , 相应edit-div 会有变化 -->
  <style contenteditable style="display:block">
    .edit-div {
      height: 300px;
      width: 100vw;
      background-color: aliceblue;
    }
  </style>
    <div class="edit-div" contenteditable="true">
      1. 譬如厨子做菜，有人品评他坏，他固不应该将厨刀铁斧交给批评者，说道你来做一碗好的看；
      2. 从来如此便对吗？
      3. 损着别人的牙眼，却反对报复，主张宽容的人，万勿和他接近
      4. 他们都叫你学好，好自个使坏.
    </div>
</body>

</html>

```

### Range.insertNode

会破坏 `ctrl+z` 等操作

```js
if (window.getSelection && (sel = window.getSelection()).rangeCount) {
  range = sel.getRangeAt(0);
  range.collapse(true);
  var span = document.createElement("span");
  span.id = "myId";
  span.appendChild(document.createTextNode("hi"));
  range.insertNode(span);

  // Move the caret immediately after the inserted span
  range.setStartAfter(span);
  range.collapse(true);
  sel.removeAllRanges();
  sel.addRange(range);
}
```

## HTML 标签

```html
<!DOCTYPE html>
<!-- 使用 HTML5 doctype，不区分大小写 -->
<meta charset="UTF-8" />
<!-- 声明文档使用的字符编码 -->
<!-- 每2秒中刷新当前页面: -->
<meta http-equiv="refresh" content="2" />
```

## meta 标签

禁用 Google 翻译提醒 `<meta name="google" content="notranslate">`

### SEO 优化

参考: <https://developers.google.com/search/docs/beginner/seo-starter-guide>

分析速度: <https://developers.google.com/speed/pagespeed/insights/>

```html
<meta name="description" content="不超过150个字符" />
<!-- 页面描述 -->
<meta name="keywords" content="" />
<!-- 页面关键词 -->
<title>标题</title>
<meta name="author" content="name, email@gmail.com" />
<!-- 网页作者 -->
<!-- none，noindex，nofollow，all，index和follow -->
<meta name="robots" content="index,follow" />
<!-- 搜索引擎抓取 follow,index 默认行为,可忽略 -->
```

### 移动设备 viewport

```html
<meta
  name="viewport"
  content="width=device-width,height=device-height,initial-scale=1, maximum-scale=1.0, minimum-scale=1, user-scalable=no"
/>
```

## HTML5 中异步加载脚本

html5 中 script 元素具有局部属性: type，src，defer，async，charset.

- type 指定引用或定义的脚本的类型. 对于 JavaScript 脚本，可以省略此属性.
- src 指定外部脚本文件的 URL.
- defer 属性指示浏览器在页面加载和解析之前不执行脚本,和将 script 元素放在文档的末尾类似. 当浏览器直到 DOM 元素全部解析完. 才会加载和执行具有 defer 属性的脚本.

```html
<script defer src="example.js"></script>
```

- async 浏览器会异步加载和执行脚本，同时继续解析 HTML 中的其他元素，包括其他脚本元素.
- charset 指定外部脚本文件的字符编码.

> defer,async,charset 只能和有 src 的属性结合使用.

## link 和 import

```html
<!-- 只能加载css 引用的css 会等页面加载完才会加载 -->
<style type="text/css">
  @imoprt url(css_file_url);
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

## Vscode Chrome 调试 (估计不能用了)

Exec=/usr/bin/google-chrome-stable %U --remote-debugging-port=9222
google-chrome-stable --remote-debugging-port=9222 --remote-debugging-host=0.0.0.0

### chrome.desktop

```kdg
#!/usr/bin/env xdg-open
[Desktop Entry]
Version=1.0
Name=Google Chrome
# Only KDE 4 seems to use GenericName, so we reuse the KDE strings.
# From Ubuntu's language-pack-kde-XX-base packages, version 9.04-20090413.
GenericName=Web Browser
Exec=/usr/bin/google-chrome-stable %U --remote-debugging-port=9222
Terminal=false
Icon=google-chrome
Type=Application
Categories=Network;WebBrowser;
```

## 参数

构造函数可以预置一个变量, 其它地方使用时也可以传.
属性可选 `name: "a"|"z"`

## 运算符

```js
?. // 条件表达式
?? // 或 同 || 但是只判断null 或者undefined
```

## 函数防抖

只执行最后一次

```js
function debounce(fn, delay = 500) {
  let timer = null;
  return function () {
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(() => {
      fn.apply(this, arguments);
      timer = null;
    }, delay);
  };
}

doucment.addEventListener(
  "keyup",
  debounce(function () {}, 600)
);
```

## 函数节流

一段时间内只执行一次

```js
function throttle(fn, delay) {
  let timer = null;

  return function () {
    if (timer) {
      return;
    }
    timer = setTimeout(() => {
      fn.apply(this, arguments);
      timer = null;
    });
  };
}
// test
doucment.addEventListener(
  "keyup",
  throttle(function (e) {}, 100)
);
```

## 图片

### 响应式图片 按分辨率切换图片

<https://developer.mozilla.org/zh-CN/docs/Learn/HTML/Multimedia_and_embedding/Responsive_images>

```html
<img
  srcset="elva-fairy-480w.jpg 480w, elva-fairy-800w.jpg 800w"
  sizes="(max-width: 600px) 480px,
         800px"
  src="elva-fairy-800w.jpg"
  alt="Elva dressed as a fairy"
/>
```

浏览器会自动加载 `srcset` 列表中引用的最接近所选的槽大小的图像.

```html
<picture>
  <source media="(max-width: 799px)" srcset="elva-480w-close-portrait.jpg" />
  <source media="(min-width: 800px)" srcset="elva-800w.jpg" />
  <img src="elva-800w.jpg" alt="Chris standing up holding his daughter Elva" />
</picture>
```

媒体查询 css 实现, 很麻烦.

```css
@media (min-width: 769px) {
  .home {
    background-image: url(bg1080.jpg);
  }
}
@media (max-width: 768px) {
  .home {
    background-image: url(bg768.jpg);
  }
}
```

## PDF pdf.js

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
    />
    <meta name="google" content="notranslate" />
    <script src="./pdfjs/pdf.js"></script>
  </head>
  <body>
    <div id="pdf-viewer"></div>
    <script>
      async function run(pdfPathAndtoken) {
        let ss = pdfPathAndtoken.split(",");
        let pdfPath = ss[0];
        let token = ss[1];
        var loadingTask = pdfjsLib.getDocument({
          url: pdfPath,
          httpHeaders: { Authorization: token },
          withCredentials: true,
        });
        const pdf = await loadingTask.promise;
        const viewer = document.getElementById("pdf-viewer");
        for (let pageIndex = 1; pageIndex <= pdf.numPages; pageIndex++) {
          const page = await pdf.getPage(pageIndex);
          var scale = 1;
          var viewport = page.getViewport({ scale: scale });
          // Support HiDPI-screens.
          var outputScale = window.devicePixelRatio || 1;
          var canvas = document.createElement("canvas");
          viewer.appendChild(canvas);
          canvas.className = "pdf-page-canvas";
          var context = canvas.getContext("2d");
          canvas.width = Math.floor(viewport.width * outputScale);
          canvas.height = Math.floor(viewport.height * outputScale);
          canvas.style.width = Math.floor(viewport.width) + "px";
          canvas.style.height = Math.floor(viewport.height) + "px";
          var transform =
            outputScale !== 1 ? [outputScale, 0, 0, outputScale, 0, 0] : null;
          var renderContext = {
            canvasContext: context,
            transform: transform,
            viewport: viewport,
          };
          page.render(renderContext);
          var desiredWidth = 100;
          var viewport = page.getViewport({ scale: 1 });
          var scale = desiredWidth / viewport.width;
          var scaledViewport = page.getViewport({ scale: scale });
        }
      }
      // 这个地方是为了手机调用方便
      run("http://host:port/web/12.pdf,token");
    </script>
  </body>
</html>
```

## 其它

<https://github.com/nolimits4web>

## 自定义字体

示例: TTF 文件, 空格是个坑

```css
@font-face {
  font-family: "font-name";
  src: url("../public/assets/font/字体\ 文件.ttf");
  src: url("../public/assets/font/字体\ 文件.ttf") format("truetype");
  font-weight: 700;
  font-style: normal;
}
```

使用

```css
div {
  font-family: "font-name";
  font-weight: 700;
}
```

## 性能

### 服务器开启 G

### webpack 按需加载,提取第三方代码

按需加载 一般框架都会做好.

提取第三方代码

<https://webpack.js.org/guides/code-splitting/>

- Entry Points: Manually split code using entry configuration.
- Prevent Duplication: Use `Entry dependencies` or `SplitChunksPlugin` to dedupe and split chunks.
- Dynamic Imports: Split code via inline function calls within modules.

The `SplitChunksPlugin` allows us to extract common dependencies into an existing entry chunk or an entirely new chunk.

### Babel ES6 转换 ES5 冗余代码

使用`ts`没有这个.

<https://babeljs.io/docs/en/babel-plugin-transform-runtime>

```json
{
  "plugins": [
    [
      "@babel/plugin-transform-runtime",
      {
        "absoluteRuntime": false,
        "corejs": 2,
        "version": "^7.7.4"
      }
    ]
  ]
}
```

### 切分任务 或 `Web Workers`

耗时任务分割.

Web Workers:

<https://developer.mozilla.org/zh-CN/docs/Web/API/Worker>

```js
const first = document.querySelector("#number1");
const second = document.querySelector("#number2");

const result = document.querySelector(".result");

if (window.Worker) {
  const myWorker = new Worker("worker.js");

  first.onchange = function () {
    myWorker.postMessage([first.value, second.value]);
    console.log("Message posted to worker");
  };

  second.onchange = function () {
    myWorker.postMessage([first.value, second.value]);
    console.log("Message posted to worker");
  };

  myWorker.onmessage = function (e) {
    result.textContent = e.data; //计算结果返回
    console.log("Message received from worker");
  };
} else {
  console.log("Your browser doesn't support web workers.");
}
```

work.js 内容

```js
onmessage = function (e) {
  console.log("Worker: Message received from main script");
  const result = e.data[0] * e.data[1];
  if (isNaN(result)) {
    postMessage("Please write two numbers");
  } else {
    const workerResult = "Result: " + result;
    console.log("Worker: Posting message back to main script");
    postMessage(workerResult);
  }
};
```

<https://web.dev/stick-to-compositor-only-properties-and-manage-layer-count/>

Use transform and opacity changes for animations

重要一点, **按需分析**

## 动画

```js
function updateScreen(time) {
  // Make visual updates here.
}

requestAnimationFrame(updateScreen);
```

## drop 元素拖动

## scroll-snap-\*

tag: 轮播，分页 ，滚动，viewpage,加载更多，特殊

scroll-snap-type CSS 属性设置在滚动容器上强制执行捕捉点（如果有）的严格程度。
The scroll-snap-type CSS property sets how strictly snap points are enforced on the scroll container in case there is one.

```css
scroll-snap-type: x mandatory;
```

这个 css 类别下可以实现像是 Android 的 viewpager 的功能

### 示例

tag: looper, banner

```html
<!-- 这里也用到了  window.intersectionObserver 用以显示小圆点 -->
<script>
     addSwiperScroll() {
       const element = this.$ref.myElement
       // Options for the observers
       const observerOptions = {
         root: this.$refs.myElementParent.$el,
         rootMargin: '-50%',
         threshold: []
       }
       for (const iterator of element) {
         const callback = (entries) => {
           entries.forEach((entry) => {
             console.log(entry)
             if (entry.isIntersecting) {
               this.lastLoopIndex = this.currentLoopIndex
               this.currentLoopIndex = parseInt(entry.target.dataset.index)
             }
           })
         }
         const observer = new IntersectionObserver(callback, observerOptions)
         observer.observe(iterator.$el)
         this.observers.push(observer)
       }
     }
  handleScroll(event) {
       console.log(event)
       uni.createSelectorQuery().select('#myElementParent').boundingClientRect().exec(res1 => {
         // const parent = res1[0]
         uni.createSelectorQuery().selectAll('#myElement').boundingClientRect().exec(res => {
           for (let index = 0; index < res[0].length; index++) {
             const item = res[0][index]
             console.log(item, res1)
             // 标准做法，有些情况这个会有问题
             // if (item.left >= parent.left && item.right <= parent.right) {
             //   console.log(index)
             //   break
             // }
             // 保险做法
             if (item.left >= 0 && item.right <= window.innerWidth) {
               console.log(index)
               break
             }
           }
         })
       })}
</script>
<style>
  .part-2 .part-2-swiper {
    margin-top: 16px;
    scroll-margin-left: 0; // 重要
    height: 68.27vw;
    display: flex;
    width: 95.33vw;
    scroll-snap-type: x mandatory; // 重要
    overflow-x: scroll;
    overflow-y: hidden;
    box-sizing: border-box;
  }

  .part-2 .part-2-view {
    background-image: url("https://www.bingimg.cn/down/OHR.CapitolButte_ZH-CN7707972988_1920x1080.jpg");
    width: 95.33vw;
    background-size: 100% 100%;
    height: 68.27vw;
    display: flex;
    flex-shrink: 0;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    scroll-snap-align: start; //  重要
    box-sizing: border-box;
  }

  /* Hide the scrollbar */
  .part-2-swiper::-webkit-scrollbar {
    display: none;
  }

  .part-2 .part-2-img {
    width: 70.13vw;
    height: 44.47vw;
    flex-shrink: 0;
    flex-grow: 0;
  }
</style>
```

## window.intersectionObserver

tag: 加载更多，特殊，图片懒加载

检查元素变化

Intersection Observer API 提供了一种异步观察目标元素与祖先元素或顶级文档视口相交变化的方法。

示例 参考 scroll-snap-\*

## 数字转换

```ts
export function convertNumber(
  value: number,
  startIndex?: number
): (string | number)[] {
  const uu = [
    "个",
    "百",
    "万",
    "百万",
    "亿",
    "百亿",
    "万亿",
    "亿亿",
    "百兆",
    "万兆",
    "超时空计算",
  ];
  const u = [];
  startIndex = startIndex ?? 2;
  for (let index = 0; index < startIndex; index++) {
    u.push("");
  }
  for (let index = startIndex ?? 2; index < uu.length; index++) {
    u.push(uu[index]);
  }
  let s: number = value;
  let index;
  for (index = 0; s > 100; index++) {
    s = s / 100;
  }
  if (u[index] === "") {
    s = value;
  } else {
    s = Number(s.toFixed(2));
  }
  return [s, u[index], index];
}
```

## shadow root

<https://www.geeksforgeeks.org/what-is-shadow-root-and-how-to-use-it/>
<https://stackoverflow.com/questions/34119639/what-is-shadow-root>
This is a special indicator that a Shadow DOM exists.

The Shadow DOM is simply saying that some part of the page, has its own DOM within it(分割 DOM). Styles and scripting can be scoped within that element so what runs in it only executes in that boundary.

```js
const shadowRootOne = document
  .getElementById("id")
  .attachShadow({ mode: "open" });
let myShadowDom = myCustomElem.shadowRoot;
const paragraphElement = document.createElement("p");
paragraphElement.setAttribute("class", "highlight");
paragraphElement.innerText = "This is a Shadow DOM paragraph";

shadowRootOne.appendChild(paragraphElement);
```

## Vite

### 环境变量配置

创建环境文件在项目根目录 ,如 `.env.development` , `.env.product`

添加类似内容

```evn
VITE_APP_NODE_ENV=production
VITE_APP_BASE_API="/"
```

`package.json` 修改

```json
"scripts": {
  "dev": "vite --host --mode development",
  "build-product": "vite build --base=/ztftrue/ --mode product",
  }
```

### 常用参数解析

--host 显示主机地址
--base 根路径, 比如添加 `--base=/ztftrue/` 变成`https://ztftrue.com/ztftrue/` 不添加就是 `https://ztftrue.com/`
--mode 指定环境

### 代理配置和代码兼容

代码兼容低版本浏览器, 需要安装 `@vitejs/plugin-legacy`, 然后导入 `import legacy from '@vitejs/plugin-legacy'`

在`vite.config.ts` 添加配置

```ts
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      template: { transformAssetUrls },
    }),
    vueJsx(),
    // 兼容低版本浏览器 配置, 会导致包变大, 某些场景不适用
    legacy({
      targets: [
        "defaults",
        "not IE 11",
        "chrome 52",
        "Firefox ESR",
        "ChromeAndroid 81",
      ], //需要兼容的目标列表，可以设置多个, 官方文档有说明
      additionalLegacyPolyfills: ["regenerator-runtime/runtime"],
      renderLegacyChunks: true,
      // 可以不写
      polyfills: [
        "es.symbol",
        "es.array.filter",
        "es.promise",
        "es.promise.finally",
        "es/map",
        "es/set",
        "es.array.for-each",
        "es.object.define-properties",
        "es.object.define-property",
        "es.object.get-own-property-descriptor",
        "es.object.get-own-property-descriptors",
        "es.object.keys",
        "es.object.to-string",
        "web.dom-collections.for-each",
        "esnext.global-this",
        "esnext.string.match-all",
      ],
    }),
  ],
  //
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  // 代理配置
  server: {
    host: "0.0.0.0",
    proxy: {
      "/api": {
        target: "",
        rewrite: (path) => path.replace("", ""),
      },
      "": {
        target: " ",
      },
    },
  },
});
```

## VUE3

`Provide` 越级传递数据(非法上访)

### 前进保存页面 keep-alive

在 Store 保存数据:

创建 sotre 文件

```ts
import { defineStore } from "pinia";
export const useCounterStore = defineStore("counter", () => {
  // APP.VUE中缓存的页面,默认保存页面
  const keepAliveInclude = ref<string[]>(["HomeView"]);
  // keepAliveInclude新增缓存页面
  function pushKeepAliveInclude(value: string) {
    if (typeof value !== "string") {
      return;
    }
    if (!keepAliveInclude.value.includes(value)) {
      keepAliveInclude.value.push(value);
    }
  }
  // keepAliveInclude移除缓存页面
  function popKeepAliveInclude(value: string) {
    if (typeof value !== "string") {
      return;
    }
    keepAliveInclude.value = keepAliveInclude.value.filter(
      (page) => page !== value
    );
  }
  // 只保留 home
  function clearAllAliveInclude() {
    keepAliveInclude.value.splice(1, keepAliveInclude.value.length);
  }
  return {
    keepAliveInclude,
    pushKeepAliveInclude,
    popKeepAliveInclude,
    clearAllAliveInclude,
  };
});
```

路由处理

```ts
const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0, left: 0 }
    }
  },
   routes: []
}
router.beforeEach((to, from, next) => {
  const counter = useCounterStore()
  if (to.name !== 'login' && !dealToken(counter)) {
    next({ name: 'login' })
  } else if (to.name === 'login' && dealToken(counter)) {
    next({ name: 'main' })
  }
  if (to.path === '/main') {
    counter.clearAllAliveInclude()
  } else {
    let toName = to.name?.toString()
    let fromName = from.name?.toString()
    if (fromName && toName) {
      //main 一直保持
      if (fromName !== 'main') {
        if (counter.keepAliveInclude.includes(toName)) {// 后退
          // 后退->弹出要离开的页面
          counter.popKeepAliveInclude(fromName)
          // 如果这个页面不需要保存 或者 需要刷新, 就把这个页面移除
          if (counter.needRefresh) {
              counter.needRefresh = false
              counter.popKeepAliveInclude(toName)
          }
        } else {
          //前进 -> 保存要离开的页面
          counter.pushKeepAliveInclude(fromName)
        }
      }
    }
  }
  next()
})
```

首页处理

```html
<template>
  <RouterView v-slot="{ Component }">
    <KeepAlive :include="counter.keepAliveInclude">
      <component :is="Component" :key="$route.fullPath" />
    </KeepAlive>
  </RouterView>
</template>
```

这种方法的好处就是,不用去每个页面单独处理

### 路由监听

```ts
const route = useRoute();
watch(
  () => route.path,
  (newPath, oldPath) => {
    //
  },
  { immediate: true }
);
```

### 对话框响应后退(返回)事件

```ts
// 在组件中,监听路由
onBeforeRouteLeave((to, from, next) => {
  if (dialog.value) {
    dialog.value = false;
    window.history.pushState(null, "", from.fullPath);
  } else {
    next();
  }
});
```

### Quasar

坑:Loading 升级后 spinner 不能指定了(应该是文档没有更新)

Loading.show() Loading.hide() // 可以用在页面加载 这是个插件

q-layout 使用起来也比较方便, 以前用过某些组件很难用

Quasar 有些默认样式不符合要求, 尝试修改无效(discord),想了个办法,运行前添加修改脚本, 其实有插件可以做,但是插件用起来太麻烦.
脚本示例:

```js
// changefile.js
var fs = require("fs");
// 同步读取
var path = "";
var path2 = "";
var data = fs.readFileSync(path).toString();
data = data.replace(/\s*opacity\s*:\s*.*/g, "");
data = data.replace(/dashed/g, "line");
var fd = fs.openSync(path, "w+");
fs.writeSync(fd, data);
fs.closeSync(fd);

data = fs.readFileSync(path2).toString();
data = data.replace(/\s*opacity\s*:\s*.*/g, "");
fd = fs.openSync(path2, "w+");
fs.writeSync(fd, data);
fs.closeSync(fd);
console.log("change file finish");
```

`package.json` 添加

```json
"build": "node ./changefile.jss&&vite build"
```

#### nofity

nofity 如果输入的是 Object 就不能消失, 之所以发现是因为 有些时候服务器返回的东西太奇葩

```ts
$q.notify({
  type: "positive",
  position: "center",
  message: new Object(),
});
```

vue 创建项目 默认添加的下列代码

```css
*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
  position: relative;
  font-weight: normal;
}
```

会导致在手机上 notify 先右移到屏幕边缘然后突然移动到上边.

可以在需要的组件添加对应代码, vue 会通过给 `div` 添加 `data-v-47bf9479` 随机生成的内容 ,然后在 css 通过 `*[data-v-47bf9479]` 限定作用域

##### notify 取消

```ts
let notif: (props?: QNotifyUpdateOptions) => void; // 文档没加类型, 我用的时候发现不加类型不能取消
notif = $q.notify({
  spinner: true,
  message: "服务器宕机中...",
  timeout: 0, // 这样就不会自己取消了
  actions: [
    // { label: '取消', color: 'white', handler: () => { /* ... */ } }
  ],
});
// 取消方法
notif();
```

#### 上传组件的自定义封装

这里封装(修改) 成了 , 用户确认上传后 开始上传到服务器, 不是用户选择了图片后就开始上传.

可以添加水印

```vue
<script setup lang="ts">
import { useCounterStore } from "@/stores/counter";
import { fileRejectedTip } from "@/utils/variables";
import {
  QUploader,
  useQuasar,
  type QRejectedEntry,
  type QUploaderFactoryFn,
  Loading,
} from "quasar";
import { onMounted, ref, watch } from "vue";
const fileRejectedTip = new Map([
  ["max-file-size", "文件大小不符合限制"],
  ["duplicate", "上传文件重复"],
  ["max-files", "文件数量超出限制"],
  ["accept", "文件类型不符合限制"],
]);
export interface ImageUrl {
  url: string;
  id: string;
}
// 有个选项设置是否批量上传,  这里没有添加
export interface Props {
  maxFileSize?: number; //
  maxFiles?: number; // 最大文件数 , 和 Quasar 一样
  waterMark?: string; // 水印
  factory?: QUploaderFactoryFn;
  images?: string[]; // 图片路径或者id
  getUrl?: Function; // 如果传id, 必须设置这个方法 根据id获取图片路径
  disable?: boolean;
}

const ImageUpload = ref<QUploader>();

const UrlImages = ref<string[]>([]);

interface FinishModel {
  succeed: readonly any[];
  readonly failed: any[];
}

const emits = defineEmits<{
  (e: "finish", value: FinishModel): void;
  (e: "uploaded", value: readonly any[]): void;
  (e: "failed", value: readonly any[]): void;
  (e: "start"): void;
  (e: "add", value: readonly any[]): void;
  (e: "remove", value: readonly any[]): void;
}>();
onMounted(() => {
  if (props.images) {
    UrlImages.value = props.images;
  }
});
const props = withDefaults(defineProps<Props>(), {
  maxFileSize: 10485760, // 这里是 Hashcode , 我知道的
  maxFiles: 6,
  factory: function () {
    // factory ，我在写的时候, 开始开始必须是(不符合文档) `()=>()=>{}`, 过了一段时间后, 就不能运行了, 要改成(符合文档) `()=>{}`
    const counter = useCounterStore();
    return {
      url: `文件上传地址`,
      method: "POST",
      headers: [{ name: "Authorization", value: counter.userToken }],
      fieldName: "file",
      withCredentials: false,
      sendRaw: false,
    };
  },
});

const $q = useQuasar();

function onRejected(rejectedEntries: QRejectedEntry[]) {
  console.log(rejectedEntries);
  $q.notify({
    type: "negative",
    message: `${fileRejectedTip.get(rejectedEntries[0].failedPropValidation)}`,
  });
}

function finish() {
  let result: string[] = [];
  let faildResult = [];
  if (ImageUpload.value != null) {
    for (const iterator of ImageUpload.value.files) {
      if (!iterator.xhr) {
        faildResult.push(iterator);
        continue;
      }
      const rt = iterator.xhr.responseText;
      if (!rt) {
        faildResult.push(iterator);
        continue;
      }
      let r = JSON.parse(rt);
      if (r.code === "200") {
        result.push(...r.data);
      } else {
        faildResult.push(iterator);
      }
    }
  }
  let info = { succeed: result, failed: faildResult };
  if (faildResult.length > 0) {
    $q.notify({
      type: "negative",
      position: "center",
      message: `图片上传失败`,
    });
    emits("failed", faildResult);
    return;
  }
  emits("finish", info);
}

function failed(info: { files: readonly any[]; xhr: any }) {
  emits("failed", info.files);
}

function start() {
  emits("start");
}
// 因为服务器返回的值http code 始终是200, 所以要加一层判断
function uploaded(info: { files: readonly any[]; xhr: any }) {
  let r = JSON.parse(info.xhr.response);
  if (r.code === "200") {
    emits("uploaded", info.files);
  } else {
    if (ImageUpload.value != null) {
      // 先删除，在重新添加， updateFileStatus 无效
      ImageUpload.value.removeFile(info.files[0]);
      ImageUpload.value.addFiles(info.files);
    }
    emits("failed", info.files);
  }
}

function removeImage(e: readonly any[]) {
  emits("remove", e);
}

/**
 * 删除url
 * @param file
 * @param index
 */
function removeUrlImage(file: any, index: any) {
  UrlImages.value?.splice(index, 1);
}

/**
 * 获取图片路径，这里返回的是服务器返回的内容
 */
function getFileId(): string[] | undefined {
  let result: string[] = [];
  let faildResult = [];
  if (ImageUpload.value != null && !ImageUpload.value.isBusy) {
    for (const iterator of ImageUpload.value.files) {
      if (!iterator.xhr) {
        faildResult.push(iterator);
        $q.notify({
          type: "negative",
          message: `图片上传错误`,
        });
        continue;
      }
      const rt = iterator.xhr.responseText;
      if (!rt) {
        faildResult.push(iterator);
        continue;
      }
      let r = JSON.parse(rt);
      if (r.code === "200") {
        result.push(...r.data);
      } else {
        faildResult.push(iterator);
      }
    }
    UrlImages.value.forEach((item) => {
      result.push(item);
    });
    // 不用返回下边这行，因为如果出现错误，canUpload 为true
    // let info = { succeed: result, failed: faildResult }
    return result;
  }
}

defineExpose({
  ImageUpload,
  UrlImages,
  getFileId,
});

watch(
  () => props.images,
  (val) => {
    if (val) {
      UrlImages.value = val;
    }
  }
);
const canvasHtml = ref();
async function addImage(e: readonly any[]) {
  if (props.waterMark) {
    // 添加水印
    let waterMark = props.waterMark;
    await e.forEach(async (item) => {
      if (item.__water) {
        return;
      }
      let image = new Image();
      image.src = item.__img.src;
      let r = image.decode();
      await r.then(async () => {
        const canvas = canvasHtml.value;
        const context = canvas.getContext("2d");
        canvas.width = image.width;
        canvas.height = image.height;
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.drawImage(image, 0, 0);
        context.font = "40px Arial";
        //  字符间隔， 暂时没用
        let maxWidth = Math.trunc((canvas.width - 100) / 40);
        let text = [];
        let temptext = [];
        for (let i = 1, l = 0; l < waterMark.length; l++) {
          const element = waterMark[l];
          if (waterMark.charAt(l) === "\n") {
            text.push(temptext.join(""));
            temptext = [];
            i = 1;
          } else if (i == maxWidth) {
            temptext.push(element);
            text.push(temptext.join(""));
            temptext = [];
            i = 1;
          } else {
            temptext.push(element);
            i++;
          }
        }
        if (temptext.length > 0) {
          text.push(temptext.join(""));
        }
        context.fillStyle = "red";
        text.forEach((element, index) => {
          context.fillText(element, 20, 80 * (index + 1));
        });

        image = new Image();
        await canvas.toBlob((blob: Blob) => {
          console.log(item.type);
          let file = new File([blob], item.name, { type: "image/jpeg" });
          // @ts-ignore
          file.__water = true;
          ImageUpload.value?.removeFile(item);
          ImageUpload.value?.addFiles([file]);
        }, "image/jpeg");
      });
    });
  }
  emits("add", e);
}
// 点击放大图片的功能
const alert = ref(false);
const dialogImgUrl = ref("");
const scale = ref(1);
const translate3d = ref({ x: 0, y: 0 });
let touchStartX = 0,
  touchStartY = 0,
  touchEndX = 0,
  touchEndY = 0;
let initialDistance = 1;

const image = ref<HTMLImageElement>();
function onTouchStart(event: TouchEvent) {
  if (event.touches.length === 2) {
    touchStartX = event.touches[0].clientX;
    touchStartY = event.touches[0].clientY;
    touchEndX = event.touches[1].clientX;
    touchEndY = event.touches[1].clientY;
    initialDistance = Math.sqrt(
      (touchStartX - touchEndX) ** 2 + (touchStartY - touchEndY) ** 2
    );
  } else if (event.touches.length === 1) {
    touchStartX = event.touches[0].clientX;
    touchStartY = event.touches[0].clientY;
  }
}
let timeFilter = 0;
function onTouchMove(event: TouchEvent) {
  if (timeFilter == 0) {
    if (event.touches.length === 2) {
      touchStartX = event.touches[0].clientX;
      touchStartY = event.touches[0].clientY;
      touchEndX = event.touches[1].clientX;
      touchEndY = event.touches[1].clientY;
      let distance = Math.sqrt(
        (touchStartX - touchEndX) ** 2 + (touchStartY - touchEndY) ** 2
      );
      if (distance > initialDistance) {
        if (scale.value < 2) {
          scale.value = scale.value + 0.05;
        }
      } else {
        if (scale.value > 0.05) {
          scale.value = scale.value - 0.05;
        }
      }
      initialDistance = distance;
      if (distance / initialDistance) {
        // const scale1 = distance / Math.sqrt(2 * (image.value.clientWidth ** 2));
        // scale.value = distance/initialDistance;
      }
    } else if (event.touches.length === 1) {
      touchEndX = event.touches[0].clientX;
      touchEndY = event.touches[0].clientY;
      let mx = touchEndX - touchStartX;
      let my = touchEndY - touchStartY;
      translate3d.value = {
        x: translate3d.value.x + mx,
        y: translate3d.value.y + my,
      };
      touchStartX = event.touches[0].clientX;
      touchStartY = event.touches[0].clientY;
    }
  }
}

function onTouchEnd(event: TouchEvent) {
  touchStartX = 0;
  touchStartY = 0;
  touchEndX = 0;
  touchEndY = 0;
  initialDistance = 0;
}
</script>

<template>
  <!-- 点击可以放大图片 -->
  <q-dialog
    v-model="alert"
    maximized
    @hide="
      scale = 1;
      translate3d = { x: 0, y: 0 };
    "
  >
    <div
      class="resizable"
      @touchstart="onTouchStart"
      @touchmove="onTouchMove"
      @touchend="onTouchEnd"
      style="width:100%;height:100%;padding: 0px;overflow: hidden;display: flex;justify-content: center; background-color: rgb(0, 0, 0);"
      v-close-popup
    >
      <q-img
        width="100%"
        fit="scale-down"
        ref="image"
        :src="dialogImgUrl"
        class="img-dialog"
        :style="{
          transform: `translate3d(${translate3d.x}px,${translate3d.y}px,0px) scale3d(${scale},${scale},1)`,
        }"
      />
    </div>
  </q-dialog>

  <div style="overflow: scroll;height: 1px;width: 1px;">
    <canvas ref="canvasHtml"></canvas>
  </div>

  <q-uploader
    @failed="failed"
    @start="start"
    style="width: 100%;"
    :readonly="disable"
    @rejected="onRejected"
    flat
    multiple
    @finish="finish"
    @added="addImage"
    @removed="removeImage"
    @uploaded="uploaded"
    :max-file-size="props.maxFileSize"
    :factory="factory"
    accept="image/*"
  >
    <template v-slot:header="scope">
      <div v-show="false && scope.isUploading">{{ (ImageUpload = scope) }}</div>
    </template>

    <template v-slot:list="scope">
      <div class="upload-file-list">
        <div
          class="upload-file-list-item"
          v-for="(file, index) in UrlImages"
          :key="index"
        >
          <q-img
            style="width:100%;height:100%"
            class="upload-file-list-item-image"
            ratio="1"
            @click="
              dialogImgUrl = props.getUrl ? props.getUrl(file) : file;
              alert = true;
            "
            :src="props.getUrl ? props.getUrl(file) : file"
          >
          </q-img>
          <q-btn
            class="close-img"
            size="12px"
            color="gray"
            flat
            dense
            round
            icon="o_cancel"
            v-show="!props.disable"
            @click="removeUrlImage(file, index)"
          />
        </div>
        <div
          class="upload-file-list-item"
          v-for="file in scope.files"
          :key="file.__key + file.__img.src"
        >
          <q-img
            style="width:100%;height:100%"
            class="upload-file-list-item-image"
            v-if="file.__img"
            ratio="1"
            :src="file.__img.src"
            @click="
              dialogImgUrl = file.__img.src;
              alert = true;
            "
          >
          </q-img>
          <q-btn
            class="close-img"
            size="12px"
            color="gray"
            v-show="!props.disable"
            flat
            dense
            round
            icon="o_cancel"
            @click="scope.removeFile(file)"
          />
        </div>
        <div class="upload-file-list-item items-center q-pa-sm q-gutter-xs">
          <q-spinner v-if="scope.isUploading" class="q-uploader__spinner" />
          <div v-if="scope.canAddFiles" class="submitGamePicture-btn">
            <div @click="scope.pickFiles">
              <img class="add-icon" :src="add" />
              <q-uploader-add-trigger />
            </div>
          </div>
        </div>
      </div>
    </template>
  </q-uploader>
</template>

<style lang="scss" scoped>
@mixin absolute-width($width) {
  width: $width;
  height: $width;
}

.resizable {
  display: inline-block;
  background: red;
  resize: both;
  overflow: hidden;
  line-height: 0;
}

.resizable img {
  width: 100%;
  height: 100%;
}

.img-dialog {
}

.upload-file-list {
  display: flex;
  flex-wrap: wrap;
  justify-items: center;
  height: auto;
  flex-shrink: 0;
  flex-grow: 0;
  width: 100%;

  .submitGamePicture-btn {
    background-color: #edededff;
    width: 62px;
    height: 62px;
    padding: 20px;

    .add-icon {
      width: 100%;
      height: 100%;
    }
  }

  .upload-file-list-item {
    display: flex;
    @include absolute-width(calc((100% - 60px) / 4));
    margin-right: 15px;
    flex-direction: column;
    flex-shrink: 0;
    flex-grow: 0;
    justify-items: center;
    justify-content: space-between;
    align-content: space-between;

    .upload-file-list-item-image {
      margin-top: 10px;
      margin-bottom: 10px;
      flex-shrink: 0;
      flex-grow: 0;
      object-fit: scale-down;
    }

    .close-img {
      position: absolute;
      top: -4px;
      right: -12px;
    }
  }
}

*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
  position: relative;
  font-weight: normal;
}
</style>
```

#### 日期时间组件

```vue
<script lang="ts" setup export>
import { onMounted, ref } from "vue";
import { date } from "quasar";

const tab = ref("date");
const ccurDate = ref(date.formatDate(Date.now(), "YYYY-MM-DD HH:mm:ss"));
const props = withDefaults(
  defineProps<{ modelValue: string | undefined | null }>(),
  {
    modelValue: date.formatDate(Date.now(), "YYYY-MM-DD HH:mm:ss"),
  }
);
onMounted(() => {
  if (props.modelValue)
    // 多余代码
    ccurDate.value = props.modelValue;
});
const emits = defineEmits<{
  (e: "update:modelValue", a: string): void;
}>();
function onConfirm() {
  emits("update:modelValue", ccurDate.value);
}
</script>
<template>
  <q-card>
    <q-tabs v-model="tab" inline-label>
      <q-tab name="date" icon="o_calendar_month" label="日期" />
      <q-tab name="time" icon="o_schedule" label="时间" />
    </q-tabs>
    <q-tab-panels v-model="tab" animated>
      <q-tab-panel style="padding: 0px;" keep-alive name="date">
        <q-date
          flat
          v-model="ccurDate"
          today-btn
          mask="YYYY-MM-DD HH:mm:ss"
          color="primary"
        >
          <div class="row items-center justify-end">
            <q-btn label="取消" color="primary" v-close-popup flat />
            <q-btn
              v-on:click="tab = 'time'"
              label="选择时间"
              color="primary"
              flat
            />
          </div>
        </q-date>
      </q-tab-panel>
      <q-tab-panel style="padding: 0px;" keep-alive name="time">
        <q-time
          flat
          v-model="ccurDate"
          mask="YYYY-MM-DD HH:mm:ss"
          color="primary"
        >
          <template v-slot>
            <div class="row items-center justify-end">
              <q-btn label="取消" color="primary" v-close-popup flat />
              <q-btn
                v-on:click="tab = 'date'"
                label="选择日期"
                color="primary"
                flat
              />
              <q-btn
                label="确定"
                @click="onConfirm"
                color="primary"
                v-close-popup
                flat
              />
            </div>
          </template>
        </q-time>
      </q-tab-panel>
    </q-tab-panels>
  </q-card>
</template>
```

#### 全屏搜索选择框

title: 标题,
disable?: boolean,
options?: any[] | null,
optionLabel: ((option: string | any) => any), // 和官方文档一样
optionValue: ((option: string | any) => any), // 和官方文档一样
selectedLabel?: string | null | undefined, // 选择的标签名称
selectedValue?: string | null | undefined, // 选择的标签值
rules?: ValidationRule[], // 和官方文档一样
modelValue?: any

const emits = defineEmits<{
(e: 'update:modelValue', a: string): void,// 对应上边
(e: 'update:selectedLabel', a: string): void,// 对应
(e: 'update:selectedValue', a: string): void, // 对应
(e: 'select', a: any): void, // 选择事件
(e: 'prepareOption', updateFn: Function): void,// 选择框显示事件,可以懒加载需要的数据
}>()

```vue
<template>
  <q-input
    v-model="selectedLabelLocal"
    :dense="true"
    readonly
    no-error-icon
    :rules="rules"
    @click="!disable ? (dialog = true) : ''"
    :input-style="{ textAlign: 'end' }"
  >
    <template v-slot:prepend v-if="$slots.prepend">
      <slot name="prepend"></slot>
    </template>
    <q-dialog
      v-model="dialog"
      @show="prepareOption"
      :maximized="true"
      transition-show="slide-up"
      transition-hide="slide-down"
    >
      <q-card style="overflow: hidden;">
        <div
          style="display: flex;flex-direction: column;overflow: hidden;height: 100%;"
        >
          <div class="title-bar">
            <q-btn flat icon="o_arrow_back_ios" v-close-popup> </q-btn>
            <div class="absolute-center">{{ title }}</div>
          </div>
          <q-input
            @update:model-value="searchUpdate"
            style="height: 50px;"
            rounded
            dense
            placeholder="在此搜索"
            v-model="text"
            bg-color="white"
          >
            <template v-slot:prepend>
              <q-avatar size="50px"><q-icon name="search" /></q-avatar>
            </template>
          </q-input>
          <div style="height: calc(100vh - 100px);overflow: scroll;">
            <q-list
              v-if="options"
              padding
              class="rounded-borders"
              style="max-width: 100%"
            >
              <q-item
                v-close-popup
                clickable
                v-ripple
                v-for="(val, index) of optionsLocal"
                :key="index"
                @click="clickSelect(val)"
              >
                <slot name="option-item" :value="val">
                  <div v-html="optionLabel(val)"></div>
                </slot>
              </q-item>
            </q-list>
            <q-inner-loading
              style="margin-top: 60px;"
              size="50px"
              :showing="
                options === undefined || options === null || options.length == 0
              "
            >
              <q-spinner-hourglass size="50px" color="primary" />
            </q-inner-loading>
          </div>
        </div>
      </q-card>
    </q-dialog>
  </q-input>
</template>

<script setup lang="ts">
import type { ValidationRule } from "quasar";
import { onMounted, ref, watch } from "vue";
import { onBeforeRouteLeave } from "vue-router";

const text = ref();
const optionsLocal = ref();
const selectedLabelLocal = ref();
const dialog = ref(false);

function searchUpdate(value: string | number | null) {
  filter(value);
}

function filter(search: string | number | null) {
  if (props.options == null) {
    return;
  }
  if (search == null) {
    optionsLocal.value = props.options;
  }
  let o: any[] = [];
  props.options?.forEach((item) => {
    if (props.optionLabel) {
      let l = props.optionLabel(item);
      if (l.includes(search)) {
        o.push(item);
      }
    }
  });
  optionsLocal.value = o;
}

const props = defineProps<{
  title: string;
  disable?: boolean;
  options?: any[] | null;
  optionLabel: (option: string | any) => any;
  optionValue: (option: string | any) => any;
  selectedLabel?: string | null | undefined;
  selectedValue?: string | null | undefined;
  rules?: ValidationRule[];
  modelValue?: any;
}>();

onMounted(() => {
  console.log(props.options);
  optionsLocal.value = props.options;
  if (optionsLocal.value) {
    for (let i = 0; i < optionsLocal.value.length; i++) {
      const item = optionsLocal.value[i];
      if (props.optionValue(item) === props.selectedValue) {
        selectedLabelLocal.value = props.optionLabel(item);
        break;
      }
    }
    if (!props.selectedValue) {
      selectedLabelLocal.value = undefined;
    }
  }
});
onBeforeRouteLeave((to, from, next) => {
  if (dialog.value) {
    dialog.value = false;
    window.history.pushState(null, "", from.fullPath);
  } else {
    next();
  }
});

const emits = defineEmits<{
  (e: "update:modelValue", a: string): void;
  (e: "update:selectedLabel", a: string): void;
  (e: "update:selectedValue", a: string): void;
  (e: "select", a: any): void;
  (e: "prepareOption", updateFn: Function): void;
}>();

function clickSelect(option: any) {
  selectedLabelLocal.value = props.optionLabel(option);
  emits("update:modelValue", option);
  emits("update:selectedLabel", props.optionLabel(option));
  emits("update:selectedValue", props.optionValue(option));
  emits("select", option);
}
watch(
  () => props.options,
  (newVal, oldVal) => {
    optionsLocal.value = newVal;
    if (optionsLocal.value && props.selectedValue) {
      optionsLocal.value.forEach((item: any[]) => {
        if (props.optionValue(item) === props.selectedValue) {
          selectedLabelLocal.value = props.optionLabel(item);
          emits("update:selectedLabel", props.optionLabel(item));
          //   emits("select", item);
        }
      });
    } else {
      selectedLabelLocal.value = undefined;
    }
  },
  { deep: true }
);
watch(
  () => props.selectedValue,
  (newVal, oldVal) => {
    if (optionsLocal.value) {
      for (let i = 0; i < optionsLocal.value.length; i++) {
        const item = optionsLocal.value[i];
        if (props.optionValue(item) === newVal) {
          selectedLabelLocal.value = props.optionLabel(item);
          // emits('update:modelValue', item)
          // emits('update:selectedLabel', props.optionLabel(item))
          // emits('select', item)
          break;
        }
      }
      if (!newVal) {
        selectedLabelLocal.value = undefined;
      }
    }
  }
);
function prepareOption() {
  emits("prepareOption", (fun: Function) => {
    if (fun) {
      fun();
    }
  });
}
</script>

<style lang="scss" scoped>
.title-bar {
  height: 50px;
  background: $primary;
  width: 100vw;
  display: flex;
  color: white;
  position: sticky;
}
*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
  position: relative;
  font-weight: normal;
}
</style>
```

#### splitter 和 infinite-scroll 结合使用 实现上拉浮动窗口

```vue
<script setup lang="ts">
const scrollTargetRef = ref();
const initHeight = 70;
const minHeight = 25; // 控制范围
const ratio = ref(initHeight);
const separatorButtionIcon = ref("o_expand_less"); // 按钮图标
// 控制按钮方向
function splitterChange(value: number) {
  if (value < heightSplitValue) {
    separatorButtionIcon.value = "o_expand_more";
  } else {
    if (value > 50) {
      ratio.value = initHeight;
    }
    separatorButtionIcon.value = "o_expand_less";
  }
}
// 点击按钮控制
function recoverHeight() {
  if (ratio.value > heightSplitValue) {
    ratio.value = minHeight;
    separatorButtionIcon.value = "o_expand_more";
  } else {
    ratio.value = initHeight;
    separatorButtionIcon.value = "o_expand_less";
  }
}
</script>
<template>
  <q-splitter
    @update:model-value="splitterChange"
    v-model="ratio"
    class="splice-view"
    id="photos"
    :limits="[minHeight, 100]"
    horizontal
    before-class="overflow-hidden"
    after-class="overflow-hidden"
  >
    <template v-slot:before>
      <div ref="viewDiv">上半部分内容</div>
    </template>
    <!-- 拖动按钮 -->
    <template v-slot:separator>
      <q-btn
        unelevated
        size="sm"
        dense
        style="top: -5px;
            position: absolute;width:70px;
            border-radius: 10px 10px 0vw 0vw;
            box-shadow: 0.5px -1px 1px #00000025;"
        square
        color="white"
        @click="recoverHeight"
        :icon="separatorButtionIcon"
        text-color="grey"
      />
    </template>
    <!-- 下 -->
    <template v-slot:after>
      <div class="after-content" :style="{ height: `100%` }">
        <div
          ref="scrollTargetRef"
          class="scroll"
          :style="{ height: `${100 - ratio + ratio + 1}%` }"
        >
          <!--   :style="{ height: `${100 - ratio + ratio + 1}%` }" 主要目标是触发重新渲染, 也有其它办法可以触发 -->
          <!-- onLoad 加载方法 -->
          <q-infinite-scroll
            @load="onLoad"
            :scroll-target="scrollTargetRef"
            :disable="noneMore"
            ref="infiniteScroll"
            :offset="250"
          >
            <!-- 重点在于 scrollTargetRef  :style, 否则会出现 infinite-scroll 位置错误的情况-->
            <template v-slot:loading>
              <div class="row justify-center q-my-md">
                <q-spinner-dots color="primary" size="40px" />
              </div>
            </template>
            <div v-if="noneMore" class="text-center q-mt-md">没有更多了</div>
          </q-infinite-scroll>
        </div>
      </div>
    </template>
  </q-splitter>
</template>
```

## Arcgis

资源配置

```ts
import esriConfig from "@arcgis/core/config.js";
esriConfig.assetsPath = import.meta.env.VITE_APP_ARCGIS_ASSETS_PATH
  ? import.meta.env.VITE_APP_ARCGIS_ASSETS_PATH
  : `${window.location.protocol}//${window.location.host}/arcgis/assets`;
esriConfig.fontsUrl;
```

### 去除 arcgis 地图选中边框

```css
/* 去除arcgis地图选中边框 */
.esri-view,
.esri-view-surface,
.esri-view .esri-view-surface--inset-outline:focus::after {
  outline: none !important;
}
.esri-view .esri-view-surface--inset-outline:focus::after {
  outline: auto 0px Highlight !important;
  outline: auto 0px -webkit-focus-ring-color !important;
}
/* 上边的没效果 */
.esri-view *:focus {
  outline: none !important;
}
.esri-view *:focus::after {
  outline: none !important;
}
/* end 下边是其它内容 */
.esri-ui .esri-popup {
  flex-flow: row;
  transform: translateY(-6px);
}

.esri-popup__header {
  display: none;
}

.esri-view-width-xlarge .esri-popup__main-container {
  flex-flow: row;
  align-items: center;
}

.esri-popup__footer--has-actions {
  max-width: 32px;
}

.esri-popup__inline-actions-container {
  width: 32px;
  align-items: center;
}

.esri-popup__main-container.esri-widget {
  background-color: white;
  display: flex;
  flex-direction: row;
  height: 32px;
  border-radius: 3px;
  max-width: 200px;
}

.esri-popup__content {
  margin: 0 0px 0px 10px;
  flex-flow: row;
  font-weight: bold;
  max-width: 160px;
}

.esri-popup__content > div {
  margin: auto;
  /* text-overflow: ellipsis; */
}

.esri-popup__footer {
  padding: 0;
  margin-top: 0 !important;
  background-color: #f7f8fa;
}

.esri-popup__button {
  margin: 0;
  color: #1b72e6;
}

.esri-popup__action-text {
  display: none;
}

.esri-popup__button.esri-popup__action {
  align-items: center;
  margin-left: -20px;
}

.esri-popup__icon.esri-popup__action-image {
  margin-left: -3px;
}

.esri-icon-zoom-in-magnifying-glass:before {
  /* content: '';
  background-image: url(../public/images/common/icon_daohang.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 20px;
  height: 20px;
  position: absolute;
  top: 4px;
  left: 8px; */
  display: none;
}
```

### 创建地图

```ts
const l1 = new TileLayer({
  url: "",
  id: "satellite-tile",
  visible: false,
  title: "satellite",
});
const l2 = new TileLayer({
  url: "",
  visible: true,
  id: "standard-tile",
  title: "standard",
});
// 这样创建的 MAP 可以在多个地方使用
// function createMap(): { mapView: MapView, mapDiv: HTMLDivElement } {
//   if (mapView && mapDiv) {
//       return { mapView, mapDiv }
//   }
const mapDiv = document.createElement("div");
mapDiv.className = "map-div";
mapDiv.style.height = "calc(100vh - 60px)";
mapDiv.style.width = "100vw";
const mapView = new MapView({
  map: map,
  center: [0, 0], // Longitude, latitude
  zoom: 3, // scale: 72223.819286
  container: mapDiv,
  constraints: {
    snapToZoom: false,
    maxZoom: 20,
  },
});
// 移除地图自带的组件
mapView.ui.empty("top-left");
mapView.ui.remove("attribution");
mapView.popup.collapseEnabled = false; //去除点击title收缩事件
mapView.popup.highlightEnabled = false;
// 添加地图
mapView.map.add(l1);
mapView.map.add(l2);
// 特别注意
// 当l1 和l2 切换(visible)时, 如果它们的坐标系不一样, 那么需要动态修改坐标系,如下:
if (l1.visable) {
  mapView.spatialReference = new SpatialReference({
    wkid: 2, // 对应的坐标系
  });
} else {
  mapView.spatialReference = new SpatialReference({
    wkid: 1, // 对应的坐标系
  });
}
```

### 画点/线

```ts
// 创建画点的图层
const pointLayer = new GraphicsLayer();
mapView.map.add(pointLayer, 10); // 这个10 没卵用

const symbolPic = new PictureMarkerSymbol({
  url: locateImage.toString(),
  width: "80px",
  height: "80px",
});
const point = new Point(x, y);
const textSymbol = new TextSymbol({
  text: "67",
  color: "white",
  yoffset: "-8px",
  // xoffset :'40px',
  font: {
    size: 12,
    family: "Arial",
  },
});
const pointImageGraphic = new Graphic({
  geometry: point,
  symbol: symbolPic, // 也可以使用 textSymbol 这样就加上了文字
});
pointLayer.add(pointImageGraphic);
```

画线

```ts
function drawLine(points: number[][]) {
  const simpleLineSymbol = {
    type: "simple-line",
    color: [41, 152, 255, 0.5],
    style: "solid",
    width: 3,
    outline: {
      color: [41, 152, 255, 0.5],
      width: 1,
    },
  };
  const polyline = new Polyline({
    paths: [points],
  });
  const pointGraphic = new Graphic({
    geometry: polyline,
    symbol: simpleLineSymbol,
  });
  const lineLayer = new GraphicsLayer();
  lineLayer.graphics.add(pointGraphic);
  mapView.map.add(lineLayer, 10); // 这个10 没卵用
}
```

### 绘制图形, 画面

```ts
export function drawPolygon(wkt: string) {
  const polylineSymbol = {
    type: "simple-fill", // autocasts as SimpleLineSymbol()
    color: [0, 0, 139, 0.5],
    width: 4,
    outline: {
      // autocasts as new SimpleLineSymbol()
      width: 0.5,
      color: [0, 0, 139, 0.5],
    },
  };
  const wr: number[][][] = wktToJson(wkt);
  const polygon = new Polygon({
    spatialReference: new SpatialReference({ wkid: 4326 }),
    rings: wr,
  });
  const graphic = new Graphic({
    geometry: polygon,
    symbol: polylineSymbol,
  });
  pLayer.add(graphic);
  pLayer.visible = true;
  // 移动到某一点
  // to(wr[0][0], wr[0][1])
}
```

### 撒点

```ts
const symbolPicture = new PictureMarkerSymbol({
  url: textImage,
  width: 26,
  height: 36,
});

let renderer2 = {
  type: "simple",
  field: "mag",
  symbol: symbolPicture,
};
let renderer1 = {
  type: "simple",
  field: "mag",
  symbol: {
    type: "simple-marker",
    size: 25,
    color: "#69dcff",
    outline: {
      color: "rgba(0, 139, 174, 0.3)",
      width: 15,
    },
  },
};
const clusterConfig = {
  type: "cluster",
  clusterRadius: "80px",
  clusterMinSize: 30,
  clusterMaxSize: 30,
  labelingInfo: [
    {
      deconflictionStrategy: "none",
      labelExpressionInfo: {
        expression: "Text($feature.cluster_count, '#,###')",
      },
      symbol: {
        type: "text",
        color: "white",
        haloColor: "#0d7970",
        haloSize: "1px",
        font: {
          size: 16,
          family: "sans-serif",
          weight: "bold",
        },
        // yoffset: 18,
      },
      labelPlacement: "center-center",
    },
  ],
};
let features: any[] = [];
features.push({
  geometry: {
    type: "point",
    x: Number(p.x),
    y: Number(p.y),
  },
  attributes: {
    ...p,
    type: "id",
  },
});
const featureLayer = new FeatureLayer({
  source: features,
  fields: fileds,
  title: "digital",
  outFields: ["*"],
  objectIdField: "ObjectID",
  id: `id`,
  featureReduction: clusterConfig,
  renderer: renderer1,
  popupEnabled: false,
  visible: true,
});
mapView.watch("scale", function (scale) {
  featureLayer.featureReduction = scale > 20000 ? clusterConfig : null;
  featureLayer.renderer = scale > 20000 ? renderer1 : renderer2;
});
```

### 点击事件

```ts
clickHandler = mapViewT.on("click", (event) => {
  mapView.hitTest(event).then(function (response) {
    if (response.results) {
    }
    //  图层查询
    export const searchParmas = [
      {
        url: "",
        sublayers: [0, ID],
      },
    ];
    queryMap(mapView, event.mapPoint, searchParmas)
      .then((res) => {})
      .catch((err) => {
        console.error(err);
      });
  });
});
```

### 图层查询

```ts
export interface ClickQuerylistConfig {
  name?: string;
  url: string;
  sublayers: number[];
}
export function queryMap(
  view: MapView,
  geometry: Point,
  config: ClickQuerylistConfig[]
) {
  const queryLlist: Promise<any>[] = [];
  config.forEach((item) => {
    const { url, sublayers } = item;
    const params = new IdentifyParameters({
      geometry,
      tolerance: 6,
      width: view.width,
      height: view.height,
      layerIds: sublayers,
      mapExtent: view.extent,
      returnGeometry: true,
      layerOption: "all",
      spatialReference: view.spatialReference,
      returnFieldName: true,
    });
    const res = identify.identify(url, params);
    queryLlist.push(res);
  });

  return Promise.all(queryLlist);
}
```

### 图形转换成 ArcGis 的 JSON

wkt 转换成 geojson

使用 `wellknown`,再使用 `fracturedjsonjs`

```ts
//@ts-ignore
import { parse, type GeoJSONGeometryOrNull } from "wellknown";

export class WKTUtil {
  public static wktToGeoJSONGeometry(wkt: string): GeoJSONGeometryOrNull {
    return parse(wkt);
  }

  public static wktToGeoJson(): string {
    return jsonFormatter.Serialize(wktToGeoJSONGeometry(wkt)) as string;
  }
}
```

### 坐标系的转换

对于地图：如果不是特别需要, 我记得有个`WorldJson`项目能直接用, 网络上也有开源地图.

```ts
function gcj02towgs84(lng: number, lat: number): { x: number; y: number } {
  //定义一些常量
  const PI = Math.PI;
  const a = 6378245.0;
  // eslint-disable-next-line no-loss-of-precision
  const ee: number = parseFloat((0.00669342162296594323).toPrecision(21));
  if (out_of_china(lng, lat)) {
    return { x: lng, y: lat };
  } else {
    let dlat = transformlat(lng - 105.0, lat - 35.0);
    let dlng = transformlng(lng - 105.0, lat - 35.0);
    const radlat = (lat / 180.0) * PI;
    let magic = Math.sin(radlat);
    magic = 1 - ee * magic * magic;
    const sqrtmagic = Math.sqrt(magic);
    dlat = (dlat * 180.0) / (((a * (1 - ee)) / (magic * sqrtmagic)) * PI);
    dlng = (dlng * 180.0) / ((a / sqrtmagic) * Math.cos(radlat) * PI);

    const mglat = lat + dlat;
    const mglng = lng + dlng;
    return { x: lng * 2 - mglng, y: lat * 2 - mglat };
  }
}

function transformlat(lng: number, lat: number) {
  //定义一些常量
  const PI = Math.PI;
  let ret =
    -100.0 +
    2.0 * lng +
    3.0 * lat +
    0.2 * lat * lat +
    0.1 * lng * lat +
    0.2 * Math.sqrt(Math.abs(lng));
  ret +=
    ((20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) *
      2.0) /
    3.0;
  ret +=
    ((20.0 * Math.sin(lat * PI) + 40.0 * Math.sin((lat / 3.0) * PI)) * 2.0) /
    3.0;
  ret +=
    ((160.0 * Math.sin((lat / 12.0) * PI) + 320 * Math.sin((lat * PI) / 30.0)) *
      2.0) /
    3.0;
  return ret;
}

function transformlng(lng: number, lat: number) {
  //定义一些常量
  const PI = Math.PI;
  let ret =
    300.0 +
    lng +
    2.0 * lat +
    0.1 * lng * lng +
    0.1 * lng * lat +
    0.1 * Math.sqrt(Math.abs(lng));
  ret +=
    ((20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) *
      2.0) /
    3.0;
  ret +=
    ((20.0 * Math.sin(lng * PI) + 40.0 * Math.sin((lng / 3.0) * PI)) * 2.0) /
    3.0;
  ret +=
    ((150.0 * Math.sin((lng / 12.0) * PI) +
      300.0 * Math.sin((lng / 30.0) * PI)) *
      2.0) /
    3.0;
  return ret;
}

/**
 * 判断是否在国内，不在国内则不做偏移
 * @param lng
 * @param lat
 * @returns {boolean}
 */
function out_of_china(lng: number, lat: number) {
  return (
    lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271 || false
  );
}
```

## TypeScript Axios 泛型使用

### Axios 配置

```ts
import { store } from "@/stores/store";
import axios from "axios";

// 创建axios实例
const instance = axios.create({
  // 基本请求路径的抽取
  baseURL: `${import.meta.env.VITE_APP_BASE_API}`,
  // 这个时间是你每次请求的过期时间，这次请求认为20秒之后这个请求就是失败的
  timeout: 20000,
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    config.headers.Authorization = store().userToken;
    return config;
  },
  (err) => {
    return Promise.reject(err);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (res) => {
    if (res.status === 200) {
      const data = res.data;
      if (data.code == undefined) {
        return Promise.resolve(data);
      }
      if (data.code === "200") {
        return Promise.resolve(data);
      } else if (data.code == "401") {
        // 如果是登录状态错误,就清除数据
        localStorage.removeItem("token");
        store().setUserToken("");
        window.location.reload(); // 我的项目里边路由那边做了拦截了, 所以直接刷新
        return Promise.reject(data);
      } else {
        return Promise.reject(data);
      }
    } else {
      return Promise.reject(res);
    }
  },
  (err) => {
    if (err.response) {
      const r = err.response;
      if (r.status === 401) {
        // 如果是登录状态错误,就清除数据
        localStorage.removeItem("token");
        store().setUserToken("");
        window.location.reload(); // 我的项目里边路由那边做了拦截了, 所以直接刷新
        return Promise.reject(err);
      }
    }
    return Promise.reject(err);
  }
);

export default instance;
```

再建立一个文件(不创建也行)

```ts
import request from "./axios上边的文件";

export const List = (): Promise<BaseModel<ListModel<ItemModel[]>>> =>
  request.get("");
export const Data = (): Promise<BaseModel<DataModel>> => request.get("");
// 最好新建一个文件
export interface BaseModel<T> {
  code: number;
  message: string | null | undefined;
  data: T;
}

export interface ListModel<T> {
  total: number;
  data: T;
}
export interface DataModel {}
```

### typescript 严格模式

```json
 "compilerOptions": {
     "strict":true
 }
```

### 枚举

```ts
enum PremissionOperate {
  ADD = "add",
  DELETE = "delete",
  UPDATE = "update",
}
```

## 网页通过 浏览器内部存储 下载文件

正常请求下载文件就可以

axios 示例

```ts
export const Download = (p: { path: string }): Promise<Blob> => {
  return axios.get("api/File/FileDownLoad", {
    params: p,
    responseType: "blob",
    timeout: 100000000,
  });
};
//axios.get("api/File/FileDownLoad", { params: p, responseType: 'blob', timeout: 100000000 }).then((r:Blob))
// 这种操作的好处是可以实现自己的下载方法
Download({ path: "" })
  .then((res) => {
    const url = URL.createObjectURL(res);
    // 示例
    const a = document.createElement("a");
    a.href = url;
    a.download = filename || "download";
    a.style.display = "none";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  })
  .catch((e) => {});
```

### 与 Android webView 交互

```ts
Download({ path: "" })
  .then((res) => {
    var reader = new FileReader();
    reader.readAsDataURL(res);
    reader.onloadend = function () {
      // 转出Base64, 因为anroid webView 不支持下载文件
      let base64data = reader.result;
      //@ts-ignore
      android.downloadFile(base64data, fileName); // 传给Android
    };
  })
  .catch((e) => {});
```

Android 端实现

```kt
  // 常规操作
  webView.addJavascriptInterface(InJavaScriptLocalObj(), "android")
  @Suppress("unused")
    inner class InJavaScriptLocalObj {

        @Throws
        private fun convertBase64StringToFileAndStoreIt(base64PDf: String, file: File) {
            val EMAIL_REGEX = "^data:[a-z-/]{1,25};base64,";
            val regex = Regex(EMAIL_REGEX)
            val t = base64PDf.replace(regex, "");// 移除 base64 开头的内容
            val pdfAsBytes: ByteArray = Base64.decode(t, Base64.DEFAULT)
            try {
                file.writeBytes(pdfAsBytes)
                open(file.path, "", this@MainActivity)
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, "FAILED TO DOWNLOAD THE FILE!", LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        @JavascriptInterface
        fun downloadFile(base64: String, fileName: String): String {// 接收文件
            val file = File(context.externalCacheDir, fileName)
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            convertBase64StringToFileAndStoreIt(base64, file)
            return ""
        }
    }

```

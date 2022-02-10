# 浏览器的工作原理与虚拟DOM

## 浏览器是如何渲染html的

首先渲染引擎从网络层获取文件的内容, 然后解析`html`, 并将各标签(如:`div`,`table`)逐个转化成DOM节点,并将这些节点附加到Document根对象上,这便是DOM 树. 与此同时浏览器也会将`CSS`解析`CSSOM`, 并将其附加到DOM树上, 然后构建渲染树,最后由渲染树处理和显示.如果修改了DOM或CSSOM，则会重新构建DOM树即重排(reflow),重新渲染(Repaint), [发生重排不是必然性的, 有些情况仅需要重绘](https://developers.google.com/speed/docs/insights/browser-reflow).

[关于更多](https://www.html5rocks.com/zh/tutorials/internals/howbrowserswork/),[也可以看看这个(推荐)](https://developers.google.com/web/fundamentals/performance/critical-rendering-path/render-tree-construction)

## 为什么要有虚拟DOM

重排对性能影响非常大, 每次一个节点重排时,都会让整个父节点甚至DOM树重排.

## ??判断运算符

## CSS

### position

```position: static|absolute|fixed|relative|sticky|initial|inherit;```

```css
/* 可以让状态栏悬浮*/
  position: sticky;
```

例子:[https://www.w3schools.com/cssref/tryit.asp?filename=trycss_position_sticky](https://www.w3schools.com/cssref/tryit.asp?filename=trycss_position_sticky)

[https://developer.mozilla.org/en-US/docs/Web/CSS/position](https://developer.mozilla.org/en-US/docs/Web/CSS/position)

|position|description|
|---|---|
|static| every element has a static position by default, so the element will stick to the normal page flow. So if there is a|
|relative| an element’s original position remains in the flow of the document, just like the static value. But now left/right/top/bottom/z-index will work. The positional properties “nudge” the element from the original position in that direction.|
|absolute| the element is removed from the flow of the document and other elements will behave as if it’s not even there whilst all the other positional properties will work on it.|
|fixed| the element is removed from the flow of the document like absolutely positioned elements. In fact they behave almost the same, only fixed positioned elements are always relative to the document, not any particular parent, and are unaffected by scrolling.|
|sticky (experimental)| the element is treated like a relative value until the scroll location of the viewport reaches a specified threshold, at which point the element takes a fixed position where it is told to stick.
|inherit| the position value doesn’t cascade, so this can be used to specifically force it to, and inherit the positioning value from its parent.|

relative: 定位是相对于自身位置定位(设置偏移量的时候，会相对于自身所在的位置偏移). 设置了relative的元素仍然处在文档流中，元素的宽高不变，设置偏移量也不会影响其他元素的位置. 最外层容器设置为relative定位，在没有设置宽度的情况下，宽度是整个浏览器的宽度.

absolute: 定位是相对于离元素最近的设置了绝对或相对定位的父元素决定的，__如果没有父元素设置绝对或相对定位__，则元素相对于根元素即html元素定位. 设置了absolute的元素脱了了文档流，元素在没有设置宽度的情况下，宽度由元素里面的内容决定. 脱离后原来的位置相当于是空的，下面的元素会来占据位置.

### 盒子模型 `box-sizing`

```css
/* 元素实际占用的宽度等于style里的width+margin+border+padding, 即width就是能显示内容的宽度 */
box-sizing: content-box   //标准盒模型
/*元素实际占用的宽度等于style里的width, 这样可以让 margin,border,padding 不占用宽度 */
box-sizing: border-box    //怪异盒模型
```

### rem与em的区别

rem是根据html的font-size变化，而em是根据父级的font-size变化

```css
1(r)em = font-size 设置的px
```

### CSS选择器

#### css常用选择器

通配符: *
ID选择器: #ID
类选择器: .class
元素选择器: p、a    等
后代选择器: p span、div a   等
伪类选择器: a:hover 等
属性选择器: input[type="text"]  等

#### css选择器权重

!important -> 行内样式 -> #id -> .class -> 元素和伪元素 -> * -> 继承 -> 默认

### mouseover和mouseenter的区别

1. mouseover: 当鼠标移入元素或其子元素都会触发事件，所以有一个重复触发，冒泡的过程. 对应的移除事件是mouseout

2. mouseenter: 当鼠标移除元素本身(不包含元素的子元素)会触发事件，也就是不会冒泡，对应的移除事件是mouseleave

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
5. 计算BFC的高度时，浮动元素也参与计算

总结: 清除浮动,阻止覆盖,外边距重叠

## CSS 动画

### Transition 过渡

```css
  transition: 1s; // 指定时间1s  
  transition: 1s height; // 指定适用于height
  transition: 1s height, 1s width; // 指定多个属性
  transition: 1s height, 1s 2s width; // 为width指定一个延迟(delay)属性,延迟2s


img{
    height:15px;
    width:15px;
}

img:hover{
    height: 450px;
    width: 450px;
}
 ```

#### transition-timing-function

- transition的状态变化速度(又称timing function)，默认不是匀速的，而是逐渐放慢，这叫做ease.

 ```css
  transition: 1s ease;  
 ```

  名称          |  效果
  ------       | ------  
  linear       |  匀速
  ease-in      |  加速
  ease-out     |  减速
  cubic-bezier | 自定义速度模式
  > _自定义速度模式cubic-bezier，可以使用这个[网站](http://cubic-bezier.com/)来制定._

#### transition定义单个属性

 ```css
  img{
    transition-property: height;
    transition-duration: 1s;
    transition-delay: 1s;
    transition-timing-function: ease;
  }
 ```

#### *

- 各大浏览器(包括IE 10)都已经支持无前缀的transition，所以transition已经可以很安全地不加浏览器前缀.
- 不是所有css属性都支持transition，具体看[这里](http://oli.jp/2010/css-animatable-properties/).

### Animation 动画

 ```css

   div:hover {
       animation: 1s rainbow; //持续时间是1s rainbow是动画名称 默认动画只播放一次
   }
   // 通过 keyframes 指定名称
   @keyframes rainbow {
     0% { background: #c00; }
     50% { background: orange; }
     100% { background: yellowgreen; }
   }
 ```

 ```css
   div:hover {
       animation: 1s rainbow  infinite; //持续时间是1s rainbow是动画名称 默认动画只播放一次 infinite让动画无数次播放
       
   }
 ```

 关键字         |    效果
 ------        | --------
 infinite      | 动画无数次播放
 数字(1、2、3等) | 指定动画具体播放的次数(1次、2次等)

#### 动画结束之后状态(一次播放)

- 动画结束以后，会立即从结束状态跳回到起始状态.
- 如果想让动画保持在结束状态，需要使用animation-fill-mode属性.

 animation-fill-mode值 |   效果
 :-------:        |  :-------:
 none           |  默认值，回到动画没开始时的状态.
 forwards       |  让动画停留在结束状态
 backwards      |  让动画回到第一帧的状态. (测试是开始状态)
 both           | 根据animation-direction轮流应用forwards和backwards规则.

#### animation-direction 动画指引(循环播放)

  动画循环播放时，每次都是从结束状态跳回到起始状态，再开始播放. animation-direction属性，可以改变这种行为.

 ```css
  @keyframes rainbow {
    0% { background-color: yellow; }
    100% { background: blue; }
  }
  // 默认情况 animation-direction 是 normal

  div:hover {
  animation: 1s rainbow 3 normal;
  }
 ```

animation-direction属性 | 效果
:-------: |---------
normal  | 下次从第一帧开始
reverse | 倒置动画 和normal相反
alternate | 渐变播放
alternate-reverse | 方向相反渐变播放

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
  animation-fill-mode:forwards;
  animation-direction: normal;
  animation-iteration-count: 3;
}
```

#### animation-play-state 控制动画终止状态

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

#### step 分步动画

 > 浏览器从一个状态向另一个状态过渡，是平滑过渡. steps函数可以实现分步过渡.

 _[step例子](http://dabblet.com/gist/1745856)，可以看到steps函数的用处._

### 附录: transform 属性

 > [w3school](http://www.w3school.com.cn/cssref/pr_transform.asp)  解释

 名称 | 效果
 :----: | :---
 none | 定义不进行转换
matrix(n,n,n,n,n,n) | 定义 2D 转换，使用六个值的矩阵.
matrix3d(n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n) | 定义 3D 转换，使用 16 个值的 4x4 矩阵.  
translate(x,y) | 定义 2D 转换.
translate3d(x,y,z) | 定义 3D 转换.  
translateX(x) | 定义转换，只是用 X 轴的值.
translateY(y) | 定义转换，只是用 Y 轴的值.
translateZ(z) | 定义 3D 转换，只是用 Z 轴的值.  
scale(x,y) | 定义 2D 缩放转换.
scale3d(x,y,z) | 定义 3D 缩放转换.
scaleX(x) | 通过设置 X 轴的值来定义缩放转换.
scaleY(y) | 通过设置 Y 轴的值来定义缩放转换.
scaleZ(z) | 通过设置 Z 轴的值来定义 3D 缩放转换.  
rotate(angle) | 定义 2D 旋转，在参数中规定角度.
rotate3d(x,y,z,angle) | 定义 3D 旋转.  
rotateX(angle) | 定义沿着 X 轴的 3D 旋转.
rotateY(angle) |  定义沿着 Y 轴的 3D 旋转.
rotateZ(angle) | 定义沿着 Z 轴的 3D 旋转.
skew(x-angle,y-angle) | 定义沿着 X 和 Y 轴的 2D 倾斜转换.  
skewX(angle) | 定义沿着 X 轴的 2D 倾斜转换.
skewY(angle) | 定义沿着 Y 轴的 2D 倾斜转换.
perspective(n) | 为 3D 转换元素定义透视视图.

[阮一峰地址](http://www.ruanyifeng.com/blog/2014/02/css_transition_and_animation.html)

一个进度条的效果

```css
.circle-progress-dialog{
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
  0%{
    border-width: 0px 0px 0px 0px;
  }
  50%{
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

```css

:root {
    --bird-skin: yellow;
}
@media (max-width: 400px) {
  :root {
  --bird-skin: grey;
  }
}
.a{
    --bird-skin: black;
}
.b{
    background: var(--bird-skin);
}

### type='checkbox'

```css
[type='checkbox'] {
  margin: 20px 0px 20px 0px;
}
```

## html 不使用input 输入(富文本输入)

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

### 编辑style

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

## HTML 标签

```html
<!DOCTYPE html> <!-- 使用 HTML5 doctype，不区分大小写 -->
    <meta charset="UTF-8">  <!-- 声明文档使用的字符编码 -->
    <!-- 每2秒中刷新当前页面: -->
    <meta http-equiv="refresh" content="2">
```

## meta 标签

### SEO 优化

参考: <https://developers.google.com/search/docs/beginner/seo-starter-guide>

分析速度: <https://developers.google.com/speed/pagespeed/insights/>

```html
<meta name="description" content="不超过150个字符" /> <!-- 页面描述 -->
<meta name="keywords" content=""/> <!-- 页面关键词 -->
<title>标题</title>
<meta name="author" content="name, email@gmail.com" /> <!-- 网页作者 -->
<!-- none，noindex，nofollow，all，index和follow -->
<meta name="robots" content="index,follow" /> <!-- 搜索引擎抓取 follow,index 默认行为,可忽略 -->
```

### 移动设备viewport

```html
<meta name ="viewport" content ="width=device-width,height=device-height,initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
```

## HTML5 中异步加载脚本

html5 中 script元素具有局部属性: type，src，defer，async，charset.

- type 指定引用或定义的脚本的类型. 对于JavaScript脚本，可以省略此属性.
- src 指定外部脚本文件的URL.
- defer 属性指示浏览器在页面加载和解析之前不执行脚本,和将script元素放在文档的末尾类似. 当浏览器直到DOM元素全部解析完. 才会加载和执行具有defer属性的脚本.

```html
   <script defer src="example.js"></script>
```

- async  浏览器会异步加载和执行脚本，同时继续解析HTML中的其他元素，包括其他脚本元素.
- charset  指定外部脚本文件的字符编码.

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

## Vscode  Chrome  调试 (估计不能用了)

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

## typescript 严格模式

```json
 "compilerOptions": {
     "strict":true
 }
```

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
    return function() {
        if (timer) {
            clearTimeout(timer)
        }
        timer = setTimeout(() => {
            fn.apply(this, arguments)
            timer = null
        }, delay)
    }
}
 
doucment.addEventListener('keyup', debounce(function () {
}, 600))
```

## 函数节流

一段时间内只执行一次

```js
function throttle(fn, delay) {
    let timer = null
    
    return function() {
        if (timer) {
            return
        }
        timer = setTimeout(() => {
            fn.apply(this, arguments)
            timer = null
        })
    }
}
// test
doucment.addEventListener('keyup', throttle(function(e) {
}, 100))
```

## position

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

## 盒子模型 `box-sizing`

```css
/* 元素实际占用的宽度等于style里的width+margin+border+padding, 即width就是能显示内容的宽度 */
box-sizing: content-box   //标准盒模型
/*元素实际占用的宽度等于style里的width, 这样可以让 margin,border,padding 不占用宽度 */
box-sizing: border-box    //怪异盒模型
```

## rem与em的区别

rem是根据html的font-size变化，而em是根据父级的font-size变化

```css
1(r)em = font-size 设置的px
```

## CSS选择器

### css常用选择器

通配符: *
ID选择器: #ID
类选择器: .class
元素选择器: p、a    等
后代选择器: p span、div a   等
伪类选择器: a:hover 等
属性选择器: input[type="text"]  等

### 复制代码css选择器权重

!important -> 行内样式 -> #id -> .class -> 元素和伪元素 -> * -> 继承 -> 默认

## mouseover和mouseenter的区别

1. mouseover: 当鼠标移入元素或其子元素都会触发事件，所以有一个重复触发，冒泡的过程. 对应的移除事件是mouseout

2. mouseenter: 当鼠标移除元素本身(不包含元素的子元素)会触发事件，也就是不会冒泡，对应的移除事件是mouseleave

## BFC(Block Formatting Contexts )

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

总结: 清除浮动,阻止覆盖,外边距重叠

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

+ transition的状态变化速度(又称timing function)，默认不是匀速的，而是逐渐放慢，这叫做ease. 

 ```css
  transition: 1s ease;  
 ```

  名称          |  效果
  ------       | ------  
  linear       |  匀速
  ease-in      |  加速
  ease-out     |  减速
  cubic-bezier | 自定义速度模式
  > _自定义速度模式cubic-bezier，可以使用这个[网站](http://cubic-bezier.com/)来制定. _

#### transition定义单个属性

 ```css
  img{
    transition-property: height;
    transition-duration: 1s;
    transition-delay: 1s;
    transition-timing-function: ease;
  }
 ```

####

+ 各大浏览器(包括IE 10)都已经支持无前缀的transition，所以transition已经可以很安全地不加浏览器前缀. 
+ 不是所有css属性都支持transition，具体看[这里](http://oli.jp/2010/css-animatable-properties/). 

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

+ 动画结束以后，会立即从结束状态跳回到起始状态. 
+ 如果想让动画保持在结束状态，需要使用animation-fill-mode属性.   

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

#### step 分步动画

 > 浏览器从一个状态向另一个状态过渡，是平滑过渡. steps函数可以实现分步过渡. 

 _[step例子](http://dabblet.com/gist/1745856)，可以看到steps函数的用处. _

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

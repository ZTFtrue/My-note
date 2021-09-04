# css 实现阴阳加载框

## 实现基本图形

1. 定义外形

```css
 #yin-yang {
      width: 100px;
      height: 50px;
      background: rgb(255, 255, 255);
      border-color: #000000;
      border-style: solid;
      display: flex;
      /* 让两个小圆分别居于两侧 */
      justify-content: space-between;
    }

 <body>
   <div id="yin-yang"></div>
 </body>

```

2. 修改边框宽度让其一边占据半个图形，在设置圆角为100%

```css
  border-width: 2px 2px 52px 2px;
  border-radius: 100%;
```

> 此时一半黑一半白

## 利用css伪元素实现阴阳

1. :before 实现阴的小圆

```css
    #yin-yang:before {
      content: "";
      /* 距离顶部25px */
      margin-top: 25px;
      background: rgb(255, 255, 255);
      /* 黑色外形大小*/
      border: 18px solid #000000;
      border-radius: 100%;
      /* 内部圆形大小 ,加上边框整个大小为50*/
      width: 14px;
      height: 14px;
    }
```

2. `:after` 实现阳

```css
 #yin-yang:after {
      content: "";
      margin-top: 25px;
      background: #000000;
      border: 18px solid rgb(255, 255, 255);
      border-radius: 100%;
      width: 14px;
      height: 14px;
  }
```

## 转起来

1. 绘制转的动画

```css
  @keyframes load-yin {
      0% {}
      50% {}
      100% {
        transform: rotate(360deg);
      }
    }
```

2. 调用动画

```css
  #yin-yang {
     animation: load-yin 1s linear infinite forwards; 
    }
```

## 全部代码

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <title>
    yin-yang
  </title>
  <meta charset="UTF-8"></meta>
  <style>
    #yin-yang {
      width: 100px;
      height: 50px;
      background: rgb(255, 255, 255);
      border-color: #000000;
      border-style: solid;
      border-width: 2px 2px 52px 2px;
      border-radius: 100%;
      display: flex;
      justify-content: space-between;
      animation: load-yin 1s linear infinite forwards;
    }
    /* CSS 伪元素 */
    #yin-yang:before {
      content: "";
      margin-top: 25px;
      background: rgb(255, 255, 255);
      border: 18px solid #000000;
      border-radius: 100%;
      width: 14px;
      height: 14px;
    }
    #yin-yang:after {
      content: "";
      margin-top: 25px;
      background: #000000;
      border: 18px solid rgb(255, 255, 255);
      border-radius: 100%;
      width: 14px;
      height: 14px;
    }
    @keyframes load-yin {
      0% {}
      50% {}
      100% {
        transform: rotate(360deg);
      }
    }
  </style>
</head>
<body>
  <div id="yin-yang"></div>
</body>
</html>
```

[svg 实现 https://github.com/ZTFtrue/new-tab/blob/main/src/assets/yinyang.svg](https://github.com/ZTFtrue/new-tab/blob/main/src/assets/yinyang.svg)

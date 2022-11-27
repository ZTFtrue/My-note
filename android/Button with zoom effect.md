# Android button with zoom out animation

如何实现android button缩放动画
如果把这个需求在网上搜一下, 一般都是自定义一个button, 今天来个不一样的套路.

给button 设置一个背景, 当```state_pressed="ture"``` 给它加一个透明边框,这样当用户按下时, 能看到的背景就会变小, 然后就会产生缩放效果.

代码:

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:state_pressed="false">
        <shape android:shape="rectangle">
            <corners android:radius="4dip" />
            <solid android:color="#54c3fb" />
        </shape>
    </item>

    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <corners android:radius="4dp"  />
            <solid android:color="#54c3fb"  />
           <!-- 加一个透明的边框 -->
            <stroke android:width="10dp" android:color="#006D7880" />
        </shape>
    </item>
</selector>
```

这样做也是有缺点,你可以在开发者选项里把动画调成```0x```,```stroke``` 设置够大, 再试试就会发现问题.不过这种情况一般也不会发生,所以其实还是可以用的.

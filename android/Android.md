# Base knowledge of Android

通过intent的bundle的源码可以看到它们都是实现了Parcelable，其实就是通过序列化来实现通信的. 大小限制 1M.

startActivity携带的数据会经过BInder内核再传递到目标Activity中去，因为binder映射内存的限制，所以startActivity也就会这个限制了

一、写入临时文件或者数据库，通过FileProvider将该文件或者数据库通过Uri发送至目标. 一般适用于不同进程，比如分离进程的UI和后台服务，或不同的App之间. 之所以采用FileProvider是因为7.0以后，对分享本App文件存在着严格的权限检查.

二、通过设置静态类中的静态变量进行数据交换. 一般适用于同一进程内，这样本质上数据在内存中只存在一份，通过静态类进行传递. 需要注意的是进行数据校对，以防多线程操作导致的数据显示混乱.

## handler

- 处理器 Handler
- 消息队列 MessageQueue
- 循环器 Looper

## 自定义View

### onMeasure()

```java
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);

        setMeasuredDimension(width, height);
}
```

如果自定义属性格式为dimension又支持enum，那么我们在获取属性值的时候，必须使用getLayoutDimension,不可使用getDimensionPixelSize.

### onDraw()

不考虑父控件大小，在父控件为空进行测量，

## 点击事件传递

父到子, 子返回false 最终返回到父

```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    //请求所有父控件不要拦截Touch事件
    getParent().requestDisallowInterceptTouchEvent(true);
    return super.dispatchTouchEvent(ev);
}
```

父控件对点击事件进行拦截，子控件会收到什么消息？

## APK打包流程

 gradle 打包

 ```gradlew assembleRelease```  命令打包

### 多apk打包

```gradle
 productFlavors {
      aaa {
            applicationId 'com.aaa'
            minSdkVersion 21
            targetSdkVersion 30
            versionCode 1
            versionName "1.0.0"
        }
        bbb {
            applicationId 'com.bbb'
              minSdkVersion 21
            targetSdkVersion 30
            versionCode 1
            versionName "1.0.0"
        }
 }
```

通过下面的步骤创建源集目录:

1. 打开 Project 窗格并从窗格顶端的下拉菜单中选择 Project 视图.
2. 导航至 MyProject/app/src/.
3. 右键点击 src 目录并选择 New > Folder > Java Folder.
4. 从 Target Source Set 旁边的下拉菜单中，选择需要创建源集的build flavor名称.
5. 点击 Finish.

Android Studio 将会为我们选择的构建类型创建源集目录，然后在该目录内部创建 java 目录. 或者，也可以让 Android Studio 创建相关目录.

例如:

1. 在该 Project 窗格中，右键点击 src 目录并选择 New > XML > Values XML File; 输入名称
2. 从 __Target Source Set__ 旁边的下拉菜单中，选择aaa.
3. 点击 Finish.

### 多渠道打包

```xml
<meta-data android:value="Channel ID" android:name="UMENG_CHANNEL"/>
<meta-data android:name="UMENG_CHANNEL" android:value="${UMENG_CHANNEL_VALUE}" />
```

```gradle
android {  
    productFlavors {
        huawei {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "huawei"]
        }
        xiaomi {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "xiaomi"]
        }
        qh360 {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "qh360"]
        }
        baidu {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "baidu"]
        }
        wandoujia {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "wandoujia"]
        }
    }  
}

```gradle
//可以批量修改打包渠道配置
android {
    productFlavors {
        huawei {}
        xiaomi {}
        qh360 {}
        baidu {}
        wandoujia {}
    }
    productFlavors.all { 
        flavor -> flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name] 
    }
}
```

## Dalivk虚拟机和java虚拟机差别

就是Dalvik基于寄存器，而JVM基于栈

## 工具

Android 扫码工具 ML Kit

## 安全

okhttp  不走系统代理  OkhttpClient.Builder().proxy(Proxy.NO_PROXY);

https 自签名验证证书和双向验证证书

验证app证书是否正确

## View 的invalidate\postInvalidate\requestLayout方法

invalidate 会调用onDraw进行重绘，只能在主线程. 刷新当前View，使当前View进行重绘，不会进行测量、布局流程，因此如果View只需要重绘而不需要测量，布局的时候，使用invalidate方法往往比requestLayout方法更高效

postInvalidate 可以在其他线程

requestLayout 会调用onLayout和onMeasure，不一定会调用onDraw. 如果子View调用了这个方法，其实会从View树重新进行一次测量、布局、绘制这三个流程

## 更新UI方式

Activity.runOnUiThread(Runnable)
View.post(Runnable),View.postDelay(Runnable,long)
Handler

## javascript java 交互

```java
webView.evaluateJavascript("javascript:saveImage()", new ValueCallback<String>() {
```

## 线性布局 相对布局 效率哪个高

相同层次下，因为相对布局会调用两次measure，所以线性高;
当层次较多时，建议使用相对布局

RelativeLayout分别对所有子View进行两次measure，横向纵向分别进行一次，这是为什么呢？首先RelativeLayout中子View的排列方式是基于彼此的依赖关系，而这个依赖关系可能和布局中View的顺序并不相同，在确定每个子View的位置的时候，需要先给所有的子View排序一下. 又因为RelativeLayout允许A，B 2个子View，横向上B依赖A，纵向上A依赖B. 所以需要横向纵向分别进行一次排序测量.  mSortedHorizontalChildren和mSortedVerticalChildren是分别对水平方向的子控件和垂直方向的子控件进行排序后的View数组.

与RelativeLayout相比LinearLayout的measure就简单的多，只需判断线性布局是水平布局还是垂直布局即可，然后才进行测量:

## 看不到activity初启动异常信息

一般是APP启动是就奔溃了, 用loader加载activity, activity崩溃是就会有信息

## android 单元测试

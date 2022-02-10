# Base knowledge of Android

通过intent的bundle的源码可以看到它们都是实现了Parcelable，其实就是通过序列化来实现通信的. 大小限制 1M.

startActivity携带的数据会经过BInder内核再传递到目标Activity中去，因为binder映射内存的限制，所以startActivity也就会这个限制了

一、写入临时文件或者数据库，通过FileProvider将该文件或者数据库通过Uri发送至目标. 一般适用于不同进程，比如分离进程的UI和后台服务，或不同的App之间. 之所以采用FileProvider是因为7.0以后，对分享本App文件存在着严格的权限检查.

二、通过设置静态类中的静态变量进行数据交换. 一般适用于同一进程内，这样本质上数据在内存中只存在一份，通过静态类进行传递. 需要注意的是进行数据校对，以防多线程操作导致的数据显示混乱.

## handler

- 处理器 Handler
- 消息队列 MessageQueue
- 循环器 Looper

Looper 利用的是Linux 的pipe/epoll机制. 在主线程的MessageQueue没有消息时，便阻塞在loop的queue.next()中的nativePollOnce()方法里，此时主线程会释放CPU资源进入休眠状态，直到下个消息到达或者有事务发生，通过往pipe管道写端写入数据来唤醒主线程工作.

## 自定义View

measure->layout->draw

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

view.dispatchTouchEvent();// 在这里判断enable
view.onTouch();
view.onTouchEvent();
view.onClick();

按下View与抬起View，会回调两次onTouch()

```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    //请求所有父控件不要拦截Touch事件
    getParent().requestDisallowInterceptTouchEvent(true);
    return super.dispatchTouchEvent(ev);
}
```

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

一般是APP启动时就奔溃了, 用loader加载activity, activity崩溃时就会有信息.

## Glide

发起请求->执行解码任务->加载图片
into->run->onResourceReady

TargetTracker

待运行 PENDING
已清除 CLEARED
待测量 WAITING_FOR_SIZE
运行中 RUNNING
已完成 COMPLETE
失败 FAILED

## GPU加速

```xml
android:hardwareAccelerated="true"
```

## Rtsp 视频播放

NodeMedia:NodeMediaClient

opencv (需要二次开发)

## 打包arr , 包含依赖的 aar

添加

```gradle
plugins {
    id 'com.android.library'
}
```

按错误修改即可打包`aar`

### 添加依赖的aar

参考: <https://stackoverflow.com/a/52720958/6631835>

```gradle
configurations {
    customConfig.extendsFrom implementation
}
dependencies {
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.navigation:navigation-fragment:2.3.5'
    implementation 'androidx.navigation:navigation-ui:2.3.5'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
    implementation fileTree( include: ['*.jar','*.aar'],dir: 'libs')
}

// 执行这这
task copyLibs(type: Copy) {
    print(configurations.customConfig)
    from configurations.customConfig
    into 'libs'
}
```

## Okhttp 双向验证

### 证书制作

客户端持有服务端的公钥证书，并持有自己的私钥
服务端持有客户的公钥证书，并持有自己私钥

生成客户端keystore,证书(cer)

```sh
keytool -genkeypair -alias client -keyalg RSA -validity 3650 -keypass 123456 -storepass 123456 -keystore client.jks
keytool -export -alias client -file client.cer -keystore client.jks -storepass 123456 
```

生成服务端keystore,证书(cer)

```sh
keytool -genkeypair -alias server -keyalg RSA -validity 3650 -keypass 123456 -storepass 123456 -keystore server.keystore
keytool -export -alias server -file server.cer -keystore server.keystore -storepass 123456 
```

交换证书  
将客户端证书导入服务端keystore中，再将服务端证书导入客户端keystore中， 一个keystore可以导入多个证书，生成证书列表。

```sh
# 服务端证书导入Android
keytool -import -v -alias server -file server.cer -keystore server.jks -storepass 123456
# 客户端导入服务端
keytool -import -v -alias client -file client.cer -keystore server.keystore -storepass 123456 
```

生成Android使用的BKS库文件 , 工具下载地址<https://sourceforge.net/projects/portecle/>

将证书放在android客户端assert目录(能读取到的目录就可以)

```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .sslSocketFactory(getSSLCertifcation(context))//为OkHttp对象设置SocketFactory用于双向认证
    .hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO 根据实际情况返回
                return true;
            }
        })
    .build();

private final static String CLIENT_PRI_KEY = "client.bks";
private final static String TRUSTSTORE_PUB_KEY = "serve.bks";
private final static String CLIENT_BKS_PASSWORD = "123456";
private final static String TRUSTSTORE_BKS_PASSWORD = "123456";
private final static String KEYSTORE_TYPE = "BKS";
private final static String PROTOCOL_TYPE = "TLS";
private final static String CERTIFICATE_FORMAT = "X509";

public static SSLSocketFactory getSSLCertifcation(Context context) {
  SSLSocketFactory sslSocketFactory = null;
  try {
    // 服务器端需要验证的客户端证书，其实就是客户端的keystore
    KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);// 客户端信任的服务器端证书
    KeyStore trustStore = KeyStore.getInstance(KEYSTORE_TYPE);//读取证书
    InputStream ksIn = context.getAssets().open(CLIENT_PRI_KEY);
    InputStream tsIn = context.getAssets().open(TRUSTSTORE_PUB_KEY);//加载证书
    keyStore.load(ksIn, CLIENT_BKS_PASSWORD.toCharArray());
    trustStore.load(tsIn, TRUSTSTORE_BKS_PASSWORD.toCharArray());
    ksIn.close();
    tsIn.close();
    //初始化SSLContext
    SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TYPE);
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(CERTIFICATE_FORMAT);
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(CERTIFICATE_FORMAT);
    trustManagerFactory.init(trustStore);
    keyManagerFactory.init(keyStore, CLIENT_BKS_PASSWORD.toCharArray());
    sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null); 
    sslSocketFactory = sslContext.getSocketFactory();
  } catch (KeyStoreException e) {
      // TODO 处理异常
  } 
  return sslSocketFactory;
}
```

## android 单元测试

# Unity与Android 原生交互

## 安卓端接口

1. 创建一个Java类文件.
2. 构建一个静态函数,用以给Unity返回类实例.
3. 构建一个函数, 让Unity调用. 因为调用静态函数不允许传参.

```java
public class Service {
    public static Service _instance;

    /**
     * @return service
     */
    @SuppressWarnings("unused")
    public static Service getInstance() {
        if (_instance == null) {
            _instance = new Service();
        }
        return _instance;
    }

    @SuppressWarnings("unused")
    public void invokeJava(Context context) {
            Intent intent = new Intent(context, WebViewActivity.class);
            context.startActivity(intent);
    }
}
```

## Unity 调用

```cs
var m_jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
AndroidJavaClass jc = new AndroidJavaClass("com.google.Service");//包名+Java类名
AndroidJavaObject m_Android = jc.CallStatic<AndroidJavaObject>("getInstance");// 调用getInstance
AndroidJavaObject m_AndroidPluginObj = m_jc.GetStatic<AndroidJavaObject>("currentActivity");
if (m_Android != null)
{
    // m_AndroidPluginObj 就是 android 端的 context
    m_Android.Call("invokeJava", m_AndroidPluginObj);
}
```

## Android 向Unity发送消息

引入`unity.jar`(可以向Unity开发人员要一个, 或者网上下载)

```gradle
    implementation files('libs/unity.jar')
```

```java
UnityPlayer.UnitySendMessage("NetLogic", "CallbackWxLogin", "参数");
```

## 打包aar

修改

```gradle
plugins {
    id 'com.android.application'
}
```

为

```gradle
plugins {
    id 'com.android.library'
}
```

移除 ```applicationId```;

配置`buildTypes`, 如下参考(signingConfig 可以删除)

```gradle
 buildTypes {
        release {
            shrinkResources false
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
        debug {
            shrinkResources false
            minifyEnabled false
            signingConfig signingConfigs.release
        }
    }
```

在guadle 里点击 `assembleRelease` 或者运行命令```gradle  assembleRelease```; 最后在 `./app/build/outputs/aar/` 即可找到`app-release.aar`(或`debug`);

如果 android 端 依赖了 `unity.jar` 则 需要删除.

## 坑

### 在unity 打包时遇到方法数限制问题

#### Android 端修改

添加依赖

```gradle
implementation 'com.android.support:multidex:1.0.3'
```

添加`multiDexEnabled true`

```gradle
    defaultConfig {
        minSdkVersion 23
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        multiDexEnabled true
    }
```

创建 `App` 文件继承自 Application;

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
```

android Mainfest 的 application 添加 `android:name=".App"`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.google.android">

    <uses-permission android:name="android.permission.INTERNET" />
    <application android:name=".App"
        android:usesCleartextTraffic="true">
        <activity
            android:name="com.google.WebViewActivity"
            android:exported="true">
        </activity>
    </application>

</manifest>
```

重新打aar包.

#### *

修改Unity default gradle(网上搜索); 添加依赖

```gradle
implementation 'com.android.support:multidex:1.0.3'
```

添加`multiDexEnabled true`

```gradle
defaultConfig {
    minSdkVersion **MINSDKVERSION**
    targetSdkVersion **TARGETSDKVERSION**
    applicationId '**APPLICATIONID**'
    ndk {
        abiFilters **ABIFILTERS**
    }
    versionCode **VERSIONCODE**
    versionName '**VERSIONNAME**'
    multiDexEnabled true
}
```

打包APK.

### Android 端依赖了第三方包(包括 `.so` 文件(NDK))

#### 方法一

下载对应的aar或者jar 等 添加到项目中;

#### 方法二 使用gradle 线上依赖方式

修改Unity default gradle(网上搜索); 添加对应依赖

#### **

有部分 `sdk` 对cpu架构有要求, 需要 `unity` 端开启对应架构.

#### 转 Android 打包

一些 `app` 用 `unity` 打包会出现错误, 转成 `Android` 工程, 使用 `AndroidStudio` 打包.

#### 混淆配置

#### 包依赖

一些项目可能缺少一些包, 根据错误找对应的包.

````gradle
  implementation 'com.android.support:appcompat-v7:28.0.0'
````

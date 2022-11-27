#

<https://developer.android.google.cn/jetpack/androidx/explorer?hl=zh-cn>

[演示https://github.com/android/sunflower](https://github.com/android/sunflower)

## Activity

访问基于 Activity 构建的可组合 API。

onCreate()->onStart()->onResume()->onPause()->onStop()->onRestart()->->onStart()
onDestroy()

自定义权限控制其他人访问这个activity

```xml
//应用A
<manifest
  xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.test.myapp" >
    
    <permission
      android:name="com.test.myapp.permission.DEADLY_ACTIVITY"
      android:permissionGroup="android.permission-group.COST_MONEY"
      android:protectionLevel="dangerous" />
    
     <activity
            android:name="MainActivity"
            android:exported="true" 
            android:permission="com.test.myapp.permission.DEADLY_ACTIVITY">
       </activity>
</manifest>

//应用B
<manifest
  xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.test.otherapp" >
    
    <uses-permission android:name="com.test.myapp.permission.DEADLY_ACTIVITY" />
</manifest>
```

视图绑定

```java
viewBinding {
          enabled = true
      }
tools:viewBindingIgnore="true"
binding = ResultProfileBinding.inflate(getLayoutInflater());
binding = ResultProfileBinding.inflate(inflater, container, false);
```

数据绑定

```xml
  buildFeatures {
        dataBinding = true
    }
android:text="@={viewmodel.text}"
android:onClick="@{(view)->viewmodel.viewClick(view)}"
<import type="android.view.View" />
<import type="android.text.TextUtils" />
```

## appcompat

允许在平台旧版 API 上访问新 API

`AppCompatActivity` 带标题栏的Activity

ShareActionProvider 在菜单栏集成分享功能。
通过setShareIntent(Intent intent)方法可以在Menu里设置要分享的内容

## camera

构建移动相机应用。

## databinding 和 MutableLiveData, ViewModel

使用声明性格式将布局中的界面组件绑定到应用中的数据源。

`MutableLiveData` 可以直接应用. 不再需要`ObserveFiled`

避免在 ViewModel 中引用 View 或 Activity 上下文。如果 ViewModel 存在的时间比 Activity 更长（在配置更改的情况下），Activity 将泄漏并且不会获得垃圾回收器的妥善处置。

## compose

<https://developer.android.google.cn/jetpack/compose/tutorial?hl=zh-cn>

compose.animation 在 Jetpack Compose 应用中构建动画，丰富用户的体验

compose.compiler 借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。

compose.foundation 使用现成可用的构建块编写 Jetpack Compose 应用，还可扩展 Foundation 以构建您自己的设计系统元素。

compose.material 使用现成可用的 Material Design 组件构建 Jetpack Compose UI。这是更高层级的 Compose 入口点，旨在提供与 www.material.io 上描述的组件一致的组件。

compose.runtime Compose 的编程模型和状态管理的基本构建块，以及 Compose 编译器插件针对的核心运行时。

compose.ui 与设备互动所需的 Compose UI 的基本组件，包括布局、绘图和输入。

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text (text = "Hello $name!")
}
```

## Fragment

## hilt

Hilt 通过为项目中的每个 Android 类提供容器并自动管理其生命周期，提供了一种在应用中使用 DI（依赖项注入）的标准方法

<https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn>

## lifecycle

```java
public class MyObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
        ...
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
        ...
    }
}

myLifecycleOwner.getLifecycle().addObserver(new MyObserver());
```

```kotlin
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myLocationListener = MyLocationListener(this) { location ->
            // update UI
        }
       lifecycle.addObserver(myLocationListener)
    }



    internal class MyLocationListener (
            private val context: Context,
            private val callback: (Location) -> Unit
    ): LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start() {

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop() {
            // disconnect if connected
        }
    }
```

避免在 ViewModel 中引用 View 或 Activity 上下文。如果 ViewModel 存在的时间比 Activity 更长（在配置更改的情况下），Activity 将泄漏并且不会获得垃圾回收器的妥善处置。

## room  

创建、存储和管理由 SQLite 数据库支持的持久性数据(JPA)

## test

在 Android 中进行测试

## car

为支持 Android 技术的汽车开发驾驶友好型应用。

<https://developer.android.google.cn/training/cars?hl=zh-cn>

## media-compat , media2

媒体应用架构

media2

## Autofill

通过扩展提示提高输入框自动填充的准确性。

## security

安全地管理密钥并对文件和 sharedpreferences 进行加密 , 对文件进行加密

## TV

 tvprovider  提供 Android TV 频道。

recommendation 将内容推送到 Android TV 启动器的主屏幕。

leanback 使用适合 dpad 的微件和模板 Fragment 为 Android TV 设备编写应用。

## Navigation

Navigation 会销毁fragment

## WorkManager

后台定时运行任务, 我试了几次不能成功, 可能是不是原生的问题.

## DownloadManager

## [Preference](https://developer.android.google.cn/guide/topics/ui/settings?hl=zh-cn)

无需与设备存储空间交互，也不需要管理界面，便能构建交互式设置画面.

设置界面

## DataStore

如果您当前在使用 SharedPreferences 存储数据，请考虑迁移到 DataStore。

```java
RxDataStore<Preferences> dataStore =
  new RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build();
  Flowable<Integer> exampleCounterFlow =
  dataStore.data().map(prefs -> prefs.get(EXAMPLE_COUNTER));
  // write
  Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
  MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
  Integer currentInt = prefsIn.get(INTEGER_KEY);
  mutablePreferences.set(INTEGER_KEY, currentInt != null ? currentInt + 1 : 1);
  return Single.just(mutablePreferences);
});
```

## 共享

```xml
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is text to send.")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
    
    
    <activity android:name=".ui.MyActivity" >
        <intent-filter>
            <action android:name="android.intent.action.SEND" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="text/plain" />
        </intent-filter>
    </activity>
```

## Slice

在应用外显示模板化界面元素。

Slice 是一种界面模板，可以在 Google 搜索应用中以及 Google 助理中等其他位置显示您应用中的丰富而动态的互动内容。Slice 支持全屏应用体验之外的互动，可以帮助用户更快地执行任务。您可以将 Slice 构建成为与应用有关的 Action的增强功能。

在Google 助理中可以显示你的应用提供的内容

## Emoji

<https://developer.android.google.cn/guide/topics/ui/look-and-feel/emoji-compat?hl=zh-cn>

EmojiCompat 支持库旨在让 Android 设备及时兼容最新的表情符号。它可防止您的应用以`☐`的形式显示缺少的表情符号字符，该符号表示您的设备没有用于显示文字的相应字体。通过使用 EmojiCompat 支持库，您的应用用户无需等到 Android OS 更新即可获取最新的表情符号。

## Palette

获取 图片中的暗色，亮色，鲜艳颜色，柔和色，文字颜色，主色调

## ConstraintLayout 关系布局 CoordinatorLayout , CollapsingToolbarLayout

Framlayout->linearLayout->ConstraintLayout

 在 `ConstraintLayout` 中，您可以使用 `ConstraintSet` 和 `TransitionManager` 为尺寸和位置元素的变化添加动画效果。]

 ConstraintSet 仅对子元素的大小和位置设置动画。它们不会为其他属性（如颜色）设置动画。

 需要指定初始布局和结束布局

## MotionLayout  动画布局

<https://developer.android.google.cn/training/constraint-layout/motionlayout/examples?hl=zh-cn>

## 动画

帧动画 ，补间动画

属性动画
AnimationDrawable 加载一系列可绘制资源以创建动画

AnimatedVectorDrawable,VectorDrawable 支持SVG

### 基于物理特性的动画

[运用弹簧物理学原理为图形运动添加动画](https://developer.android.google.cn/guide/topics/graphics/spring-animation?hl=zh-cn)

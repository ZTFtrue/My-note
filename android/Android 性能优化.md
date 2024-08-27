# Android 性能优化

一般来说，程序的性能通过以下三个方面来表现:

1. 程序的响应速度和时间.
2. 程序的启动时间
3. 内存使用量或者是否存在内存泄漏

一些可以用于定量评测的性能指标有:响应时间,内存占用,磁盘I/O,网络I/O,CPU时间等.

## 开发时优化

### 系统设计
  
1. 选择合适的框架,合适的架构,依赖库时注意其性能和兼容性.
2. 复杂计算可以使用C/C++(NDK 开发)
3. 按需求控制消耗内存还是消耗CPU(空间和时间的关系), 比如可以缓存一些常用的变量在内存中.
4. 控制好服务,线程的创建和使用.  
5. 在网络上可以自定义协议, 如果可以,也可以使用PB协议等现有适合的协议.
6. 如果你的APP比较大, 尽量在启动时初始化好一些常用数据, 不要太单纯的仅仅在那边放广告,减少使用广播.
7. 不是特别需要的SDK 可以放到后边初始化

### 代码调优

1. 比如应该使用LinkedList还是ArrayList. 使用一些合适的设计模式, 比如单例模式(注意内存泄漏),工厂模式,代理模式,观察者模式等.谨慎使用静态变量, 如果不会再使用时记得清空
2. 控制代码质量, 多数人写代码是不在乎警告的, 这一点非常不好, 出现警告是因为编译器检查出可能有内存泄漏,空指针异常,频繁创建对象(常见的onDraw中创建画笔)等, 修复警告可以极大避免运行时出现的错误.
3. 过期的API尽量不要使用. 布局时减少不必要的XML嵌套,使用include和merge增加复用, 如有必要可以使用Java实现XML的功能, 减少解析XML耗时, 为了用户体验可以适当的增加或删减动画.

## 后期分析



## UI 优化

## 一些Android中常见却没人注意的Bug

1. 首屏问题: 除去某些设计不给跳过广告,跳过按钮是假的,或者跳过就是点击之外, 有一种应该就不是设计的问题了. 启动首屏广告页之后, APP回到后台,用户进入桌面,过几秒APP会自动回到前台. 这个问题原因很简单,我就不说了
2. 微信在信号不好情况下, 会弹出切换网络(节点)的建议对话框,如果此时微信在后台,微信就会悄悄的崩溃. 这个问题不是什么后台保存状态的问题, 仅仅是Activity onpuse 时就不要在再操作和他相关的内容了, 比如更改UI,弹出对话框, 如果想做就把操作记下来, onresume 时再去做.

## 内存泄漏

1. 非静态内部类和匿名內部类Handler、Thread、Runnable 持有外部类Activity的引用, 导致当关闭activity时，线程未完成造成内存泄漏. 一般 AndroidStudio 会有警告.  Timer 计时器.

2. 资源未及时关闭造成的内存泄漏

    广播BraodcastReceiver, 需要注销`unregisterReceiver()`; 记得如果不做会报一个不会导致崩溃的异常.

    文件流File `close()` 还有一种可以自己关闭忘了, 叫什么了`try(InputStrem input=)`

    数据库游标Cursor ,关闭游标`cursor.close()`;

    ~~Bitmap, 不再被使用时，应调用 recycle() 回收此对象的像素所占用的内存. 无需设为null.~~  
    <https://developer.android.com/topic/performance/graphics/manage-memory#java>  
    <https://stackoverflow.com/questions/3823799/android-how-does-bitmap-recycle-work>

    无限循环动画: 如果在Activity中播放这类动画并且在onDestroy中没有去停止动画，那么这个动画将会一直播放下去，这时候Activity会被View所持有，从而导致Activity无法被释放。在Activity中onDestroy去调用`cancel()`来停止动画。

## 线程控制

提供基础线程池供各个业务线使用, 或者使用 Rxjava.

- Schedulers.io() 非CPU密集的I/O工作, 线程池数量没有限制;
- Schedulers.computation() 执行CPU密集的工作, 线程池数量有限制;
- Schedulers.newThread() 创建一个全新的线程来完成工作(用于在一个完全分离的线程中开始一项长时间运行、隔离的一组任务)
- Schedulers.single() 单线程, 按照排队执行任务。
- Schedulers.from(Executor executor) 自定义的Scheduler，它是由我们自己的Executor作为支撑的Scheduler.from(Executors.newFixedThreadPool(n))
- AndroidSchedulers.mainThread() 需要引入库[RxAndroid](https://github.com/ReactiveX/RxAndroid) , AndroidSchedulers.from(Looper looper)

Thread.currentThread().setName()  排查问题时方便方便查找

// import android.os.Process; 不要导错
`Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);` 和 `Thread.setPriority` 可以当前工作线程设置优先级, 越小越高(<https://stackoverflow.com/questions/5198518/whats-the-difference-between-thread-setpriority-and-android-os-process-setthr>)

关键异步任务耗时监控，可以使用`aspectj`或`Android Studio` 自带的`Profiler CPU`方式或做监控。

## IntentService

## UI

选择Layout顺序：优先FrameLayout>LinearLayout(baselineAligned 默认开启文本基线对齐, 可以关闭) >ConstraintLayout

### ViewStub  不可见，零尺寸

当ViewStub被设置为visible或者调用inflate方法时，指定的layout会被加载填充， 被加载的layout将代替ViewStub添加到ViewStub的父布局中.

对于部分一开始不使用的layout，可以采用此种方式加载，提高初次inflate和渲染主界面的速度。

它的绘制方法是空的. 一般View 中 执行 visible 也会调用自己的draw

### include,merge

### 减少过度绘制(Overdraw)

一般移除多余的背景.

不显示的布局不绘制或刷新.

### Canvas.clipRect/quickReject

Canvas.clipRect()可以定义绘制的边界，边界以外的部分不会进行绘制。

Canvas.quickReject() 可以用来测试指定区域是否在裁剪范围之外，如果要绘制的元素位于裁剪范围之外，就可以直接跳过绘制步骤。

### 自定义控件防止重新布局

requestLayout , onSizeChanged

### SparseArray

## 计算绘制时间

Added in API level 24 (Android7.0)

Set an observer to collect frame stats for each frame rendered in this window. Must be in hardware rendering mode.

```java
        protected void onCreate(Bundle savedInstanceState) {
            // 渲染性能测量
            HandlerThread handlerThread = new HandlerThread("FrameMetrics");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());

            getWindow().addOnFrameMetricsAvailableListener(new Window.OnFrameMetricsAvailableListener() {
                  /**
    * window – The Window on which the frame was displayed.
    * frameMetrics – the available metrics. This object is reused on every call and thus this reference is not valid outside the scope of this method.
    * dropCountSinceLastInvocation – the number of reports dropped since the last time this callback was invoked.
    */
                @Override
                public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {

                    // 先备份数据，然后读取
                    FrameMetrics fm = new FrameMetrics(frameMetrics);
                    //  动画执行回调时间, 单位纳秒     fm.getMetric(FrameMetrics.ANIMATION_DURATION)
                    //  向 GPU 发送绘制命令花费的时间, 单位纳秒  fm.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION)
                    //  ...
                }
            },handler);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
```

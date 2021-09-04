# java 的引用 强、软、弱、虚

## 强引用 ---用的最多

```java
Object o=new Object();
```

> 当内存不足时,只要有依赖，如: A a=new A(o) , jvm也不会回收 ,使用完记得 a=null,o 才会回收

## 软引用

对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象. 因此，这一点可以很好地用来解决OOM的问题，并且这个特性很适合用来实现缓存: 比如网页缓存、图片缓存等.

```java
Object o=new Onject();
SoftReference softReference=new SoftReference( o );
if(softReference.get()!=null){

}else{
    o=new Onject();
    softReference=new SoftReference( o );
}
```

## 弱引用

其与软引用的区别在于: 在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存. 但是，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象,可手动调用GC.

```java
Object o = new Object();
WeakReference<Object> studentWeakRef = new WeakReference<Object>(o);
```

## 虚引用

没看到过哪在用，与其他几种引用都不同，虚引用并不会决定对象的生命周期. 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收. 虚引用用来跟踪对象被垃圾回收器回收的活动.

## 缓存介绍 LUR

[LruCache是android提供的一个缓存工具类，其算法是最近最少使用算法.](https://developer.android.com/reference/android/util/LruCache)

LRU是当内存满时，会优先淘汰那些近期最少使用的缓存对象. 采用LRU算法的缓存有两种: LrhCache和DisLruCache，分别用于实现内存缓存和硬盘缓存，其核心思想都是LRU缓存算法.

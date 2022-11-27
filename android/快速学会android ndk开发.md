# 快速学会android ndk开发

## 下载ndk

## 创建一个示例

1. 打开androidstudio 新建工程(一定要新建).
2. 选择```Phone and Tablet -> Native C++```(向下滑动), 点击```Next```

    ![example image](./img/ndk-1.png)

    这一步可以选择```C++```版本,不过Android不一定能够完全支持该```C++```版本的特性,而且依赖于ndk version.

    ![example image select toolchain](./img/ndk-2.png)

3. 点击```finish``` , 进入工程窗口, ```MainActivity.java``` 的代码就包含了如何调用```C++```

## 分析已有代码

在```MainActivity.java``` 中:

```java
static {
    System.loadLibrar("native-lib");
}
```

这段代码加载一个名为```native-lib```的```C++```库

```native-lib``` 就在你的工程中:

![native-lib](./img/native-lib.png)

打开```CMakeLists.txt```

```cmake
add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             native-lib.cpp )
```

上面代码中```native-lib``` 可以修改为想要的名字. 如果需要修改, 这里的```System.loadLibrar("native-lib");```代码也要修改一下. ```native-lib.cpp``` 就是左侧的那个```cpp```了, 也可以修改他的名字.

再次回到```MainActivity.java``` 中:

```java
public native String stringFromJNI();
```

如果想要通过```java```调用```c++```, 必须使用```native``` 关键字.```String``` 是```c++```返回的数据类型. 先记着```stringFromJNI```.

打开```native-lib.cpp```:

```c++
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ztftrue_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
```

```extern "C"``` 使编译器不会修改破坏名称,可以方便```java```中调用[参考stackoverflow](https://stackoverflow.com/questions/1041866/what-is-the-effect-of-extern-c-in-c).

```JNIEXPORT jstring JNICALL``` 告诉编译器下面那段代码(方法)要被```java```调用.

```Java_com_ztftrue_myapplication_MainActivity_stringFromJNI```不同的工程这行是不一样的,自己观察一下(和你的包名做个对比),发现其中的规律. ```stringFromJNI``` 就是java中的那个方法名了.

```JNIEnv* env, jobject``` 一个是```JNIEnv```的指针(来自jni),```jobject```就是指向加载的```activity```(this).

```return env->NewStringUTF(hello.c_str());``` 返回一个字符串.java 中调用```stringFromJNI```,```tv.setText(stringFromJNI());```.

可以修改```native-lib.cpp```中的```std::string hello``` 运行工程,看看效果.

## 基础需求完善

### java向c++ 传递参数

在```java``` 中:

```java
public native String getJobs(long myName, String profilePath);
```

在```c++``` 中:

```c++
extern "C"{
JNIEXPORT jstring JNICALL
java_这里你自己包名_类名_getJobs(
        JNIEnv* env,
        jobject /* this */,
        jlong myName, jstring profilePath) {
        // jstring2string 方法在下边的代码中
        std::string nativeProfilPath = jstring2string(env, profilePath);
    return env->NewStringUTF(nativeProfilPath.c_str());
}
}
```

```jstring``` 转换成 ```std::string```

```c++
std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const auto stringClass = env->GetObjectClass(jStr);
    const auto getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const auto stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                 env->NewStringUTF("UTF-8"));

    auto length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte *pBytes = env->GetByteArrayElements(stringJbytes, nullptr);

    std::string ret = std::string((char *) pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}
```

一些类型定义,可以在这里找到原文件```.toolchains/llvm/prebuilt/linux-x86_64/sysroot/usr/include/jni.h```, 在```androidstudio``` 中,也可以点击```jlong```的定义而找到:

```c++
typedef uint8_t  jboolean; /* unsigned 8 bits */
typedef int8_t   jbyte;    /* signed 8 bits */
typedef uint16_t jchar;    /* unsigned 16 bits */
typedef int16_t  jshort;   /* signed 16 bits */
typedef int32_t  jint;     /* signed 32 bits */
typedef int64_t  jlong;    /* signed 64 bits */
typedef float    jfloat;   /* 32-bit IEEE 754 */
typedef double   jdouble;  /* 64-bit IEEE 754 */
```

### c++向java传递参数

``c++`` 代码:

```c++
Java_这里你自己包名_类名_callMe(
        JNIEnv *env,
        jobject obj/* this */) {
    // c->java
    jclass cls = env->GetObjectClass(obj);
    jmethodID receiveObject = env->GetMethodID(cls, "receiveMessage", "(Ljava/lang/String;)V");
    jstring jstr = env->NewStringUTF("This string comes from JNI");
    if (receiveObject == 0) {
        // can't found function
    }
    env->CallVoidMethod(obj, receiveObject, jstr);
}
```

Java代码:

```java
public native void callMe();

public void receiveMessage(String message) {
    Log.d(MainActivity.class.getSimpleName(), message);
}
```

自此NDK基础开发完成, [源代码](https://github.com/ZTFtrue/Ndk-demo).

打开```app/build.gradle``` ,查找字段```externalNativeBuild``` , 如果想要在已有项目增加ndk 开发, 需要修改这里的相关内容 .

```gradle
android {
    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "com.ztftrue.myapplication"
        minSdkVersion 23
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                // cpp 版本
                cppFlags "-std=c++17"
            }
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    externalNativeBuild {
        // cmake  编译
        cmake {
            // cmake 文件路径
            path "src/main/cpp/CMakeLists.txt"
            // cmake 版本
            version "3.10.2"
        }
    }
```

#

建议使用cmake-gui

1. 在github下载源代码，从release下载或者直接 clone 源代码。选择那个要根据编译情况决定，一般情况，如果系统相关包升级偏保守，就可以用release的版本。
2. 打开终端，在终端中打开`cmake-gui`,选择opencv 源代码，选择编译结果目录，一般是新建一个build.
3. 点击`configure` 如果出现错误，就配置环境变量；编辑`.bashrc`文件
   按需添加如下内容：

    ```bash
    export MPI_C=`which mpicc`
    export MPI_CXX=`which mpicxx`
    export LIBVA_DRIVER_NAME=vdpau
    export PATH="$PATH:/opt/cuda/bin"
    export CPATH=/opt/cuda/targets/x86_64-linux/include:$CPATH
    export LD_LIBRARY_PATH=/opt/cuda/targets/x86_64-linux/lib:$LD_LIBRARY_PATH
    ```

4. 选择需要编译的内容，有些内容（nofree,cuda）需要下载 extra 内容，然后搜索添加`OPENCV_EXTRA_MODULES_PATH`:PATH="/home///opencv_contrib-4.5.4/modules"

5. 点击 `Generate`, 根据错误添加如下内容(或搜索)：

    ```bash
    CUDA_ARCH_BIN:STRING="8.0" 
    CUDA_TOOLKIT_ROOT_DIR:PATH="/opt/cuda"
    ```

6. 根据错误筛选内容

    比如有些包用到的sdk 本来就没有，那就取消勾选。如

    ```bash
    BUILD_JAVA:BOOL=0
    WITH_VA_INTEL:BOOL=0
    ```

```cmake
Commandline options:
-DOPENCV_ENABLE_NONFREE:BOOL="1" -DWITH_OPENEXR:BOOL="1" -DENABLE_FAST_MATH:BOOL="1" -DBUILD_opencv_xobjdetect:BOOL="0" -DOPENCV_DNN_CUDA:BOOL="1" -DOPENCV_EXTRA_MODULES_PATH:PATH="/home///opencv_contrib-4.x/modules" -DBUILD_JAVA:BOOL="0" -DBUILD_opencv_java_bindings_generator:BOOL="0" -DWITH_VA_INTEL:BOOL="0" -DOpenEXR_DIR:PATH="/usr/lib/cmake/OpenEXR" -DWITH_CUDA:BOOL="1" -DBUILD_opencv_java:BOOL="0" 


Cache file:
OPENCV_ENABLE_NONFREE:BOOL=1
WITH_OPENEXR:BOOL=1
ENABLE_FAST_MATH:BOOL=1
BUILD_opencv_xobjdetect:BOOL=0
OPENCV_DNN_CUDA:BOOL=1
OPENCV_EXTRA_MODULES_PATH:PATH=/home///opencv_contrib-4.x/modules
BUILD_JAVA:BOOL=0
BUILD_opencv_java_bindings_generator:BOOL=0
WITH_VA_INTEL:BOOL=0
OpenEXR_DIR:PATH=/usr/lib/cmake/OpenEXR
WITH_CUDA:BOOL=1
BUILD_opencv_java:BOOL=0
```
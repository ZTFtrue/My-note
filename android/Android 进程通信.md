# Interprocess Communication 进程间通信

binder，使用文件共享，使用Messenger，通过ALDL，ConetentProvider方式，Socket方式，广播方式，匿名共享内存

## binder的原理

Binder通信机制采用C/S架构

- Binder框架中主要涉及到4个角色Client、Server、Service Manager及Binder驱动，其中Client、Server、Service Manager运行在用户空间，Binder驱动运行在内核空间
- Client代表客户端进程，Server代表客户端进程提供各种服务，如音视频等
- Service Manager用来管理各种系统服务
- Binder驱动提供进程间通信的能力
- 用户空间的Client、Server、ServiceManager通过open、mmap和ioctl等标准文件操作(详见Unix环境编程)来访问/dev/binder，进而实现进程间通信

首先 Binder 驱动在内核空间创建一个数据接收缓存区；
接着在内核空间开辟一块内核缓存区，建立内核缓存区和内核中数据接收缓存区之间的映射关系，以及内核中数据接收缓存区和接收进程用户空间地址的映射关系；
发送方进程通过系统调用 copyfromuser() 将数据 copy 到内核中的内核缓存区，由于内核缓存区和接收进程的用户空间存在内存映射，因此也就相当于 __把数据发送到了接收进程的用户空间__ ，这样便完成了一次进程间的通信. 

有大小限制1M(全局限制)

## AIDL

Binder
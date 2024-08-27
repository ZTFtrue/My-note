// https://banu.com/blog/2/how-to-use-epoll-a-complete-example-in-c/
// http://www.kegel.com/poller/
// https://gist.github.com/dtoma/417ce9c8f827c9945a9192900aff805b
// https://man7.org/linux/man-pages/man7/epoll.7.html
/** Todo
 * - [ ] Split i/o and logic
 * - [ ] Unit test logic
 * - [ ] Logging
 * - [ ] Continuous integration
 *   - [ ] Linux
 *   - [ ] Windows
 *   - [ ] Mac
 * - [ ] Library-fy
 * - [ ] Add support for kqueue
 * - [ ] Add support for windows equivalent
 *
 *
 *
 *
 * epoll是 Linux I/O 的一种多路复用的机制，另外还有select和poll
 *
 * 在这个文件 /proc/sys/fs/epoll/max_user_watches 里的描述限制了，内核中epoll使用的内存总量
 *
 * 源码位置： https://github.com/torvalds/linux/blob/master/fs/eventpoll.c
 * epoll 主要有两个结构体： eventpoll 和 epitem。
 * epitem 是每一个 I/O 对应的事件，比如 EPOLL_CTL_ADD 操作时，就需要创建一个 epitem；
 * eventpoll 是每一个 epoll 所对应的，比如 epoll_create 就是创建一个 eventpoll。
 *
 *
 * 创建 list 队列用来存储就绪的 I/O， 红黑树 用来存储所有 I/O。这样方便快速查找
 * 当内核 I/O 准备就绪时，则执行 epoll_event_callback 回调函数，将 epitem 添加到 list 中；
 * 当 epoll_wait 激活重新运行时，将 list 的 epitem 逐一拷贝到 events 中，
 * 并删除 list 中被拷贝出来的 epitem。
 * 当执行 epoll_ctl(EPOLL_CTL_ADD)操作，将 epitem 添加到 rbtree 中；
 * 当执行 epoll_ctl(EPOLL_CTL_DEL)操作，将对应的 epitem 从 rbtree 中删除。
 *
 * 这个时候如果多线程操作，就需要保证安全
 *
 * list队列 使用最小粒度的锁 spinlock , 便于在 SMP(symmetric multiprocessing，对称多处理)
 * 下添加操作的时候，能够快速操作 list。
 *
 * 对于 rbtree 的操作使用互斥锁
 *
 * 
 *
 * 创建：epoll_ctl
 * 通过系统调用陷入内核: SYSCALL_DEFINE4
 * 当用户调用epoll_wait的时候，线程陷入内核态后并不会轮询所有监控的fd，
 * 而是只关注一个就绪I/O集合, 轮询集合上的文件描述符对应的 poll 接口，获取事件掩码并返回给用户。
 *
 *
 * 就绪I/O集合不使用栈的原因是： 节点一次取不完数据的情况下，可能在下一次继续取的时候也获取不到
 *
 *
 */

#include <sys/epoll.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <fcntl.h>
#include <iostream>
#include <string>
#include <cstring>
#include <netdb.h>
#include <array>

namespace
{
    constexpr int max_events = 32;
    /**
     * 构建 socket
     */
    int create_and_bind(std::string const &port)
    {
        struct addrinfo hints;

        memset(&hints, 0, sizeof(struct addrinfo));
        hints.ai_family = AF_UNSPEC;     /* Return IPv4 and IPv6 choices */
        hints.ai_socktype = SOCK_STREAM; /* TCP */
        hints.ai_flags = AI_PASSIVE;     /* All interfaces */

        struct addrinfo *result;
        int sockt = getaddrinfo(nullptr, port.c_str(), &hints, &result);
        if (sockt != 0)
        {
            std::cerr << "[E] getaddrinfo failed\n";
            return -1;
        }

        struct addrinfo *rp;
        int socketfd;
        for (rp = result; rp != nullptr; rp = rp->ai_next)
        {
            // 创建一个tcp socket
            socketfd = socket(rp->ai_family, rp->ai_socktype, rp->ai_protocol);
            if (socketfd == -1)
            {
                continue;
            }
            // 将socket和地址绑定
            sockt = bind(socketfd, rp->ai_addr, rp->ai_addrlen);
            if (sockt == 0)
            {
                break;
            }

            close(socketfd);
        }

        if (rp == nullptr)
        {
            std::cerr << "[E] bind failed\n";
            return -1;
        }

        freeaddrinfo(result);

        return socketfd;
    }

    auto make_socket_nonblocking(int socketfd)
    {
        // 把socket设置为非阻塞模式
        int flags = fcntl(socketfd, F_GETFL, 0);
        if (flags == -1)
        {
            std::cerr << "[E] fcntl failed (F_GETFL)\n";
            return false;
        }

        flags |= O_NONBLOCK;
        int s = fcntl(socketfd, F_SETFL, flags);
        if (s == -1)
        {
            std::cerr << "[E] fcntl failed (F_SETFL)\n";
            return false;
        }

        return true;
    }

    auto accept_connection(int socketfd, struct epoll_event &event, int epollfd)
    {
        struct sockaddr in_addr;
        socklen_t in_len = sizeof(in_addr);
        // 接受来自socket连接
        int infd = accept(socketfd, &in_addr, &in_len);
        if (infd == -1)
        {
            if (errno == EAGAIN || errno == EWOULDBLOCK) // Done processing incoming connections
            {
                return false;
            }
            else
            {
                std::cerr << "[E] accept failed\n";
                return false;
            }
        }

        std::string hbuf(NI_MAXHOST, '\0');
        std::string sbuf(NI_MAXSERV, '\0');
        if (getnameinfo(&in_addr, in_len,
                        const_cast<char *>(hbuf.data()), hbuf.size(),
                        const_cast<char *>(sbuf.data()), sbuf.size(),
                        NI_NUMERICHOST | NI_NUMERICSERV) == 0)
        {
            std::cout << "[I] Accepted connection on descriptor " << infd << "(host=" << hbuf << ", port=" << sbuf << ")"
                      << "\n";
        }

        if (!make_socket_nonblocking(infd))
        {
            std::cerr << "[E] make_socket_nonblocking failed\n";
            return false;
        }

        event.data.fd = infd;
        event.events = EPOLLIN | EPOLLET;
        // 将新的连接添加到epoll中
        if (epoll_ctl(epollfd, EPOLL_CTL_ADD, infd, &event) == -1)
        {
            std::cerr << "[E] epoll_ctl failed\n";
            return false;
        }

        return true;
    }
    /**
     * 读取数据
     */
    auto read_data(int fd)
    {
        char buf[512];
        auto count = read(fd, buf, 512);
        if (count == -1)
        {
            /**
             * EINTR Interrupted system call
             * https://stackoverflow.com/questions/6870158/epoll-wait-fails-due-to-eintr-how-to-remedy-this
             * 如果有慢的操作调用，比如阻塞的套接字操作，当有一个信号过来中断了这个慢操作时， 就会触发EINTR。
             * 这时根据需要 有的可以设置自动从打断的地方开始，有的需要处理。如果可以重新开始
             * 只需要和 EAGAIN 一样重新读取。
             * epoll_wait 就是一个慢操作
             */
            if (errno == EAGAIN) // read all data
            {
                return false;
            }
        }
        else if (count == 0) // EOF - remote closed connection
        {
            std::cout << "[I] Close " << fd << "\n";
            close(fd);
            return false;
        }
        std::cout << fd << " says: " << buf;
        return true;
    }
}

// 发送消息
int sendMessage(int fd, char *msg)
{
    // 这里不能用 sizeof()
    /**
     * https://stackoverflow.com/questions/8590332/difference-between-sizeof-and-strlen-in-c
     * strlen() is used to get the length of a string stored in an array.
     * sizeof() is used to get the actual size of any type of data in bytes.
     */
    return write(fd, msg, strlen(msg));
}

// 浏览器 直接 http://localhost:4242/
int main()
{
    int socketfd = ::create_and_bind("4242");
    if (socketfd == -1)
    {
        return 1;
    }
    /**
     * 设置为非阻塞模式
     *
     * epoll 事件有两种模型:
     * Level Triggered (LT,水平触发模式) 默认的工作方式, 同时支持 block 和 non-block socket,
     * 如果一个文件描述符就绪，就一直推送，直到你处理
     *
     * Edge Triggered (ET,边缘触发模式), 只支持no-block socket， 如果一个文件描述符就绪，只说一次，
     * 除非又有新的数据
     *
     */
    if (!::make_socket_nonblocking(socketfd))
    {
        return 1;
    }
    // Prepare to accept connections on socket FD. 开始监听socket,当调用listen之后,
    if (listen(socketfd, SOMAXCONN) == -1)
    {
        std::cerr << "[E] listen failed\n";
        return 1;
    }
    // 调用epoll_create来创建一个epoll实例
    // The unused SIZE parameter has been dropped.
    int epollfd = epoll_create1(0);
    if (epollfd == -1) // 创建失败
    {
        std::cerr << "[E] epoll_create1 failed\n";
        return 1;
    }

    struct epoll_event event;
    // union类型，里面的参数只能使用一个，会相互覆盖
    // void*ptr 可以用来实现其它要求
    event.data.fd = socketfd;
    /**
     * EAGAIN 表示重试
     * EPOLLIN  :表示对应的文件描述符可以读（SOCKET正常关闭）
     * EPOLLOUT :表示对应的文件描述符可以写，只要输出缓冲区可写就会回调
     * EPOLLPRI :表示对应的文件描述符有紧急的数据可读（表示有带外数据到来）
     * EPOLLERR :表示对应的文件描述符发生错误(默认注册)
     * EPOLLHUP :表示对应的文件描述符被挂断(默认注册)
     * EPOLLRDHUP ： 表示对应的客户端关闭
     * EPOLLET : 将EPOLL设为边缘触发(Edge Triggered)模式，这是相对于水平触发(Level Triggered)来说的
     * EPOLLONESHOT :只监听一次事件，当监听完这次事件之后，如果还需要继续监听这个socket的话，需要再次把这个socket加入到EPOLL队列里
     * 如果客户端直接断开或者杀死程序，
     * 监听EPOLLIN或EPOLLRDHUP， 而不需要等待心跳超时，如果不指定，就不会返回这事件
     * | 这种算法又是一个小的内容
     *
     * EPOLLOUT : 会出现吴用回调，导致性能下降，它的使用场景是当 当向socket写失败后，
     * 注册上 EPOLLOUT (EPOLL_CTL_MOD), 当响应了可写事件后，重新往socket中写数据，
     * 写成功后，再取移除 EPOLLOUT
     * https://stackoverflow.com/questions/13568858/epoll-wait-always-sets-epollout-bit
     *
     */
    event.events = EPOLLIN | EPOLLET;
    /*
     * 文件描述符:
     *
     *
     * 操作方式:
     * epoll_ctl可以增加、删除一个监控对象
     * EPOLL_CTL_ADD Add a file descriptor to the interface.  增
     * EPOLL_CTL_DEL Remove a file descriptor from the interface. 删
     * EPOLL_CTL_MOD Change file descriptor epoll_event structure. 改
     *
     *
     * 当从一个通道比如管道或者流式套接字读取数据的时候，这个事件只表示对方关闭了通道，
     * 通道依然有缓存数据，但是 epoll_wait 返回0， 这是表示连接断开；如果没有数据并且
     * 返回为 0 表示文件结束
     *
     * 操作对象描述符:
     *
     * 响应事件:
     * 有事件发生时，会通过 &event 所指的对象传递数据，
     * 所以在添加连接时，要把这个连接包装成一个 epoll_event 对象
     * 也可以传 EPOLLIN 或者 EPOLLOUT
     */
    if (epoll_ctl(epollfd, EPOLL_CTL_ADD, socketfd, &event) == -1)
        if (epollfd == -1)
        {
            std::cerr << "[E] epoll_ctl failed\n";
            return 1;
        }

    std::array<struct epoll_event, ::max_events> events;

    while (true)
    {
        /*
         * 等待监控的fd中有事件发生， 此时线程会阻塞在epoll_wait中，
         * 当有关注的事件发生时，阻塞的线程会被唤醒,
         * 通过epoll_ctl添加的参数 &event 所指对象也会返回（修改）,
         * 这样就可以获取事件对应的接口指针了
         *
         * 返回 -1 in case of error with the "errno" variable set to the specific error code.
         * 返回 0 表示结束 或 没有数据缓存
         *
         * 文件描述符: 和 epoll_ctl 一样
         *
         * __events 可以操作的连接数组
         *
         * max_events 可以处理的最大事件数量
         *
         * The "timeout" parameter    specifies the maximum wait time in milliseconds (-1 == infinite).
         *
         */
        int n = epoll_wait(epollfd, events.data(), ::max_events, -1);
        for (int i = 0; i < n; ++i)
        {
            if (events[i].events & EPOLLERR ||
                events[i].events & EPOLLHUP ||
                !(events[i].events & EPOLLIN)) // error
            {                                  // 这里判断是否是关闭
                std::cerr << "[E] epoll event error\n";
                close(events[i].data.fd);
            }
            else if (socketfd == events[i].data.fd) // new connection
            {
                // 接受来自socket连接
                while (::accept_connection(socketfd, event, epollfd))
                {
                }
            }
            else // data to read
            {
                auto fd = events[i].data.fd;
                // 位域
                while (::read_data(fd))
                {
                }
                //向客户端发数据
                char a[] = "HTTP/1.0 200 OK\nContent-Type: text/plain\n\n";
                sendMessage(fd, a);
                char c[] = "Can't find your Object\n";
                sendMessage(fd, c);
                // 用浏览器请求必须有这一步(参考http 原理)
                close(fd);
            }
        }
    }

    close(socketfd);
}

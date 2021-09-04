# DoS攻击、IP地址欺骗 概述

===SYN Flood是一种广为人知的DoS(拒绝服务攻击)是DDoS(分布式拒绝服务攻击)的方式之一，

这是一种利用TCP协议缺陷，发送大量伪造的TCP连接请求，从而使得被攻击方资源耗尽(CPU满负荷或内存不足)的攻击方式. 中文名拒绝服务攻击DoS.

问题就出在TCP连接的三次握手中，假设一个用户向服务器发送了SYN报文后突然死机或掉线，

那么服务器在发出SYN+ACK应答报文后是无法收到客户端的ACK报文的(第三次握手无法完成)，

这种情况下服务器端一般会重试(再次发送SYN+ACK给客户端)并等待一段时间后(约75s)丢弃这个未完成的连接，

这段时间的长度我们称为SYN Timeout；一个用户出现异常导致服务器的一个线程等待1分钟并不是什么很大的问题，

但如果有一个恶意的攻击者大量模拟这种情况，服务器端将消耗非常多的资源----数以万计的半连接，

即使是简单的保存并遍历也会消耗非常多的CPU时间和内存，何况还要不断对这个列表中的IP进行SYN+ACK的重试.

实际上如果服务器的TCP/IP栈不够强大，最后的结果往往是堆栈溢出崩溃---即使服务器端的系统足够强大，

服务器端也将忙于处理攻击者伪造的TCP连接请求而无暇理睬客户的正常请求(毕竟客户端的正常请求比率非常之小)，

此时从正常客户的角度看来，服务器失去响应.

这种情况我们称作: 服务器端受到了SYN Flood攻击(SYN洪水攻击).

针对前面的攻击手段，可以在内部网络与因特网的连接处设置一台防火墙，

防火墙过滤排查所有数据，检查外来IP数据报中的源IP地址.

如果发现是内部网络的IP地址，则禁止通过(因为内部网络直接送达，不会经过这台防火墙).

就能有效的阻止IP地址欺骗. 此外，防火墙也能阻止外来的SYN洪水攻击(DoS攻击).

BTW，防止IP地址欺骗最简单的办法是不使用源IP地址进行进行身份认证.

1、修改等待数
sysctl -w net.ipv4.tcp_max_syn_backlog=2048

2、启用syncookies
sysctl -w net.ipv4.tcp_syncookies=1

3、修改重试次数
sysctl -w net.ipv4.tcp_syn_retries = 0
重传次数设置为0，只要收不到客户端的响应，立即丢弃该连接，默认设置为5次

4、限制单IP并发数
使用iptables限制单个地址的并发连接数量:
iptables -t filter -A INPUT -p tcp --dport 80 --tcp-flags FIN,SYN,RST,ACK SYN -m connlimit --connlimit-above 10 --connlimit-mask 32 -j REJECT

5、限制C类子网并发数
使用iptables限制单个c类子网的并发链接数量:
iptables -t filter -A INPUT -p tcp --dport 80 --tcp-flags FIN,SYN,RST,ACK SYN -m connlimit --connlimit-above 10 --connlimit-mask 24 -j REJECT

6、限制单位时间内连接数
设置如下:
iptables -t filter -A INPUT -p tcp --dport 80 -m --state --syn -m recent --set
iptables -t filter -A INPUT -p tcp --dport 80 -m --state --syn -m recent --update --seconds 60 --hitcount 30 -j DROP

7、修改modprobe.conf
为了取得更好的效果，需要修改/etc/modprobe.conf
options ipt_recent ip_list_tot=1000 ip_pkt_list_tot=60
作用: 记录10000个地址，每个地址60个包，ip_list_tot最大为8100,超过这个数值会导致iptables错误

8、限制单个地址最大连接数
iptables -I INPUT -p tcp --dport 80 -m connlimit --connlimit-above 50 -j D

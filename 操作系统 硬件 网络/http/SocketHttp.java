package com.fixdoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class SocketHttp {

    private static final int PORT = 8080;
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        // 一个 SelectableChannel 对象的多路复用器。
        // 可以通过调用此类的打开方法来 open 一个选择器，
        // 该方法将使用系统的默认 selector provider 来创建一个新的选择器。
        // 也可以通过调用自定义选择器的 openSelector 方法来创建选择器。
        // 选择器将保持打开状态，直到通过其 close 方法关闭。
        // 用于监听多个Channel的各种状态
        Selector selector = Selector.open();
        //  stream-oriented的侦听 sockets 的一个可选择的通道。
        // 通道与流的不同之处在于通道是双向的，流只是在一个方向上移动
        // 创建一个 TCP 套接字通道
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        // Binds the channel's socket to a local address and
        // configures the socket to listen for connections.
        serverSocket.bind(new InetSocketAddress("localhost", PORT));
        //调整通道为非阻塞模式
        serverSocket.configureBlocking(false);
        //向选择器注册一个通道
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        answerWithEcho(key);
                    }
                    iter.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 可以简单的理解http 请求为: 开一个tcp连接, 然后传规定好格式的数据(字符串), 然后关闭连接
     *
     * @param key 选择器与通道的一个关联关系(绳索, 一头选择器, 一头是通道)
     * @throws IOException IOException
     */
    private static void answerWithEcho(SelectionKey key)
            throws IOException {
        // 获取服务通道
        SocketChannel client = (SocketChannel) key.channel();
        String request = parseRequest(client);
        dealResponse(client, request);
        client.close();
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        // 执行阻塞方法
        SocketChannel client = serverSocket.accept();
        // 设置阻塞模式
        client.configureBlocking(false);
        // 客户端的通道注册到多路复用器上，并设置读取标识
        client.register(selector, SelectionKey.OP_READ);
    }

    public static String parseRequest(SocketChannel client) throws IOException {
        int capacity = 256;// 初始容量, 每次读取容量
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        while (client.read(buffer) > 0) {
            if (buffer.position() == buffer.capacity()) {// 扩容
                ByteBuffer bufferTemp = ByteBuffer.allocate(buffer.capacity() + capacity);
                buffer.flip();
                bufferTemp.put(buffer);
                buffer = bufferTemp;
            }
        }

        String request = new String(buffer.array(), buffer.arrayOffset(),
                buffer.arrayOffset() + buffer.position(), StandardCharsets.UTF_8);
        buffer.clear();
        // 打印出请求, 可以看到 它就是字符串
        System.out.println(request);
        return request;
    }

    public static void dealResponse(SocketChannel client, String request) throws IOException {
        // 第一行就是协议和状态, 比如可以换成 HTTP/1.1 404 Not Found
        String response = """
                HTTP/1.1 200 OK
                server: I.com
                """;
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
        client.write(buffer);
        buffer.clear();

        if (request.contains("favicon.ico")) {// 偷懒了
            // 这个空行就是区分头和体的关键
            String image = """
                    content-type: image/jpg

                    """;
            buffer = ByteBuffer.wrap(image.getBytes());
            client.write(buffer);
            // 这一段是简单演示文件的处理, 向浏览器返回 favicon.ico
            // 读取文件
            buffer = ByteBuffer.wrap(Files.readAllBytes(Path.of("f.jpg")));
            client.write(buffer);
        } else {
            // 返回 html 文件
            // 这个空行就是区分头和体的关键
            String html = """
                    content-type: text/html; charset=utf-8

                    <html><head><title>Look at you</title></head><body>找不到你的对象</body></html>
                    """;
            buffer = ByteBuffer.wrap(html.getBytes());
            client.write(buffer);
        }

    }
}

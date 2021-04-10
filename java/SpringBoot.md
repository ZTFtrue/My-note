#

## Springboot 优化

1. 启动时启用数据库链接: @PostContrust 里面"SELECT 1"，让他在启动时候查一下数据库
2. 设置 spring.mvc.servlet.load-on-startup=1  ,  禁用懒加载
3. 加启动参数使用伪随机数, 示例`nohup java -Xmx512M -Xms512M -Djava.security.egd=/dev/urandom -jar run.jar > /dev/null 2>&1 &`

[命令解释 stackoverflow](https://stackoverflow.com/questions/10508843/what-is-dev-null-21)

`>> /dev/null`  redirects standard output (stdout) to /dev/null, which discards it.
The `>>` seems sort of superfluous, since >> means append while `>` means truncate(缩短) and write, and either appending to or writing to /dev/null has the same net effect. I usually just use > for that reason.

`2>&1` file descriptor, redirects standard error (2) to standard output (1), which then discards it as well since standard output has already been redirected.

`&` indicates(标示) a file descriptor. There are usually 3 file descriptors - standard input, output, and error.

## MultiValueMap

MultiValueMap可以让一个key对应多个value, 这是Spring框架提供的Map类.

```java
  MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap<>();
    stringMultiValueMap.add("哇哈哈", "哇");
    stringMultiValueMap.add("哇哈哈", "哈");
    stringMultiValueMap.add("哇哈哈", "哈");
    stringMultiValueMap.add("哇哈哈", "哇");
    stringMultiValueMap.add("哇哈哈", "哈");
    stringMultiValueMap.add("哇哈哈", "哈");
     //打印所有值
    Set<String> keySet = stringMultiValueMap.keySet();
    for (String key : keySet) {
        List <String> values = stringMultiValueMap.get(key); 
        System.out.println(StringUtils.join(values.toArray(), "")+": "+ key);
    }
```

## Error

A bean with that name has already been defined in null and overriding is disabled

复制项目时把

```java
@SpringBootApplication
Application
```

都复制过去了

#

## Protocol Buffer 原理

Protocol Buffer 不同于Json 等, 它不需要将数据转换成string 再解码; 而是在 二进制串 中直接解码. 所以必须提前定义好结构文件.

As you know, a protocol buffer message is a series of `key-value` pairs.

每一对的键实际上是两个值, 来自.proto文件中的字段号，以及一个wire types，它提供了足够的信息来查找以下值的长度。

```text
message Test1 {
  optional int32 a = 1;
}
结果:
08 96 01
```

<https://developers.google.com/protocol-buffers/docs/javatutorial#defining-your-protocol-format>(都是直接翻译这篇的,我也是)

`addressbook.proto`

```proto
syntax = "proto2"; 

package tutorial;//以一个程序包声明开始 防止不同项目之间的命名冲突(必须有)

option java_multiple_files = true;// 分割多个Java 文件, 如有嵌套
option java_package = "com.example.tutorial.protos";// 显示的声明一个包(对于Java)
option java_outer_classname = "AddressBookProtos"; // 类名(如果不定义,就使用大驼峰文件名.比如Person.java)

message Person {
  optional string name = 1;
  optional int32 id = 2;
  optional string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    optional string number = 1;
    optional PhoneType type = 2 [default = HOME];
  }

  repeated PhoneNumber phones = 4;
}

message AddressBook {
  repeated Person people = 1;
}
```

`message` 包含一系列字段集.可用字段:

```text
bool
int32
float
double
string
```

`enum` 枚举类型

required 字段必须提供，否则消息将被认为是 "未初始化的 (uninitialized)"。尝试构建一个未初始化的消息将抛出一个 RuntimeException。解析一个未初始化的消息将抛出一个IOException。(谨慎使用这个字段)

optional 字段可以设置也可以不设置。如果可选的字段值没有设置，则将使用默认值。默认值你可以自己定义，也可以用系统默认值：数字类型为0，字符串类型为空字符串，bools值为false。

repeated 字段可以重复任意多次 (包括0)(相当于JAVA中的list)

default 默认值

后边的数字 "=1","=2" 每个字段单元的唯一标记`tag`. `1-15` 比更大的数字少一个字节来编码, 因此作为一个优化，使用这些标签为常用或重复的元素，留下标签16和更大的作为不太常用的可选元素。重复字段中的每个元素都需要重新编码标签号，因此重复字段是这种优化的最佳候选项。

如果想要给Protocol Buffers 对象更丰富的功能, 需要再写一个包装类.

## 向后兼容

- you must not change the tag numbers of any existing fields.
- you must not add or delete any required fields.
- you may delete optional or repeated fields.
- you may add new optional or repeated fields but you must use fresh tag numbers (i.e. tag numbers that were never used in this protocol buffer, not even by deleted fields).

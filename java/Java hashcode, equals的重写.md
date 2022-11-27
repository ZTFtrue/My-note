# 什么时候重写hashCode和equals

在Java中，每个对象都会有一个`hash code`，Java可以通过这个`hash code`来识别一个对象.

1. 当默认`hashcode`计算复杂, 性能要求高时可能需要重写, 比如, 有N个对象,第一个对象值为1, 第二个为2, 以此类推.

2. 自定义比较检索规则时. 比如, 处理某运营商手机号,自定义`hashcode`，只处理其中的某几位.

3. 使用HashMap，如果key是自定义的类，就必须重写hashcode()和equals(). HashMap 是用`key`值对应的`hash code`去查值的, 如果`hash code` 相等, 再`equals` 比较内容是否相等, 否则直接比较对象会永远不相等.

如果不重写`hashCode`, 那么不同的对象，他们虽然名字一样但是哈希码可能会不一样.

如果你重载了equals，比如说是基于对象的内容实现的，而保留hashCode的实现不变，那么很可能某两个对象明明是“相等”，而hashCode却不一样.

这样，当你用其中的一个作为键保存到hashMap、hasoTable或hashSet中，再以“相等的”找另一个作为键值去查找他们的时候，则根本找不到(因为找的过程中是通过key值对应的hash值去寻找的).

对于每一个对象，通过其hashCode()方法可为其生成一个整形值(散列码)，该整型值被处理后，将会作为数组下标，存放该对象所对应的Entry(存放该对象及其对应值).

equals()方法则是在HashMap中插入值或查询时会使用到. 当HashMap中插入值或查询值对应的散列码与数组中的散列码相等时，则会通过equals方法比较key值是否相等.

Java Integer 重写hashCode和equals 的代码

```java
private final int value;

public Integer(int value) {
    this.value = value;// 这个Value 就是 hashCode 的值, 也是Integer 的值
}

@Override
public int hashCode() {
    return Integer.hashCode(value);
}

public static int hashCode(int value) {
    return value;
}

public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return value == ((Integer)obj).intValue();
        }
        return false;
}

/*
* Integer integer1=1;
* Integer integer2=1;
* System.out.println(integer1.hashCode()); // result 1
* System.out.println(integer2.hashCode()); // result 1
*/
```

java 原来的equals 只是比较两个对象, 所以也要重写

```java
    public boolean equals(Object obj) {
        return (this == obj);
    }
```

Java String 重写hashCode和equals 的代码

```java
   public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String aString = (String)anObject;
            if (!COMPACT_STRINGS || this.coder == aString.coder) {
                return StringLatin1.equals(value, aString.value);
            }
        }
        return false;
    }
 public int hashCode() {
        // The hash or hashIsZero fields are subject to a benign data race,
        // making it crucial to ensure that any observable result of the
        // calculation in this method stays correct under any possible read of
        // these fields. Necessary restrictions to allow this to be correct
        // without explicit memory fences or similar concurrency primitives is
        // that we can ever only write to one of these two fields for a given
        // String instance, and that the computation is idempotent and derived
        // from immutable state
        int h = hash;
        if (h == 0 && !hashIsZero) {
            h = isLatin1() ? StringLatin1.hashCode(value)
                           : StringUTF16.hashCode(value);
            if (h == 0) {
                hashIsZero = true;
            } else {
                hash = h;
            }
        }
        return h;
    }
    /**
    * StringLatin1
    */
  @HotSpotIntrinsicCandidate
    public static boolean equals(byte[] value, byte[] other) {
        if (value.length == other.length) {
            for (int i = 0; i < value.length; i++) {
                if (value[i] != other[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
/*
*  String s1="a";
*  String s2="a";
*  System.out.println(s1.hashCode());
*  System.out.println(s2.hashCode());
*/
    public static int hashCode(byte[] value) {
        int h = 0;
        for (byte v : value) {
            h = 31 * h + (v & 0xff);
            // h = (h << 5) - h + (v & 0xff);
        }
        return h;
    }

```

## 31作为乘数

[Why does Java's hashCode() in String use 31 as a multiplier?](https://stackoverflow.com/questions/299304/why-does-javas-hashcode-in-string-use-31-as-a-multiplier)

According to Joshua Bloch's Effective Java (a book that can't be recommended enough, and which I bought thanks to continual mentions on stackoverflow)

The value 31 was chosen because it is an `odd(奇怪,寄) prime 主要的,质数`.

If it were even(偶数) and the multiplication(乘法) overflowed(溢出), information would be lost, as multiplication by 2 is equivalent(等价的) to shifting(移位).

The advantage of using a prime is less(不) clear(清楚), but it is traditional.

A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction(减法) for better performance: 31 * i == (i << 5) - i.

Modern VMs do this sort of optimization automatically.

(from Chapter 3, Item 9: Always override hashcode when you override equals, page 48)

<https://stackoverflow.com/a/300111>

Goodrich and Tamassia computed from over 50,000 English words (formed as the union of the word lists provided in two variants of Unix) that using the constants 31, 33, 37, 39, and 41 will produce fewer than 7 collisions in each case. This may be the reason that so many Java implementations choose such constants.

## 扰动函数

(h = key.hashCode()) ^ (h >>> 16)

把哈希值右移16位，也就正好是自己长度的一半，之后与原哈希值做异或运算，这样就混合了原哈希值中的高位和低位，增大了`随机性`

## 加载因子

加载因子决定了数据量多少了以后进行扩容

加载因子 = 填入表中的元素个数 / 散列表的长度

默认情况下是16x0.75=12时，就会触发扩容操作

使用随机哈希码，在扩容阈值（加载因子）为0.75的情况下，节点出现在频率在Hash桶（表）中遵循参数平均为0.5的泊松分布. 确保落点均匀.

根据具体使用可以调整.

## 初始化容量

### 阀值

如果初始化容量传入奇数, 实际初始化值是比传入初始值大的

```java
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

阈值为8,桶容量大于64，进行红黑树转换操作.否则只是扩容.

## 数据迁移

原哈希值与扩容新增出来的长度16，进行&运算，如果值等于0，则下标位置不变。如果不为0，那么新的位置则是原来位置上加16。

## HashMap 扩容倍数是2

## a % b == a & (b - 1)

a % b == a & (b - 1)

验证无法通过 比如 `10` 和 `6` , `5` 和 `3`

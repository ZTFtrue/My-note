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
```

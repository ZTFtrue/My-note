# C++

## 数据

```c++
//常量:  其值在运行时是不能改变的，
//编译器会将其放置在一个只读内存中
const int MAX_VALUE=100; 

// signed 有符号  默认不用写
// unsigned 无符号 

bool is_false=0;//也可以使用false
bool is_true =1; //也可以使用true
```

宏只是文本替换, 在速度和内存上与const 一致

## 输出与输入

### 输出

```c++
printf("格式控制%d",1);//格式输出函数
printf("输出%%百分号%d",1);//输出百分号
printf("格式控制: %3s/n","ztftrue");//截取字符串
```

|格式字符  |                      功能                                        |
|--------- | ---------------------------------                               |
| d,i      |         以带符号的十进制形式输出整数(整数不输出符号)               |
|  o       |              以八进制无符号形式输出整数                           |
| x,X      | 以十六进制无符号形式输出整数. 小x以小写形式输出,大X以大写形式输出   |
|  u       |              以无符号十进制形式输出整数          |
|  c       |            以字符形式输出，只输出一个字符       |
|  s       |                     输出字符串          |
|  f       |                以小数形式输出(float)        |
| e,E      |                  以指数形式输出实数         |
| g,G      |            输出宽度较短的一种格式 不输出0       |
|  i       |           用于长整形整数,可加在符号 d、o、x、u 前面     |
|  m       |      数据最小宽度             |
|  n       | 对实数，表示输出n位小数;对字符串，表示截取的个数      |
|  -       | 输出的数字或字符在域内向左靠           |

### 输入

```c++
scanf(格式控制,);
```

## 数组

初始化

一维数组不完全初始化(partly initialized)规则: 如果初始化表达式的个数比数组元素数少，则对外部变量，静态变量和自动变量来说，没有初始化表达式的元素将被初始化为0.

## 指针变量(pointer variable)和引用变量(reference variable)

引用变量是一个别名，也就是说，它是某个已存在变量的另一个名字。一旦把引用初始化为某个变量，就可以使用该引用名称或变量名称来指向变量。

1. A pointer can be re-assigned: 一个指针可以被重新分配.

    ```c++
    int x = 5;
    int y = 6;
    int *p;
    p = &x;
    p = &y;
    *p = 10;
    assert(x == 5);
    assert(y == 10);
    ```

    A reference cannot be re-bound, and must be bound at initialization:引用不能被重新绑定，必须在初始化时绑定:

    ```c++
    int x = 5;
    int y = 6;
    int &q; // error
    int &r = x;
    ```

2. A pointer variable has its own identity: a distinct, visible memory address that can be taken with the unary & operator and a certain amount of space that can be measured with the sizeof operator. Using those operators on a reference returns a value corresponding to whatever the reference is bound to; the reference’s own address and size are invisible. Since the reference assumes the identity of the original variable in this way, it is convenient to think of a reference as another name for the same variable.
    指针变量具有自己的标识: 一个可以用一元运算符`＆`获取到独特的,可见的内存地址, 以及 可以使用sizeof运算符测量的一个确定的空间.
    `引用`的地址和大小是不可见的, 使用`引用`运算符将返回一个值，该值对应于该`引用`所绑定的 __内容__(原始变量).由于`引用`用此方式假定原始变量的身份，因此将`引用`视为同一变量的另一个名称很方便(A reference can be thought of as a constant pointer).

    ```c++
    int x = 0;
    int &r = x;
    int *p = &x;
    int *p2 = &r;

    assert(p == p2); // &x == &r
    assert(&p != &p2);
    ``

3. You can have arbitrarily nested pointers to pointers offering extra levels of indirection. References only offer one level of indirection.
    您可以任意嵌套指向提供额外间接级别的指针.  引用仅提供一种间接级别.

    ```c++
    int x = 0;
    int y = 0;
    int *p = &x;
    int *q = &y;
    int **pp = &p;

    **pp = 2;
    pp = &q; // *pp is now q
    **pp = 4;

    assert(y == 4);
    assert(x == 2);
    ```

4. A pointer can be assigned nullptr, whereas a reference must be bound to an existing object. If you try hard enough, you can bind a reference to nullptr, but this is undefined and will not behave consistently. You can, however, have a reference to a pointer whose value is nullptr.
    指针可以分配为nullptr，而引用必须绑定到一个存在的对象. 如果你可以非常努力的将引用绑定到nullptr，但这是未定义的，不会表现一致.
    但是，你可以引用一个值为nullptr的指针.

    ```c++
    /* the code below is undefined; your compiler may optimise it
     * differently, emit warnings, or outright refuse to compile it */

    int &r = *static_cast<int *>(nullptr);

    // prints "null" under GCC 10
    std::cout
        << (&r != nullptr
            ? "not null" : "null")
        << std::endl;

    bool f(int &r) { return &r != nullptr; }

    // prints "not null" under GCC 10
    std::cout
        << (f(*static_cast<int *>(nullptr))
            ? "not null" : "null")
        << std::endl;
    ```

5. Pointers can iterate over an array; you can use ++ to go to the next item that a pointer is pointing to, and + 4 to go to the 5th element. This is no matter what size the object is that the pointer points to. 指针可以遍历数组.  您可以使用++转到指针所指向的下一个项目，并使用+ 4转到第5个元素.  无论指针指向的对象大小是多少.

6. A pointer needs to be _dereferenced_ with * to access the memory location it points to, whereas a reference can be used directly. A pointer to a class/struct uses -> to access its members whereas a reference uses a `.`.

    指针需要用*取消引用，以访问其指向的内存位置，而引用可以直接使用.
    指向类/结构的指针使用`->`访问其成员，而`引用`使用`.`.

7. References cannot be put into an array, whereas pointers can be (Mentioned by user @litb) 引用不能放入数组，而指针可以(由用户@litb提及)

8. Const references can be bound to temporaries. Pointers cannot (not without some indirection). 常量引用可以绑定到临时对象, 指针不能 (并非没有间接的绑定).

## Why is transposing(移位,转置) a matrix of 512x512 much slower than transposing a matrix of 513x513?

TAG: CPU cache, CPU 缓存 , 硬件

<https://stackoverflow.com/questions/11413855/why-is-transposing-a-matrix-of-512x512-much-slower-than-transposing-a-matrix-of/48029152#48029152>

TAG: CPU cache

### Q

After conducting some experiments on square matrices of different sizes, a pattern came up. Invariably, transposing a matrix of size 2^n is slower than transposing one of size 2^n+1. For small values of n, the difference is not major.

Big differences occur however over a value of 512. (at least for me)

Disclaimer: I know the function doesn't actually transpose the matrix because of the double swap of elements, but it makes no difference.

Follows the code:

```c++
#define SAMPLES 1000
#define MATSIZE 512

#include <time.h>
#include <iostream>
int mat[MATSIZE][MATSIZE];

void transpose()
{
   for ( int i = 0 ; i < MATSIZE ; i++ )
   for ( int j = 0 ; j < MATSIZE ; j++ )
   {
       int aux = mat[i][j];
       mat[i][j] = mat[j][i];
       mat[j][i] = aux;
   }
}

int main()
{
   //initialize matrix
   for ( int i = 0 ; i < MATSIZE ; i++ )
   for ( int j = 0 ; j < MATSIZE ; j++ )
       mat[i][j] = i+j;

   int t = clock();
   for ( int i = 0 ; i < SAMPLES ; i++ )
       transpose();
   int elapsed = clock() - t;

   std::cout << "Average for a matrix of " << MATSIZE << ": " << elapsed / SAMPLES;
}
```

### A

A cache is organized in sets and lines.

At a time, only one set is used, out of which any of the lines it contains can be used. The memory (a line can mirror ) times [the number of lines]  gives us the cache size.

For a particular memory address, we can calculate (which set should mirror it) with the formula:

```txt
set = ( address / lineSize ) % numberOfsets
```

This sort of formula (ideally) gives a uniform(一致,均匀) distribution across the sets, because each memory address is as likely(可能) to be read (I said ideally).

It's clear that overlaps can occur. In case of a cache miss, the memory is read in the cache and the old value is replaced. Remember each set has (a number of lines), out of which( the least recently )used one is overwritten with the newly read memory.

Assume each set has 4 lines, each holding 64 bytes.

We first attempt to read the address `0x2710`, which goes in set 28.
And then we also attempt to read addresses `0x2F00`, `0x3700`, `0x3F00` and `0x4700`.
All of these belong to the same set.
Before reading `0x4700`, all lines in the set would have been occupied.
(Reading that memory) evicts(驱逐) an existing line in the set, the line that initially was holding `0x2710`.
(The problem) lies(躺卧) (in the fact) (that we read addresses 同位语) that are (for this example) `0x800` __apart__. This is the critical(关键的) stride (again, for this example).

The `critical stride` can also be calculated:

```text
criticalStride = numberOfSets * lineSize
```

Variables spaced criticalStride or a multiple apart contend(v. 争取) for the same cache lines.

Assume a matrix of 64x64 (remember, the effects vary(v. 变化) [according to the cache 状语]) with an 8kb cache, 4 lines per set * line size of 64 bytes. Each line can hold 8 of the elements in the matrix (64-bit int).

The critical stride would be 2048 bytes, which correspond to 4 rows of the matrix (which is continuous in memory).

Assume we're processing row 28. We're attempting to take the elements of this row and swap them with the elements from column 28. The first 8 elements of the row make up a cache line, but they'll go into 8 different cache lines in column 28. Remember, critical stride is 4 rows apart (4 consecutive elements in a column).

When element 16 is reached in the column (4 cache lines per set & 4 rows apart = trouble) the ex-0 element will be evicted from the cache. When we reach the end of the column, all previous cache lines would have been lost and needed reloading __on access__ to the next element (the whole line is overwritten).

Having a size that is not a multiple of the critical stride __messes up__ this perfect scenario for disaster, as we're no longer dealing with elements that are critical stride apart on the vertical, so the number of cache reloads is severely reduced.

--------------------

上边的我一概看不懂, 所以就有了下边的(来自 codeiemu)

CPU cache logic refers to the mechanism implemented in modern processors to improve the performance of accessing data from memory. CPU caches are small, fast memory units that store frequently accessed data and instructions, reducing the need to fetch them from slower main memory.

Here are the key concepts related to CPU cache logic:

Cache Hierarchy: CPU caches are organized in a hierarchy, typically consisting of multiple levels, such as L1, L2, and L3 caches. Each level of cache is larger but slower than the previous level. The hierarchy is designed to exploit (the principle of locality), which states that programs tend to access data and instructions that are spatially or temporally close to each other.

Cache Lines: Caches are divided into fixed-size blocks called cache lines. A cache line typically holds multiple bytes or words of data or instructions. __When the CPU accesses a memory location, it fetches an entire cache line from memory into the cache, even if only a small portion of it is needed.__

Cache Mapping: Caches use (various mapping techniques) to determine where to store data in cache lines. Common mapping techniques include direct mapping, set-associative(组相关) mapping, and fully associative mapping. These techniques determine (how memory addresses are mapped to specific cache lines and locations within the cache).

Cache Coherency(n. 一致): In multi-core systems, where multiple CPUs share access to the same memory, cache coherency protocols are employed to ensure that each CPU's cache has a consistent view of memory. These protocols manage the invalidation and updating of cache lines to maintain data consistency across caches.

Cache Replacement Policies: When the cache is full and a new cache line needs to be fetched, a cache replacement policy determines which cache line to evict. Common replacement policies include least recently used (LRU), first in first out (FIFO), and random replacement. The goal is to maximize cache hit rates by keeping frequently accessed data in the cache.

Cache Prefetching: CPU cache logic often includes prefetching mechanisms that predict and proactively fetch data into the cache before it is explicitly accessed. Prefetching can help hide memory latency and improve overall performance.

The CPU cache logic is an essential aspect of modern processors and plays a crucial role in improving computational performance. Efficient cache utilization can significantly reduce the time spent waiting for data from main memory, which is typically orders of magnitude slower than accessing data from the cache.

Please note that the specific implementation of CPU cache logic can vary across different processor architectures and manufacturers. The details of cache organization, sizes, mappings, and replacement policies are highly dependent on the specific CPU design.

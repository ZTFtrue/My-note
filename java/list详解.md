# list

1. 问: 简单说说什么是 Deque 以及 ArrayDeque 与 LinkedList 的区别及特点？  
答: Deque 是一个双端队列接口，Deque 扩展了 Queue，有队列的所有方法，还可以看做栈，有栈的基本方法 push/pop/peek，还有明确的操作两端的方法 addFirst/removeLast 等.

2. 问: 简单说说 ArrayDeque 与 LinkedList 的适用场景？  
答: ArrayDeque 和 LinkedList 都实现了 Deque 接口，如果只需要 Deque 接口且从两端进行操作则一般来说 ArrayDeque 效率更高一些，如果同时需要根据索引位置进行操作或经常需要在中间进行插入和删除操作则应该优先选 LinkedList 效率高些，因为 ArrayDeque 实现是循环数组结构(即一个动态扩容数组，默认容量 16，然后通过一个 head 和 tail 索引记录首尾相连)，而 LinkedList 是基于双向链表结构的.

//将指定元素插入双向队列开头

void addFirst(Object e);

//将指定元素插入双向队列末尾

void addLast(Object e);

//返回对应的迭代器，以逆向顺序来迭代队列中的元素

Iterator descendingIterator();

//获取但不删除双向队列的第一个元素

Object getFirst();

//获取但不删除双向队列的最后一个元素

Object getLast();

//将指定元素插入双向队列开头

boolean offerFirst(Object e);

//将指定元素插入双向队列结尾

boolean offerLast(Object e);

//获取但不删除双向队列的第一个元素，如果双端队列为空则返回 null

Object peekFirst();

//获取但不删除双向队列的最后一个元素，如果此双端队列为空则返回 null

Object peekLast();

//获取并删除双向队列的第一个元素，如果此双端队列为空则返回 null

Object pollFirst();

//获取并删除双向队列的最后一个元素，如果此双端队列为空则返回 null

Object pollLast();

//退栈出该双向队列中的第一个元素

Object pop();

//将元素入栈进双向队列栈中

void push(Object e);

//获取并删除该双向队列的第一个元素

Object removeFirst();

//删除双向队列第一次出现的元素 e

Object removeFirstOccurrence(Object e);

//获取并删除双向队列的最后一个元素

removeLast();

//删除双向队列最后一次出现的元素 e

removeLastOccurrence(Object e);

LinkedList 是一个比较奇怪的类，其即实现了 List 接口又实现了 Deque 接口(Deque 是 Queue 的子接口)，而 LinkedList 的实现是基于双向链表结构的，其容量没有限制，是非并发安全的队列，所以不仅可以当成列表使用，还可以当做双向队列使用，同时也可以当成栈使用(因为还实现了 pop 和 push 方法). 此外 LinkedList 的元素可以为 null 值.

ArrayDeque 是一个用数组实现的双端队列 Deque，为满足可以同时在数组两端插入或删除元素的需求，该数组还必须是循环的，即循环数组(circular array)，也就是说数组的任何一点都可能被看作起点或者终点，ArrayDeque 是非线程安全的，当多个线程同时使用的时候需要手动同步，此外该容器不允许放 null 元素，同时与 ArrayList 和 LinkedList 不同的是没有索引位置的概念，不能根据索引位置进行操作.

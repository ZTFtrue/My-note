# Android 实现多级列表显示

这里使用RecycleView实现, 当然也不是RecycleView嵌套实现.核心原理是每一条数据都有一个变量标记它的级,按照等级设置ItemView 的margin或者padding.

每一条Item的数据结构:

```java
class Item{
  int level;
  YourData data;
}
```

准备两个list,一个存储源数据,一个存储要显示的数据.

```java
private ArrayList<Item> sourceList = new ArrayList<>();
private LinkedList<Item> linkedList = new LinkedList<>();
```

获取到的数据首先放到sourceList中,然后通过遍历获取到你要的数据,显示即可.

```sourceList``` 仅仅是一个示例,如果你获取到的数据已经是一个树形结构,则简单了很多.

```AdapterWordRecycleView```:

```java
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = arrayList.get(position);
        Context context = holder.view.getContext();
        int levelMargin = item.getLevel() * context.getResources().getDimensionPixelSize(R.dimen.level_margin);
        holder.view.setPadding(levelMargin, holder.view.getPaddingTop(), holder.view.getPaddingRight(), holder.view.getPaddingBottom());
    }
```

为什么不使用 `RecycleView` 嵌套?

其实RecycleView嵌套本身运行起来就会很卡, 原理上来说它也破坏了RecycleView的View复用.
RecycleView的View复用实际上缓存一套子View, 重用View，可以避免重新创建新的布局.

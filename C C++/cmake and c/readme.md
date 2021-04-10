#

GCC的C编译器是gcc，其命令格式为:

```sh
# Usage: gcc [options] file...
# 生成的文件名为test
gcc -o test hello.c
#默认情况下生成的目标文件的文件名和源文件的名称一样，只是扩展名为.o. 例如，下面的命令会生成一个名字为hello.o的目标文件: 
gcc -c hello.c
# 如果需要生成指定的文件名，可以使用-o选项. 下面的命令将源文件hello.c编译成目标文件，文件名为test.o: 
gcc -c -o test.o hello.c

#可以用一条命令编译多个源文件，生成目标文件，这通常用于编写库文件或者一个项目中包含多个源文件. 例如一个项目包含file1.c、file2.c和file3.c，下面的命令可以将源文件生成3个目标文件: file1.o、file2.o和file3.o: 
gcc -c file1.c file2.c file3.c

# 下面的命令将两个源文件中的程序编译成一个执行文件，文件名为test. 
gcc -o test string.c main.c

# 当然可以先将源文件编成目标文件，然后进行链接. 例如，下面的过程先将string.c和main.c源文件编译成目标文件string.o和main.o，然后将string.o和main.o链接生成test: 
gcc -c string.c main.c
gcc -o test string.o main.o
```

GCC支持默认扩展名策略，表2.1是GCC下默认文件扩展名的含义.

|文件扩展名|GCC所理解的含义|
|---|---|
|*.c|该类文件为C语言的源文件|
|*.h|该类文件为C语言的头文件|
|*.i|该类文件为预处理后的C文件|
|*.C|该类文件为C++语言的源文件|
|*.cc|该类文件为C++语言的源文件|
|*.cxx|该类文件为C++语言的源文件|
|*.m|该类文件为Objective-C语言的源文件|
|*.s|该类文件为汇编语言的源文件|
|*.o|该类文件为汇编后的目标文件|
|*.a|该类文件为静态库|
|*.so|该类文件为共享库|
|a.out|该类文件为链接后的输出文件|

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
} 

```

# 等效

(*pointer).memberName
或者:
pointer->memberName

## this is make file , must use tab

```makefile
hello.out:max.o min.o hello.c
# 这里必须是tab
	gcc max.o min.o hello.c -o hello.out
max.o:max.c
	gcc -c max.c
min.o:min.c
	gcc -c min.c
clean :
    rm edit main.o kbd.o command.o display.o \
        insert.o search.o files.o utils.o
```

make 会一层一层的寻找依赖

## Makefile的规则

```makefile
target ... : prerequisites ...
            command
            ...
```

target也就是一个目标文件，可以是Object File，也可以是执行文件. 还可以是一个标签(Label)，

prerequisites就是，要生成那个target所需要的文件或是目标.

command也就是make需要执行的命令. (任意的Shell命令)

prerequisites中如果有一个以上的文件比target文件要新的话，command所定义的命令就会被执行.

反斜杠(\)是换行符的意思.

make 会一层一层的寻找依赖

## 使用变量

```makefile
    objects = main.o kbd.o command.o display.o \
              insert.o search.o files.o utils.o

    edit : $(objects)
            cc -o edit $(objects)
    main.o : main.c defs.h
            cc -c main.c
    kbd.o : kbd.c defs.h command.h
            cc -c kbd.c
    command.o : command.c defs.h command.h
            cc -c command.c
    display.o : display.c defs.h buffer.h
            cc -c display.c
    insert.o : insert.c defs.h buffer.h
            cc -c insert.c
    search.o : search.c defs.h buffer.h
            cc -c search.c
    files.o : files.c defs.h buffer.h command.h
            cc -c files.c
    utils.o : utils.c defs.h
            cc -c utils.c
    .PHONY : clean
    clean :
            rm edit $(objects)
```

## 引用其它的Makefile

```makefile
include 文件名
```

## Run

```sh
make
```

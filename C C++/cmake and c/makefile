# this is make file , must use tab
hello.out:max.o min.o hello.c
	gcc max.o min.o hello.c -o hello.out
max.o:max.c
	gcc -c max.c
min.o:min.c
	gcc -c min.c
clean :
    rm edit main.o kbd.o command.o display.o /
        insert.o search.o files.o utils.o
# Makefile的规则
#target ... : prerequisites ...
#            command
#            ...
#            ...
#  target也就是一个目标文件，可以是Object File，也可以是执行文件. 还可以是一个标签(Label)，
#  prerequisites就是，要生成那个target所需要的文件或是目标. 
#  command也就是make需要执行的命令. (任意的Shell命令)
#  prerequisites中如果有一个以上的文件比target文件要新的话，command所定义的命令就会被执行. 

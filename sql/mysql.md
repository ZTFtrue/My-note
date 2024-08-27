# MySql入门

mysql支持的存储引擎有: ```innoBD,MyISAM,Memory(存储到内存中),Merge,Archive,Federated,CSV,BLACKHOLE```.

```sql
show engies; -- 显示安装以后可用的存储引擎和默认引擎.

desc 查看表基本结构

show tables或show tables from database_name; -- 显示当前数据库中所有表的名称

show databases; -- 显示mysql中所有数据库的名称

show columns from table_name from database_name; 或MySQL show columns from

database_name.table_name; -- 显示表中列名称

show innodb status; -- 显示innoDB存储引擎的状态


MySQL show warnings; -- 显示最后一个执行的语句所产生的错误、警告和通知

show errors; -- 只显示最后一个执行语句所产生的错误

alter table <tablename\> engine=<更改后的引擎>;--更改表的引擎

desc 查看表

show variables like 'character%'; #执行编码显示

show index from table_name; -- 显示表的索引

show status; -- 显示一些系统特定资源的信息，例如，正在运行的线程数量

show variables; -- 显示系统变量的名称和值

show processlist; -- 显示系统中正在运行的所有进程，也就是当前正在执行的查询. 大多数用户可以查看

他们自己的进程，但是如果他们拥有process权限，就可以查看所有人的进程，包括密码.

MySQL show table status; -- 显示当前使用或者指定的database中的每个表的信息. 信息包括表类型和表的最新更新时间

show privileges; -- 显示服务器所支持的不同权限

show create database database_name; -- 显示create database 语句是否能够创建指定的数据库

show create table table_name; -- 显示create database 语句是否能够创建指定的数据库
```

表压缩  
In particular, compressed tables use less disk space and so require less disk I/O to read and write the data. Compression is available for all kinds of workloads with InnoDB tables, and for read-only MyISAM tables.



## 日志

```sql
show logs; -- 显示BDB存储引擎的日志
show variables like '%log_bin%'; -- binlog日志 , 是否开启
```

```ini
[mysqld]
# 启用二进制日志
log_bin = /path/to/mysql-bin.log

# 启用错误日志
log_error = /path/to/error.log

# 启用查询日志
general_log = 1
general_log_file = /path/to/query.log

# 启用慢查询日志
slow_query_log = 1
slow_query_log_file = /path/to/slow_query.log
long_query_time = 1
```

### [binlog](https:--stackoverflow.com/questions/1366184/what-is-binlog-in-mysql)

The binary log contains “events” that describe database changes such as table creation operations or changes to table data. It also contains events for statements that potentially could have made changes (for example, a DELETE which matched no rows), unless row-based logging is used. The binary log also contains information about how long each statement took that updated data. The binary log has two important purposes:

For replication, the binary log on a primary replication server provides a record of the data changes to be sent to secondary servers. The primary server sends the events contained in its binary log to its secondaries, which execute those events to make the same data changes that were made on the primary.
Certain data recovery operations require the use of the binary log. After a backup has been restored, the events in the binary log that were recorded after the backup was made are re-executed. These events bring databases up to date from the point of the backup

The binary log is not used for statements such as SELECT or SHOW that do not modify data. <https:--dev.mysql.com/doc/refman/8.0/en/binary-log.html>

mysql 定时清理日志文件  
删除mysql日志文件

## 登录和创建数据库

<!-- https:--stackoverflow.com/questions/9083408/fatal-error-cant-open-and-lock-privilege-tables-table-mysql-host-doesnt-ex -->
<!-- -- sudo mysql_install_db --user=mysql --ldata=/var/lib/mysql -->
对于不同的发行平台这里是不一样的, 安装时一般会有提示
[在linux 下可能需要 ```mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql``` 以初始化数据目录](https:--serverfault.com/questions/812719/mysql-mariadb-not-starting)

```sql
mysqladmin -h hostname flushprivileges  
utf8_bin :是二进制，a和A会区别对待  
utf8_general_ci :大小写不敏感a和A会被当做一样
show databases;   显示所有数据库
```

```sql
mysql -u root -p
mysql -u root 
```

> mysql: mysql -h 主机名 -u 用户名 -p 需要密码

### 创建数据库

create database 数据库名 [其他选项];

``` sql
create database samp_db character set gbk;  
create database test2 default character set utf8   collateutf8_general_ci;--指定utf编码  
```

create database test2 default character set utf8mb4;

### 查看数据库的定义

```sql
show create database 数据库名;  
```

### 查看数据库的默认存储引擎 (测试无效)
  
   > ~~show variables like 'storage_engine';~~

### 删除数据库

```sql
drop database 数据库名;  
```

### 选择所要操作的数据库,有两种方式

1. 在登录数据库时指定:  ```mysql -D 所选择的数据库名 -h 主机名 -u 用户名 -p```
2. ```use <数据库名>;```   -- 选择成功后会提示: Database changed  

### 创建数据库表

> 注意: 在创建之前先选择数据库

#### 创建表

> ```create table <表名称 ?(列声明)>;```
>
> 以创建 students 表为例, 表中将存放 学号(id)、姓名(name)、性别(sex)、年龄(age)、联系电话(tel) 这些内容:

```sql
create table students
(
    id int unsigned not null auto_increment primary key,
    name char(8) not null,
    sex char(4) not null,
    age tinyint unsigned not null,
    tel char(13) null default "-"
);
```

___语句解说:___

>create table tablename(columns) 为创建数据库表的命令, 列的名称以及该列的数据类型将在括号内完成;括号内声明了5列内容, id、name、sex、age、tel为每列的名称, 后面跟的是数据类型描述, 列与列的描述之间用逗号(,)隔开;

以 "__id int unsigned not null auto_increment primary key__" 行进行介绍:

1. "id" 为列的名称;  

2. "int" 指定该列的类型为 int(取值范围为 -8388608到8388607), 在后面我们又用 "unsigned" 加以修饰, 表示该类型为无符号型, 此时该列的取值范围为 0到16777215;  

3. "not null" 说明该列的值不能为空, 必须要填, 如果不指定该属性, 默认可为空;  

4. "auto_increment" 需在整数列中使用, 其作用是在插入数据时若该列为 NULL, MySQL将自动产生一个比现存值更大的唯一标识符值. 在每张表中仅能有一个这样的值且所在列必须为索引列.

5. "primary key" 表示该列是表的主键, 本列的值必须唯一, MySQL将自动索引该列. 也可以在其后指定，如:

```sql
create tablet students(
    id int(11) ,
    name char(25),
    primary key(id)
);
```

下面的 char(8) 表示存储的字符长度为8, tinyint的取值范围为 -127到128, default 属性指定当该列值为空时的默认值.

主键分为 __单字端主键__ 和 __多字段联合主键__

>__多字段联合主键语法规则如下:__  
```primary key(字段一,字段二);```
例:primary key(id,name,sex);

```sql
create table t1(id int primary key, name varchar(25));
create table t1(id int , name varchar(25)，primary key(id);-- 指定了主键 id
create table t1(id int , name varchar(25)，primary key(id,name);--多键
>create table t1(id int unique, name varchar(25));--约束唯一性 unique
>create table t1(id int default 111, name varchar(25));--默认约束，初始值
```

##### 查看已创建了表的名称

```show tables```

#### 向表中插入数据

>insert 语句可以用来将一行或多行数据插到数据库表中, 使用的一般形式如下:

```sql
insert [into] 表名 [(列名1, 列名2, 列名3, ...)] values (值1, 值2, 值3, ...);
```

>其中 [] 内的内容是可选的, 例如, 要给 samp_db 数据库中的 students 表插入一条记录, 行语句:

```sql
insert into students values(NULL, "王刚", "男", 20, "13811371377");
```

#### 查询表中的数据

>select 语句常用来根据一定的查询规则到数据库中获取数据, 其基本的用法为:

```sql
select 列名称 from 表名称 [查询条件];
```

例如要查询 students 表中所有学生的名字和年龄, 输入语句 ```select name, age fromstudents;```

也可以使用通配符 \* 查询表中所有的内容, 语句: ```select * from students;```

[实际开发中尽量不要使用" __*__ "](https:--stackoverflow.com/questions/321299/what-is-the-reason-not-to-use-select)

##### 按特定条件查询

_where 关键词用于指定查询条件, 用法形式为: select 列名称 from 表名称 where 条件;_

>以查询所有性别为女的信息为例, 输入查询语句: select * from students where sex="女";

__where 子句不仅仅支持 "where 列名 = 值" 这种名等于值的查询形式, 对一般的比较运算的运算符都是支持的, 例如 =、>、<、>=、<、!= 以及一些扩展运算符 is [not] null、in、like 等等.  还可以对查询条件使用 or 和 and 进行组合查询, 这里不再多做介绍.__

>示例:

* 查询年龄在21岁以上的所有人信息: select * from students where age > 21;

* 查询名字中带有 "王" 字的所有人信息: select * from students where name like "%王%";

* 查询id小于5且年龄大于20的所有人信息: select * from students where id<5 and age>20;

### 更新表中的数据

>update 语句可用来修改表中的数据, 基本的使用形式为:

```sql
 update 表名称 set 列名称=新值 where 更新条件;
```

>使用示例:

* 将id为5的手机号改为默认的"-": update students set tel=default where id=5;

* 将所有人的年龄增加1: update students set age=age+1;

* 将手机号为 13288097888 的姓名改为 "张伟鹏", 年龄改为 19: update students set name="张伟鹏", age=19 where tel="13288097888";

### 删除表中的数据

>delete 语句用于删除表中的数据, 基本用法为:

```sql
delete from 表名称 where 删除条件;
```

>使用示例:

* >删除id为2的行: delete from students where id=2;

* >删除所有年龄小于21岁的数据: delete from students where       age<20;

> _删除表中的所有数据: delete from students;_

### 创建后表的修改

>alter table 语句用于创建后对表的修改, 基础用法如下:

#### 添加列

>基本形式: alter table 表名 add 列名 列数据类型 [after 插入位置];

示例:

在表的最后追加列:```address: alter table students add address char(60);```
在名为 age 的列后插入列 birthday: ```alter table students add birthday date after age;``` (_birthday date_ 指定date 似乎有问题)

#### 修改列

>基本形式: alter table 表名 change 列名称 列新名称 新数据类型;

示例:

将表 tel 列改名为 telphone: ```alter table students change tel telphone char(13) default "-"```;

将 name 列的数据类型改为 char(16): ```alter table students change name name char(16) not null```;

#### 删除列

>基本形式: alter table 表名 drop 列名称;

示例:
删除 birthday 列: ```alter table students drop birthday;```

#### 重命名表

>基本形式: alter table 表名 rename 新表名;

示例:
重命名 students 表为 workmates: ```alter table students rename workmates;```

#### 删除整张表

>基本形式: drop table 表名;

示例: 删除 workmates 表: ```drop table workmates```;

## 用户管理

查看用户 `SELECT user,host FROM mysql.user;`

修改
`ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';`

~~设置管理员密码: ```SET PASSWORD FOR 'root'@'localhost' = PASSWORD('your password');```~~ 最近测试了一下不能这么做了<https:--www.ibm.com/docs/en/spectrum-lsf-rtm/10.2.0?topic=ssl-configuring-default-root-password-mysqlmariadb> 但是，默认的也没看到，能不用密码直接登录。

创建用户 ```CREATE USER 'ztftrue'@'localhost' IDENTIFIED BY 'password';```

授权: ```GRANT privileges ON databasename.tablename TO 'username'@'host'```;

* databasename - 数据库名,tablename-表名
* privileges: 用户的操作权限,如SELECT , INSERT , UPDATE 等, 如果要授予所的权限则使用ALL.;
* 如果要授予该用户对所有数据库和表的相应操作权限则可用`*`表示, 如:  
    授权可以访问ztftrue DataBase 下的所有数据 ```GRANT ALL ON ztftrue.* TO 'ztftrue'@'localhost';```

显示一个用户的权限，显示结果类似于grant 命令: ```show grants for user_name@localhost;```  
查看用户的授权```show grants for ztftrue@localhost;```

## 约束

<!-- 删除整个数据库
基本形式: drop database 数据库名;
示例: 删除 samp_db 数据库: drop database samp_db; -->

```sql
       create table students
    (
        id int unsigned not null auto_increment primary key,
        name char(8) not null unique,
        sex char(4) not null,
        age tinyint unsigned not null,
        tel char(13) null default "-",
        [constraint<>] unique(tel)
    );
```

>not null 非空约束
>
>unique  唯一约束，这一列不允许出现重复的值(允许空)
定义完成之后修改
>
>auto_increment  设置数据表的属性值自动增加,在每张表中仅能有一个这样的值且所在列必须为索引列.

### 外键约束

>__外键用来在两个表的数据之间建立连接，它可以是一列或者多列. 一个表可以有一个或者多个外键. 外键对应的是参照完整性，一个表的外键可以为空值，若不为空值，则每一个外键值必须等于另一个表中主键的值.__

1. __外键:__
      它可以不是本表的主键，但是对应另外一个表的主键

2. __主表:__
 对于两个具有关联关系的表，相关联字段中主键所在的那个表即是主表.

3. __从表__
 对于两个具有关联关系的表，相关联字段中外键所在的那个表即是从表.

>创建外键的语法规则如下

```sql
   [constraint<外键名>]foreign key 字段名1[,字段名2]references<主表面>主键列1[,主键列2,...]
```

## 外键

尽量不要使用外键

* 有额外开销
* 逐行操作
* 可“到达”其他表，意味着锁;高并发时容易产生死锁
* 数据维护困难，某些数据的存在与删除由于外键、约束会导致很难维护; 数据库层较难切换,数据库升级麻烦

## 提交数据去重

首先建立表的时候, 如果一个字段不想重复 , 使用 UNIQUE 约束
接着`INSERT IGNORE` 插入数据.

INSERT INGORE 会忽略导致错误的数据，并将其余数据插入表中.

## Where Vs Join

where 全部查出, 琢行消减(逐行操作)
Join 直接查到目标

```sql
SELECT g.id, g.name,u.date FROM goods  g LEFT JOIN  user u ON g.user_id=u.id WHERE g.id=?"
```

INNER JOIN 与 JOIN 是相同的。取两张表的交集
LEFT JOIN 只要左表有就算有一条数据,右表没有显示null, 如上边语句结果可以为

```bash
+-----+-------+------------+
|  id |  name | date       |
+-----+-------+------------+
|   1 | "good"| 2016-05-10 |
|   2 | "bad" | NULL       |
+-----+-------+------------+
```

RIGHT JOIN 只要右表有就算有一条数据,左表没有显示null
FULL OUTER JOIN 只要一张表表有就算有一条数据 并集.

## 添加注释

表注释

ALTER TABLE ztftrue.article COMMENT='article';

列注释

ALTER TABLE ztftrue.article MODIFY COLUMN render_method int(11) NOT NULL COMMENT '渲染方式';

查看表注释
show create table article;

或者
use information_schema;
select * from TABLES where TABLE_SCHEMA='farmsell' and TABLE_NAME='goods';

查看字段注释
show full columns from article;

或者
use information_schema;
select * from COLUMNS where TABLE_SCHEMA='ztftrue' and TABLE_NAME='artciles' and COLUMN_NAME='render_method';

## Other

对于一些较长的语句在命令提示符下可能容易输错, 因此我们可以通过任何文本编辑器将语句输入好后保存为 createtable.sql 的文件中, 通过命令提示符下的文件重定向执行执行该脚本.

打开命令提示符, 输入: mysql -D samp_db -u root -p < createtable.sql

(提示: 1.如果连接远程主机请加上 -h 指令; 2. createtable.sql 文件若不在当前工作目录下需指定文件的完整路径. )

> 我用过最好的跨平台免费工具: DBeaver

部分linux发行版数据库目录: ```/var/lib/mysql/``` , 具体查看相关wiki

[AS table & table ? https:--stackoverflow.com/questions/4164653/whats-the-purpose-of-sql-keyword-as](https:--stackoverflow.com/questions/4164653/whats-the-purpose-of-sql-keyword-as)

## 性能

inet_aton 转 IP4
inte_ntoa.

TEXT,BLOB 分离到单独的表中.

[实际开发中尽量不要使用" __*__ "](https:--stackoverflow.com/questions/321299/what-is-the-reason-not-to-use-select)

避免使用ENUM类型

尽量设置NOT NULL

和钱相关的: decimal 类型

索引不要多,要有主键

覆盖索引.

大批量,分批操作.

## 联合索引(复合索引)

Mysql从左到右的使用索引中的字段
一个查询可以只使用索引中的一部份，但只能是 __最左侧__ 部分.

<https:--dev.mysql.com/doc/refman/8.0/en/multiple-column-inde1xes.html>

```sql
key index (a,b,c)
select * from t where a=1 and b > 2 and c<3
select * from t where a=1 and b > 2 -- 就不会使用 c 了
-- 创建复合索引时，应该仔细考虑列的顺序
-- 可以加快查询速度
```

A multiple-column index can be considered a sorted array, the rows of which contain values that are created by concatenating the values of the indexed columns.

### 索引覆盖

查询的数据都可以通过联合索引查到，不用回表. 这时即使不包含最左前缀也可能会走索引

```sql
key index (a,b,c)
select a,b,c from t where c=1 and b > 2 --索引覆盖
```

## N+1 selects problem

频繁读取数据库

<https:--stackoverflow.com/a/39696775/6631835>

一次取出全部数据, 然后遍历需要的数据

## [UTF-8 转UTF-8 mb4](https:--docs.nextcloud.com/server/15/admin_manual/configuration_database/mysql_4byte_support.html)

1. Make sure your database is set to use the Barracuda InnoDB file format:

    ```bash
    mysql> show variables like 'innodb_file_format';
    +--------------------+-----------+
    | Variable_name      | Value     |
    +--------------------+-----------+
    | innodb_file_format | Barracuda |
    +--------------------+-----------+
    1 row in set (0.00 sec)
    ```

    If your innodb_file_format is set as ‘Antelope’ you must upgrade your file format using:

    ```bash
    mysql> SET GLOBAL innodb_file_format=Barracuda;
    ```

2. Make sure the following InnoDB settings are set on your MySQL server:

    MySQL 8.0 or later:

    ```config
    [mysqld]
    innodb_file_per_table=1
    ```

    ```bash
    mysql> show variables like 'innodb_file_per_table';
    +-----------------------+-------+
    | Variable_name         | Value |
    +-----------------------+-------+
    | innodb_file_per_table | ON    |
    +-----------------------+-------+
    1 row in set (0.00 sec)
    ```

    MySQL older than 8.0:

    ```config
    [mysqld]
    innodb_large_prefix=true
    innodb_file_format=barracuda
    innodb_file_per_table=1
    ```

3. Restart the MySQL server in case you changed the configuration in step 1.

4. Change your databases character set and collation:

```sql
ALTER DATABASE nextcloud CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### MariaDB support

1. Make sure the following InnoDB settings are set on your MariaDB server:

```config
[mysqld]
innodb_file_per_table=1
```

Continue at step 2 of the MySQL instructions.

#### MariaDB 10.2 or earlier

1. Make sure the following InnoDB settings are set on your MySQL server:

    ```config
    [mysqld]
    innodb_large_prefix=true
    innodb_file_format=barracuda
    innodb_file_per_table=1
    ```

2. Restart the MariaDB server in case you changed the configuration in step 1.

3. Figure out whether the file format was changed to Barracuda:  
判断文件格式是否改为Barracuda：

    ```bash
    MariaDB> SELECT NAME, SPACE, FILE_FORMAT FROM INFORMATION_SCHEMA.   INNODB_SYS_TABLES WHERE NAME like "nextcloud%";
    ```

    If the file format is “Barracuda” for every single table, nothing special is left to do. Continue with the MySQL instructions at step 3. While testing, all tables’ file format was “Antelope”.(如果每个表的文件格式都是“Barracuda”，则继续MySQL的第 3 步说明。在测试时，所有表的文件格式都是“Antelope”)

4. The tables needs to be migrated(迁移的) to “Barracuda” manually, one by one. SQL commands can be created easily, however:

    ```bash
    MariaDB> USE INFORMATION_SCHEMA;
    MariaDB> SELECT CONCAT("ALTER TABLE `", TABLE_SCHEMA,"`.`", TABLE_NAME,     "` ROW_FORMAT=DYNAMIC;") AS MySQLCMD FROM TABLES WHERE TABLE_SCHEMA =   "nextcloud";
    ```

    This will return an SQL command for each table in the nextcloud database. The rows can be quickly copied into a text editor, the “|”s replaced and the SQL commands copied back to the MariaDB shell. If no error appeared (in doubt check step 2) all is done and nothing is left to do here. It can be proceded with the MySQL instructions from step 3 onwards.

5. It is possible, however, that some tables cannot be altered. The operations fails with: “ERROR 1478 (HY000): Table storage engine ‘InnoDB’ does not support the create option ‘ROW_FORMAT’”. In that case the failing tables have a SPACE value of 0 in step 2. It basically means that the table does not have an index file of its own, which is required for the Barracuda format. This can be solved with a slightly(轻微地) different SQL command:

    ```bash
    MariaDB> ALTER TABLE `nextcloud`.`oc_tablename` ROW_FORMAT=DYNAMIC, ALGORITHM=COPY;
    ```

    Replace oc_tablename with the failing table. If there are too many (did not happen here), SQL commands can be generated in a batch (task for the reader).

6. Now everything should be fine and the MySQL instructions can be followed from step 3 onwards.

## 导出表注释 和 表的列注释 及数据

 表的列注释 及数据

 TAG: csv

```sql
select
    table_name,
 COLUMN_NAME  ,
 COLUMN_COMMENT,
 DATA_TYPE  ,
 IS_NULLABLE ,
 column_default ,
 extra
FROM INFORMATION_SCHEMA.COLUMNS
where table_schema = '库名';
-- where table_name = 'table name' and table_schema='library name';
```

表及其注释

```sql
SELECT table_name, table_comment
FROM information_schema.tables
WHERE table_schema = 'library name';
```

## 触发器

```sql
CREATE DEFINER=`root`@`localhost` TRIGGER order_log
AFTER INSERT
ON `table name要触发的表的名称` FOR EACH row
BEGIN
-- 要执行的操作
    INSERT INTO 插入的表 (operate_type,operate_user_id, operate_time, operate_table, operate_item_id)
    VALUES (1,  new.user_id,NOW(), 'order', NEW.id);
END
```

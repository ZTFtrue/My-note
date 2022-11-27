
# z

## mongoDB

### 创建用户

```sh
## password 是对应的密码
db.createUser(
  {
    user: "ztftrue",
    pwd: "password",
    roles: [ { role: "readWrite", db: "ztftrue" }]
  }
);

db.createUser(
  {
   user:"admin",
  pwd:"password",
  roles:[{role:"userAdminAnyDatabase",db:"admin"}]
});

db.createUser(
  {
    user: "root",
    pwd: "password",
    roles: [ { role: "root", db: "admin" } ]
  }
);


```

### 登录

```sh
mongo -u "root" -p "password" --authenticationDatabase "admin"  
mongo -u "admin" -p "password" --authenticationDatabase "admin"  
mongo -u "ztftrue" -p "password" --authenticationDatabase "ztftrue" 
```

### 使用

```sh
use admin
# 查找显示集合
show collections
# 查找
db.customer.find()
# 查找并格式化输出
db.customer.find().pretty() 
db.customer.insert({"name":"pink pig"})
```

### 删除用户

```sh
db.dropUser('BlogSpring')

db.collection.remove(
  {
    "_id" : "test.BlogSpring",
    "user" : "BlogSpring",
    "db" : "test",
    "credentials" : {
        "SCRAM-SHA-1" : {
            "iterationCount" : 10000,
            "salt" : "WvGMrudJb0P0xzNcCyUqww==",
            "storedKey" : "KppBpY3pao10sw6FNxjWohXyLFw=",
            "serverKey" : "jZ69DMOfbFJcOVEbzGw7Q6BgmYQ="
        }
    },
    "roles" : [
        {
            "role" : "readWrite",
            "db" : "myBlog"
        }
    ]
}
)
```

### 恢复或导入数据

```sh
mongorestore --authenticationDatabase admin  -u=admin -p=abc123 --db=Dict file.db
mongoimport --authenticationDatabase admin  -u=admin -p=abc123 --file file.bson 
```

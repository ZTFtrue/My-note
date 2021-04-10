# App security

## 常见Android漏洞

 webdeveloper 应该注意

- Webview 远程执行代码( API 17及以上不用考虑了)

- Webview 明文存储密码

- Webview 不校验证书

- 拒绝服务攻击

- SharedPrefs 任意读写

- 密钥硬编码

- AES/DES 弱加密

- 中间人攻击

- 随机数生成函数使用错误

- Intent scheme url 漏洞(校验scheme url ，具体google)

- 未进行安全加固

- File 任意读写

- 主机名弱校验

- 备份标识配置

- zip 文件遍历目录

- 引入第三方组件漏洞

## 防御

- 启动时通过NDK进行签名校验

- 对于部分应用设置蜜罐代码, 只有外挂之类的可以执行到, 而正常用户是不可能执行到那部分代码

- NDK 中加入签名校验

- 加固

- 上线前关闭调试模式```android:debuggable=”false“```

- 混淆日志，移除log

- 内存加密

### 代码规范

- 限制对变量的访问
- 让每个类和方法都成为final， 除非有足够的理由不这样做
- 不要依赖包作用域
- 使类不可克隆(克隆允许绕过构造器而轻易地复制类实例)
- 使类不可序列化(避免外部源获取对象的内部状态的控制)
- 避免硬编码敏感数据
- 查找恶意代码
- 不可统一添加全部权限

## 组件安全

### Activity

#### Activity访问权限的控制(可能会导致恶意调用页面，接收恶意数据)

1. 私有Activity不应被其他应用启动且应该确保相对是安全的

2. 关于Intent的使用要谨慎处理接收的Intent以及其携带的信息，尽量不发送敏感信息，并进行数据校验

3. 设置android:exported属性，不需要被外部程序调用的组件应该添加```android:exported=”false”```属性

#### Activity劫持(正常的Activity界面被恶意攻击者替换上仿冒的恶意Activity界面进行攻击和非法用途. )

在登录窗口或者用户隐私输入等关键Activity的onPause方法中检测最前端Activity应用是不是自身或者是系统应用，如果发现恶意风险，则给用户一些警示信息，提示用户其登陆界面以被覆盖，并给出覆盖正常Activity的类名.

设置Activity的属性: 可防止系统截屏```this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE)```;

### Service

Service 劫持、拒绝服务

以拒绝服务为例，常见的两种漏洞:

1. 空指针异常导致的拒绝服务```java.lang.NullPointerException```

2. 类型转换异常导致的拒绝服务 ```ClassCastException```

避免方法:

1. 应用内部使用的Service 应设置为私有
2. 针对Service接收到的数据应该验证并谨慎处理
3. 内部Service需要使用签名级别的protectionLevel来判断是否未内部应用调用

### BrocastReciver

病毒类型: 发送虚假、恶意广播

1. 设置AndroidManifest.xml， 添加属性```android:exported="false"```

    ```xml
    <receiver   android:name="MyBroadCastReceiver"    android:exported="false">
        <intent-filter>
            <action     android:name="MyBroadcast">
            </action>
        </intent-filter>
    </receiver>
    ```

2. 控制权限来防止类似事件发生

### Content Provider

Content Provider 扮演着应用间数据共享桥梁的角色，其主要的安全风险为SQL注入和本地文件目录遍历

1. 控制权限来限制对Content Provide的访问

2. 访问由其它公开的文件的首选方式是使用```Content Provide```

### 传输安全

1. 本地校验不该校验的，如用户名和密码用以登录，验证码，内置用户名，密码.
2. 在代码层一定要把密钥放的更隐蔽一些.
3. 所有APP的客户端不要用同一个密钥，对于密钥生成的方法，尽可能的根据里的用户的ID或者手机的一些信息来生成一个，避免随机生成.

### 键盘攻击

1. 输入数据监听攻击
2. 键盘截屏攻击
3. 输入数据篡改攻击
4. 未加密前篡改
5. 来自系统底层的内存dump攻击

防:

1. 自己绘制软键盘
2. 对键盘的数据输入过程、数据存储过程、内存数据换算过程进行加密
3. 使用第三方加固软键盘

### allowBackup

allowBackup，是Google提供的一个备份功能，如果用户要换手机了，可以用它把APP导到另一个手机上，用户本身使用的一些数据(聊天记录)会跟着一起备份过去. 它还带来了两个问题:

1. 恶意程序也可以利用这个备份，并把你备份的数据传到网上，在他的手机上打开. 如果我们在做换手机操作过程中没有做全面的校验，那将会造成直接性的用户隐私信息泄露，用户的个人信息和隐私操作将会被暴露出来.

2. 这个备份的时候，会生成一个加密的文件. 这个加密文件是可以被篡改的，而且你再利用这个备份文件导到另一个手机的时候，校验的时候可以直接过. 就是你把备份导出来，病毒可以在导入文件加入恶意的代码或者其他的数据. 你去进行备份的时候，相当于重新安装了，你在另外一个手机重新安装的时候，你重装的的APP上就携带着的病毒，后果非常危险.

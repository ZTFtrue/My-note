# App security

## 常见 Android 漏洞

webdeveloper 应该注意

- Webview 远程执行代码( API 17 及以上不用考虑了)

- Webview 明文存储密码

- Webview 不校验证书

- 拒绝服务攻击

- SharedPrefs 任意读写

- 密钥硬编码

- AES/DES 弱加密

- 中间人攻击

- 随机数生成函数使用错误

- Intent scheme url 漏洞(校验 scheme url ，具体 google)

- 未进行安全加固

- File 任意读写

- 主机名弱校验

- 备份标识配置

- zip 文件遍历目录

- 引入第三方组件漏洞

## 防御

- 启动时通过 NDK 进行签名校验

- 对于部分应用设置蜜罐代码, 只有外挂之类的可以执行到, 而正常用户是不可能执行到那部分代码

- NDK 中加入签名校验

- 加固

- 上线前关闭调试模式`android:debuggable=”false“`

- 混淆日志，移除 log

- 内存加密

### 代码规范

- 限制对变量的访问
- 让每个类和方法都成为 final， 除非有足够的理由不这样做
- 不要依赖包作用域
- 使类不可克隆(克隆允许绕过构造器而轻易地复制类实例)
- 使类不可序列化(避免外部源获取对象的内部状态的控制)
- 避免硬编码敏感数据
- 查找恶意代码
- 不可统一添加全部权限

## 组件安全

### Activity

#### Activity 访问权限的控制(可能会导致恶意调用页面，接收恶意数据)

1. 私有 Activity 不应被其他应用启动且应该确保相对是安全的

2. 关于 Intent 的使用要谨慎处理接收的 Intent 以及其携带的信息，尽量不发送敏感信息，并进行数据校验

3. 设置 android:exported 属性，不需要被外部程序调用的组件应该添加`android:exported=”false”`属性

#### Activity 劫持(正常的 Activity 界面被恶意攻击者替换上仿冒的恶意 Activity 界面进行攻击和非法用途. )

在登录窗口或者用户隐私输入等关键 Activity 的 onPause 方法中检测最前端 Activity 应用是不是自身或者是系统应用，如果发现恶意风险，则给用户一些警示信息，提示用户其登陆界面以被覆盖，并给出覆盖正常 Activity 的类名.

设置 Activity 的属性: 可防止系统截屏`this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE)`;

### Service

Service 劫持、拒绝服务

以拒绝服务为例，常见的两种漏洞:

1. 空指针异常导致的拒绝服务`java.lang.NullPointerException`

2. 类型转换异常导致的拒绝服务 `ClassCastException`

避免方法:

1. 应用内部使用的 Service 应设置为私有
2. 针对 Service 接收到的数据应该验证并谨慎处理
3. 内部 Service 需要使用签名级别的 protectionLevel 来判断是否未内部应用调用

### BrocastReciver

病毒类型: 发送虚假、恶意广播

1. 设置 AndroidManifest.xml， 添加属性`android:exported="false"`

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

Content Provider 扮演着应用间数据共享桥梁的角色，其主要的安全风险为 SQL 注入和本地文件目录遍历

1. 控制权限来限制对 Content Provide 的访问

2. 访问由其它公开的文件的首选方式是使用`Content Provide`

### 传输安全

1. 本地校验不该校验的，如用户名和密码用以登录，验证码，内置用户名，密码.
2. 在代码层一定要把密钥放的更隐蔽一些.
3. 所有 APP 的客户端不要用同一个密钥，对于密钥生成的方法，尽可能的根据里的用户的 ID 或者手机的一些信息来生成一个，避免随机生成.

### 存储安全

### 键盘攻击

1. 输入数据监听攻击
2. 键盘截屏攻击
3. 输入数据篡改攻击
4. 未加密前篡改
5. 来自系统底层的内存 dump 攻击

防:

1. 自己绘制软键盘
2. 对键盘的数据输入过程、数据存储过程、内存数据换算过程进行加密
3. 使用第三方加固软键盘

### allowBackup

allowBackup，是 Google 提供的一个备份功能，如果用户要换手机了，可以用它把 APP 导到另一个手机上，用户本身使用的一些数据(聊天记录)会跟着一起备份过去. 它还带来了两个问题:

1. 恶意程序也可以利用这个备份，并把你备份的数据传到网上，在他的手机上打开. 如果我们在做换手机操作过程中没有做全面的校验，那将会造成直接性的用户隐私信息泄露，用户的个人信息和隐私操作将会被暴露出来.

2. 这个备份的时候，会生成一个加密的文件. 这个加密文件是可以被篡改的，而且你再利用这个备份文件导到另一个手机的时候，校验的时候可以直接过. 就是你把备份导出来，病毒可以在导入文件加入恶意的代码或者其他的数据. 你去进行备份的时候，相当于重新安装了，你在另外一个手机重新安装的时候，你重装的的 APP 上就携带着的病毒，后果非常危险.

## 双向验证 https 证书

## APP 签名验证

```java
public class SignatureValidator {
    public String getCertificateSHA1Fingerprint(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();

        //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
        String packageName = context.getPackageName();

        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;

        PackageInfo packageInfo = null;

        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();

        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);

        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //X509 证书，X.509 是一种非常通用的证书格式
        X509Certificate c = null;

        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hexString = null;

        try {
            //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");

            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());

            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * 这里是将获取到得编码进行16 进制转换
     *
     * @param arr
     * @return
     */
    private String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 生成md5
     *
     * @param message
     * @return
     */
    private String getMD5(String message) {
        String md5str = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            //2 将消息变成byte数组
            byte[] input = message.getBytes();

            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    private String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /**
     * 检测SHA1签名是否正确
     *
     * @return true 签名正常 false 签名不正常
     */
    public boolean checkSHA1(Context mContext) {
        String sha1 = "00:00"; // 获取到的签名文件
        String realCer = getMD5(getCertificateSHA1Fingerprint(mContext).trim());//再次MD5加密
        if (getMD5(sha1).equals(realCer)) {
            return true;
        }
        return false;
    }
}
```

## 检测是否 Root

```java
 public void c(){
           String message = "";
            boolean ro = false;
            try {
                startCommand("ls");
            } catch (InterruptedException | IOException e) {
                ro = true;
            } finally {
                if (!ro)
                    message = message + "您的手机已获取Root功能";
            }
    }
public String startCommand(String command) throws InterruptedException, IOException {
        Process p = new ProcessBuilder("su").start();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream())); BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            writer.write(command);
            writer.write("\n");
            writer.write("exit\n");
            writer.flush();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            p.waitFor();
            return sb.toString();
        }
}
```

## ADB Enable

```java
boolean enableAdb = Settings.Secure.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) > 0;
```

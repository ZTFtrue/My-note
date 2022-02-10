import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 代理
 * 通过反射代理方法，比较消耗系统性能
 * JDK代理需要用构造方法动态获取具体的接口信息，如果不实现接口的话, 无法初始化
 */
public class JDKDynamicProxy implements InvocationHandler {
    private Object target;// 维护一个目标对象

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }


    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void before() {
        System.out.println("Before");
    }

    private void after() {
        System.out.println("After");
    }
}

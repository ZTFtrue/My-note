import object.Greeting;
import object.GreetingImpl;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
//        object.Greeting greeting = new JDKDynamicProxy(new object.GreetingImpl()).getProxy();
//        greeting.sayHello("Jack");
        test();
    }

    public static void test() {
        // 无论是静态代理或是动态代理，都要返回一个代理对象
        GreetingImpl greetingImpl = new GreetingImpl();
        Greeting greeting = (Greeting) Proxy.newProxyInstance(
                greetingImpl.getClass().getClassLoader(),
                greetingImpl.getClass().getInterfaces(),
                new JDKDynamicProxy(greetingImpl));
        greeting.sayHello("Jack");
        // 可以直接使用 Greeting
        CGlibProxyFactory cGlibProxyFactory = new CGlibProxyFactory(greetingImpl);
        Greeting greetingCG = (Greeting) cGlibProxyFactory.getProxyInstance();
        greetingCG.sayHello("Jack");

    }
}

/**
 * 静态代理只能为一个目标对象服务，如果目标对象过多，则会产生很多代理类
 * cglib
 */
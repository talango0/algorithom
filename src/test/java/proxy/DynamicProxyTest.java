package proxy;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 字节码生产技术和动态代理的实现
 * 动态代理中所谓的"代理"是针对使用Java代码实际编写了代理类的"静态"代理而言的，它的优势不在于省去了
 * 编写代理类那一点工作量，而是实现了可以在原始类和接口还未知的时候，就确定代理类的代理行为。
 */
public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }

    static class Hello implements IHello{
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler{
        Object originalObj;
        Object bind( Object originalObj){
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originalObj, args);
        }
    }

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }


//
//    @Test
//    public void testProxy(){
//        Object x, y; String s; int i;
//        MethodType mt;
////        MethodHandle mh;
//        MethodHandles.Lookup lookup = MethodHandles.lookup();
//// mt is (char,char)String
//        mt = MethodType.methodType(String.class, char.class, char.class);
////        mh = lookup.findVirtual(String.class, "replace", mt);
////        s = (String) mh.invokeExact("daddy",'d','n');
//// invokeExact(Ljava/lang/String;CC)Ljava/lang/String;
//        assertEquals(s, "nanny");
//// weakly typed invocation (using MHs.invoke)
//        s = (String) mh.invokeWithArguments("sappy", 'p', 'v');
//        assertEquals(s, "savvy");
//// mt is (Object[])List
//        mt = MethodType.methodType(java.util.List.class, Object[].class);
//        mh = lookup.findStatic(java.util.Arrays.class, "asList", mt);
//        assert(mh.isVarargsCollector());
//        x = mh.invoke("one", "two");
//// invoke(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
//        assertEquals(x, java.util.Arrays.asList("one","two"));
//// mt is (Object,Object,Object)Object
//        mt = MethodType.genericMethodType(3);
//        mh = mh.asType(mt);
//        x = mh.invokeExact((Object)1, (Object)2, (Object)3);
//// invokeExact(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
//        assertEquals(x, java.util.Arrays.asList(1,2,3));
//// mt is ()int
//        mt = MethodType.methodType(int.class);
//        mh = lookup.findVirtual(java.util.List.class, "size", mt);
//        i = (int) mh.invokeExact(java.util.Arrays.asList(1,2,3));
//// invokeExact(Ljava/util/List;)I
//        assert(i == 3);
//        mt = MethodType.methodType(void.class, String.class);
//        mh = lookup.findVirtual(java.io.PrintStream.class, "println", mt);
//        mh.invokeExact(System.out, "Hello, world.");
//// invokeExact(Ljava/io/PrintStream;Ljava/lang/String;)V
//    }
}

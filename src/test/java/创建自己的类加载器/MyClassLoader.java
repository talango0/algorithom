package 创建自己的类加载器;

import org.junit.Test;

/**
 * 创建自己类加载器的场景：
 * 对特点Java字节码查找方法
 * 对字节码代码进行加密/解密
 * 实现同名Java类的隔离
 */
public class MyClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte [] b= null;
        return defineClass(name, b ,0, b.length);
    }
}


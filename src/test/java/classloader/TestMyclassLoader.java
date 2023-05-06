package classloader;

import org.junit.Test;

public class TestMyclassLoader {
    public  int a  = 5;

    public int getA() {
        return a;
    }

    @Test
    public void testMyClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
//        Class<?> aClass = myClassLoader.findClass("创建自己的类加载器.TestMyclassLoader");
        Class<?> aClass = Class.forName("classloader.TestMyclassLoader", true, myClassLoader);
        TestMyclassLoader o = (TestMyclassLoader)aClass.newInstance();
        System.out.println(o.getA());

    }
}

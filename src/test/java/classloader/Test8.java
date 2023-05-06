package classloader;

import java.util.UUID;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test8{
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("classloader.Test_8_A");
        System.out.println("end");
    }
}
class Test_8_A {
    static  {
        System.out.println("A static Block");
    }
}


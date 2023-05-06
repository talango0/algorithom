package classloader;

import java.util.UUID;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test7{
    public static void main(String[] args) {
        System.out.printf(Test_7_A.str);
    }
}
class Test_7_A {
    public static final String str = UUID.randomUUID().toString();
    static  {
        System.out.println("A static Block");
    }
}


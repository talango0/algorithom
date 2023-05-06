package classloader;

/**
 * <pre>
 *
 * </pre>
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test1{
    public static void main(String[] args) {
        System.out.println(Test_1_B.str);
    }
}

class Test_1_A {
    public static String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


class Test_1_B extends Test_1_A{
    static  {
        System.out.println("B static Block");
    }
}

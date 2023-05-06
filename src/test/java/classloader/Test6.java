package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test6{
    public static void main(String[] args) {
        System.out.printf(Test_6_A.str);
    }
}
class Test_6_A {
    public static final String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


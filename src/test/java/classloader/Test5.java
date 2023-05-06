package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test5{
    public static void main(String[] args) {
        Test_4_A arrs = new Test_4_A();
        System.out.printf("end");
    }
}
class Test_5_A {
    public static String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


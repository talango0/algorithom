package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test4{
    public static void main(String[] args) {
        Test_4_A [] arrs = new Test_4_A[1];
        System.out.printf("end");
    }
}
class Test_4_A {
    public static String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


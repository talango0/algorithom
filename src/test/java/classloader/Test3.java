package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test3{
    public static void main(String[] args) {
        System.out.println(Test_3_B.str);
    }
}
class Test_3_A {
    public static String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


class Test_3_B extends Test_2_A{
    public static String str = "B str";
    static  {
        System.out.println("B static Block");
    }
}
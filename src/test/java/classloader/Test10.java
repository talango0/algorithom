package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test10{
    public static void main(String[] args) {
        //System.out.println( new Test_10_B().str);
        System.out.println(Test_10_B.str);
    }
}
class Test_10_A {
    public static String str = "A str";
    static  {
        System.out.println("A static Block");
    }
}


class Test_10_B extends Test_10_A{
    static  {
        str += "11";
        System.out.println("B static Block");
    }
}
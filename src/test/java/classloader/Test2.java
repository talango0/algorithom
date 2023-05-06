package classloader;


/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test2{
    public static void main(String[] args) {
        System.out.println(Test_2_B.str);


        while (true);
    }
}
class Test_2_A {
    static  {
        System.out.println("A static Block");
    }
}


class Test_2_B extends Test_2_A{
    public static String str = "B str";
    static  {
        System.out.println("B static Block");
    }
}
package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test9{
    public static void main(String[] args) {
        System.out.println(new Test_9_B().str);
    }
}
class Test_9_A {
    static  {
        System.out.println("A static Block");
    }
}


class Test_9_B extends Test_9_A{
    public String str = "B str";
    static  {
        System.out.println("B static Block");
    }
}
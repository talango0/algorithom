package classloader;

/**
 * @author mayanwei
 * @date 2023-02-24.
 */
public class Test21{
    public static void main(String[] args) {
        Test_21_A instance1 = Test_21_A.getInstance();
        Test_21_A obj = new Test_21_A();
        System.out.println(Test_21_A.val1);
        System.out.println(Test_21_A.val2);
        System.out.println("---");
        Test_22_A instance = Test_22_A.getInstance();
        Test_22_A test22A = new Test_22_A();
        System.out.println(Test_22_A.val1);
        System.out.println(Test_22_A.val2);
    }
}
class Test_21_A {
    public static int val1 = 0;
    public static int val2 = 1;
    public static Test_21_A instance = new Test_21_A();

    public Test_21_A() {
        val1++;
        val2++;
    }

    public static Test_21_A getInstance(){
        return instance;
    }
}

class Test_22_A {
    public static int val1;
    public static Test_22_A instance = new Test_22_A();
    public Test_22_A() {
        val1++;
        val2++;
    }

    public static int val2 = 1;
    public static Test_22_A getInstance(){
        return instance;
    }
}



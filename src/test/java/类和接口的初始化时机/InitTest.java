package 类和接口的初始化时机;

public class InitTest {
    /**
     * 当访问一个Java类或接口中的静态域的时候，只有真正声明这个域的类或接口才会被实例化。
     * 在一个类被初始化的时候，它的直接父类也会被初始化。
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(B.value);
//        System.out.println(A.value);
        System.out.println(C.value);
    }
}

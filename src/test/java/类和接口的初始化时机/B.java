package 类和接口的初始化时机;

public class B {
    static int value = 100;
    static {
        System.out.println("Class B is initialized.");
    }
}
class A extends B{
//    static int value = 200;
    static {
        System.out.println("Class A is initialized.");
    }
}
class C extends A{
    static int value = 200;
    static {
        System.out.println("Class C is initialized.");
    }
}

package tos;

public class TosTest{
    public static void main(String[] args) {
        TosTest tosTest = new TosTest();
        tosTest.compute(10,20);
    }
    public int compute(int a, int b) {
        a = a + 1;
        b = b - 2;
        int temp = a * b;
        return temp;
    }
}

package GC;

public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2* _1MB];//占点儿内存，以便能在GC日志中看清楚是否被回收过

    public static void testGC(){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        //假设在这行发生GC,objA和objB是否能被回收
        System.gc();

    }

    public static void testAlloction(){
        byte [] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[ 2 * _1MB];
        allocation2 = new byte[ 2 * _1MB];
        allocation3 = new byte[ 2 * _1MB];
        allocation4 = new byte[ 4 * _1MB];
    }
    public static void main(String[] args) {
//        testGC();
        testAlloction();
    }



}

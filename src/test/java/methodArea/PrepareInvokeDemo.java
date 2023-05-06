package methodArea;

public class PrepareInvokeDemo{
    public static void callInvokestatic(){
        staticAdd(3,5);
    }

    private static int staticAdd(int i, int j) {
        return i+j;
    }
}

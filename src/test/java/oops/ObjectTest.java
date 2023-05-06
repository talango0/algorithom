package oops;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author mayanwei
 * @date 2023-02-25.
 */
public class ObjectTest{
    static int [] arr = {1,2,3};
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(ObjectTest.class).toPrintable());
        System.out.println(ClassLayout.parseInstance(new ObjectTest()).toPrintable());
        // 8 + 4 + 4 + 3*4 =28+ 4(对齐) = 32B
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());

    }
}

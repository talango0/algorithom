package object.stacking.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author mayanwei
 * @date 2022-11-10.
 */
public class ObjectMemoTest{
    //String a = "a";
    //int b = 1;
    //boolean c = false;
    //char d = 'd';
    public static void main(String[] args) {
        ObjectMemoTest t = new ObjectMemoTest();
        System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
}

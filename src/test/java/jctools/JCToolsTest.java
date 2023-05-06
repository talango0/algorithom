package jctools;

import org.jctools.util.UnsafeAccess;

/**
 * @author mayanwei
 * @date 2023-03-19.
 */
public class JCToolsTest{

    public static void main(String[] args) {
        int i = UnsafeAccess.UNSAFE.pageSize();
        System.out.println(i);
    }


}

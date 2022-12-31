package GC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author mayanwei
 * @date 2022-11-13.
 */
public class OOMTest{
    ///java -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC OOMTest
    //public static void main(String... args){
    //    List<byte[]> buffer = new ArrayList<byte[]>();
    //    buffer.add(new byte[10*1024*1024]);
    //}

    public static void main(String... args) {
        //List<String> list = new ArrayList<String>();
        while (true) {
            //list.add(UUID.randomUUID().toString().intern());
        }
    }

}

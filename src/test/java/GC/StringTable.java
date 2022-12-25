package GC;

import java.util.UUID;

public class StringTable {
    static String s = UUID.randomUUID().toString().intern();;
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            s = UUID.randomUUID().toString().intern();
            s += s.intern();
            if(s.length() <10000000 ){
                continue;
            }
            if (i >= 100000 && i%100000 == 0) {
                System.out.println("i=" + i);
            }
        }
    }
}

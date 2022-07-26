package work;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mayanwei
 * @date 2022-03-07.
 */

public class Test1{

    public static void main(String[] args){
        String format = String.format("%02d",1);;
        System.out.println(format);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "&" );
        System.out.println(stringBuilder.toString());

        AtomicInteger index = new AtomicInteger();
        System.out.println(index.incrementAndGet());
        System.out.println(index.incrementAndGet());
        System.out.println(index.incrementAndGet());
        System.out.println(index.incrementAndGet());

    }

    @Test
    public void test3(){
        String s = LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyyMMddHHmmssSSS" ) );
        String format = LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) );
        System.out.println(format);
    }

}

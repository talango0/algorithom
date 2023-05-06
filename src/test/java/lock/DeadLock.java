package lock;

import java.util.concurrent.TimeUnit;

/**
 * @author mayanwei
 * @date 2023-03-10.
 */
public class DeadLock{
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        new Thread(() -> {
            synchronized (obj1) {
                try {
                    synchronized (obj2) {
                        obj2.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                synchronized (obj2) {
                    synchronized (obj1) {
                        obj1.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


}

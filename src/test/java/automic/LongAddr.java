package automic;

import org.junit.jupiter.api.Test;
import org.quartz.spi.ThreadExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class LongAddr {
    /**
     * LongAdder性能比AtomicLong要好
     * 如果是 JDK8，推荐使用 LongAdder 对象，比 AtomicLong 性能更好(减少乐观锁的重试次数)。
     * 去查阅了一些博客和资料，大概的意思就是：
     * 使用AtomicLong时，在高并发下大量线程会同时去竞争更新同一个原子变量，但是由于同时只有一个线程的CAS会成功，
     * 所以其他线程会不断尝试自旋尝试CAS操作，这会浪费不少的CPU资源。
     * 而LongAdder可以概括成这样：内部核心数据value分离成一个数组(Cell)，每个线程访问时,通过哈希等算法映射到其中一个数字进行计数，
     * 而最终的计数结果，则为这个数组的求和累加。
     * 简单来说就是将一个值分散成多个值，在并发的时候就可以分散压力，性能有所提高。
     */
    @Test
    public void testLongAddr() {

        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println(longAdder);
    }

    @Test
    public void testThreadPoll(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncThreadPool" + mCount.getAndDecrement());
            }
        }, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i <8; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Task done by "+ Thread.currentThread().getName());
                }
            });

        }
    }
}

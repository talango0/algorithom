package leetcode.多线程;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author mayanwei
 * @date 2022-10-02.
 */
public class CountDownLatchDemo{
    /**
     * 1. 让一组线程在所有线程全部启动以后，在一起执行。
     * 2. 主线程等待另一组线程都执行完成之后，再继续执行。
     */
    class Worker implements Runnable{
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }


        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " begin");
            try {
                startSignal.await();
                doWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
            System.out.println(Thread.currentThread().getName() + " end");


        }

        private void doWork() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + "sleep 5s ...");
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void test() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch downLatch = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            new Thread(new Worker(startLatch, downLatch)).start();
        }
        // 这行代码换醒4个子线程，开始执行（因为 startLatch锁的状态是1，所以调用一次 countDown 方法就可以释放4个等待的子线程）
        startLatch.countDown();
        // 这行代码使主线程陷入沉睡，等待4个子线程执行完成之后才会继续执行（就是等待子线程执行 doneLatch.countDown()）
        downLatch.await();
        System.out.println("main thread end.");
    }
}

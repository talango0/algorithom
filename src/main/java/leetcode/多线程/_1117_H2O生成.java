package leetcode.多线程;
//现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
//
// 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
//
// 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
//
// 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
//
// 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
//
// 换句话说:
//
//
// 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
// 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
//
//
// 书写满足这些限制条件的氢、氧线程同步代码。
//
//
//
// 示例 1:
//
//
//输入: water = "HOH"
//输出: "HHO"
//解释: "HOH" 和 "OHH" 依然都是有效解。
//
//
// 示例 2:
//
//
//输入: water = "OOHHHH"
//输出: "HHOHHO"
//解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和
//"OHHOHH" 依然都是有效解。
//
//
//
//
// 提示：
//
//
// 3 * n == water.length
// 1 <= n <= 20
// water[i] == 'O' or 'H'
// 输入字符串 water 中的 'H' 总数将会是 2 * n 。
// 输入字符串 water 中的 'O' 总数将会是 n 。
//
//
// Related Topics 多线程 👍 118 👎 0


import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayanwei
 * @date 2022-10-06.
 */
public class _1117_H2O生成{
    /**
     * 信号量 Semaphore
     */
    static class Solution1{
        static class H2O{
            Semaphore hSemaphore = new Semaphore(2);
            Semaphore oSemaphore = new Semaphore(0);

            public H2O() {

            }

            public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
                hSemaphore.acquire();
                // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                releaseHydrogen.run();
                oSemaphore.release();
            }

            public void oxygen(Runnable releaseOxygen) throws InterruptedException {
                oSemaphore.acquire(2);
                // releaseOxygen.run() outputs "O". Do not change or remove this line.
                releaseOxygen.run();
                hSemaphore.release(2);
            }
        }
    }

    /**
     * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）是被阻塞，直到最后一个线程到达屏障，屏障才会开门，所有被屏障拦截的线程才会继续运行。
     * cb.await();
     * CyclicBarrier 会自动充值
     */

    static class Solution2{
        static class H2O{

            private Semaphore hSemaphore = new Semaphore(2);
            private Semaphore oSemaphore = new Semaphore(1);
            private CyclicBarrier cb = new CyclicBarrier(3);

            public H2O() {

            }

            public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
                hSemaphore.acquire();
                // 插入一个屏障
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                releaseHydrogen.run();
                hSemaphore.release();
            }

            public void oxygen(Runnable releaseOxygen) throws InterruptedException {
                oSemaphore.acquire();
                // 插入一个屏障
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // releaseOxygen.run() outputs "O". Do not change or remove this line.
                releaseOxygen.run();
                oSemaphore.release();
            }
        }
    }

    /**
     * ReentrantLock + Condition
     */
    static class Solution3{
        static class H2O{
            private int oCnt = 0;
            private int hCnt = 0;
            private ReentrantLock lock = new ReentrantLock();
            private Condition condition = lock.newCondition();

            public H2O() {

            }

            public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
                lock.lock();
                // 插入一个屏障
                try {
                    while (hCnt == 2) {
                        condition.await();
                    }
                    hCnt++;
                    if (hCnt == 2 && oCnt == 1) {
                        hCnt = 0;
                        oCnt = 0;
                    }
                    // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                    releaseHydrogen.run();
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }

            public void oxygen(Runnable releaseOxygen) throws InterruptedException {
                lock.lock();
                // 插入一个屏障
                try {
                    while (oCnt == 1) {
                        condition.await();
                    }
                    oCnt++;
                    if (hCnt == 2 && oCnt == 1) {
                        hCnt = 0;
                        oCnt = 0;
                    }
                    // releaseOxygen.run() outputs "O". Do not change or remove this line.
                    releaseOxygen.run();
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Synchronized + wait + notifyAll
     */
    static class Solution4{
        static class H2O{
            private volatile int state = 0;
            private Object obj = new Object();

            public H2O() {

            }

            public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
                synchronized (obj) {
                    while (state == 2) {
                        obj.wait();
                    }
                    state++;
                    // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                    releaseHydrogen.run();
                    obj.notifyAll();
                }

            }

            public void oxygen(Runnable releaseOxygen) throws InterruptedException {
                synchronized (obj) {
                    while (state != 2) {
                        obj.wait();
                    }
                    state = 0;
                    // releaseOxygen.run() outputs "O". Do not change or remove this line.
                    releaseOxygen.run();
                    obj.notifyAll();
                }

            }
        }
    }

    /**
     * BlockingQueue
     */
    static class Solution5{
        static class H2O {

            private int cnt = 0;
            private BlockingQueue<Integer> hQ = new LinkedBlockingDeque<>(2);
            private BlockingQueue<Integer> oQ = new LinkedBlockingDeque<>(1);

            public H2O() {

            }

            public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
                hQ.put(1);
                releaseHydrogen.run();
                cnt++;
                if (cnt == 3) {
                    cnt = 0;
                    hQ.clear();
                    oQ.clear();
                }
            }

            public void oxygen(Runnable releaseOxygen) throws InterruptedException {
                oQ.put(1);
                releaseOxygen.run();
                cnt++;
                if (cnt == 3) {
                    cnt = 0;
                    hQ.clear();
                    oQ.clear();
                }
            }
        }
    }
}

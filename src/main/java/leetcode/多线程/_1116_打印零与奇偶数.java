package leetcode.多线程;
//现有函数 printNumber 可以用一个整数参数调用，并输出该整数到控制台。
//
//
// 例如，调用 printNumber(7) 将会输出 7 到控制台。
//
//
// 给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。ZeroEvenOdd 的相同实例将会传递给三个不同线程：
//
//
//
// 线程 A：调用 zero() ，只输出 0
// 线程 B：调用 even() ，只输出偶数
// 线程 C：调用 odd() ，只输出奇数
//
//
// 修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
//
// 实现 ZeroEvenOdd 类：
//
//
// ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。
// void zero(printNumber) 调用 printNumber 以输出一个 0 。
// void even(printNumber) 调用printNumber 以输出偶数。
// void odd(printNumber) 调用 printNumber 以输出奇数。
//
//
//
//
// 示例 1：
//
//
//输入：n = 2
//输出："0102"
//解释：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
//
//
// 示例 2：
//
//
//输入：n = 5
//输出："0102030405"
//
//
//
//
// 提示：
//
//
// 1 <= n <= 1000
//
//
// Related Topics 多线程 👍 135 👎 0

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author mayanwei
 * @date 2022-10-05.
 */
public class _1116_打印零与奇偶数{
    /**
     * Semaphore 是一个基数信号量。
     * 从概念上讲，Semaphore 包含一组许可证。
     * 如果有需要的话，每个 acquire() 方法都会阻塞，直到获取一个可用的许可证。每个release() 方法都会释放一个可用的许可证。
     * 然而，实际上并没有真实的许可证对象供线程使用，Semaphore 只是对可用数量进行管理维护。
     * 总结，如果线程访问一个资源就必须先获得信号量。如果信号量内部计数器大于0，信号量减1，然后允许共享这个资源；否则，如果
     * 信号量的计数器等于 0，信号量将会把线程置入休眠直至计数器大于0，当信号量使用完时，必须释放。
     */
    static class Solution1{
        static class ZeroEvenOdd{
            private int n;
            private Semaphore zeroSemaphare = new Semaphore(1);
            private Semaphore oddSemaphare = new Semaphore(0);
            private Semaphore evenSemaphare = new Semaphore(0);

            public ZeroEvenOdd(int n) {
                this.n = n;
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void zero(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    zeroSemaphare.acquire();
                    printNumber.accept(0);
                    // 偶数
                    if ((i & 1) == 0) {
                        evenSemaphare.release();
                    }
                    // 奇数
                    else {
                        oddSemaphare.release();
                    }
                }
            }

            public void even(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 偶数打印
                    if ((i & 1) == 0) {
                        evenSemaphare.acquire();
                        printNumber.accept(i);
                        zeroSemaphare.release();
                    }
                }
            }

            public void odd(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 奇数打印
                    if ((i & 1) != 0) {
                        oddSemaphare.acquire();
                        printNumber.accept(i);
                        zeroSemaphare.release();
                    }
                }
            }
        }

    }

    /**
     * CountDownLatch 这个类使一个线程等待其他线程各自执行完毕后再执行。
     * 是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程执行完毕后，计数器值就-1，当计数器的值为 0 时，
     * 表示所有线程都执行完毕，然后在闭锁上等待的线程就恢复工作了。
     * <p>
     * CountDownLatch 和 CyclicBarrier 的区别
     * CoundDownLatch 时一次性的，CyclicBarrier 是可循环利用的。
     * CountCountLatch 参与的线程职责是不一样的，有的在倒计时，有的在等待倒计时结束。CyclicBarrier 参与的指责是一样的。
     */
    static class Solution2{
        static class ZeroEvenOdd{
            private int n;
            CountDownLatch zeroLatch = new CountDownLatch(0);
            CountDownLatch oddLatch = new CountDownLatch(1);
            CountDownLatch evenLatch = new CountDownLatch(1);

            public ZeroEvenOdd(int n) {
                this.n = n;
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void zero(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    zeroLatch.await();
                    printNumber.accept(0);
                    zeroLatch = new CountDownLatch(1);
                    // 偶数
                    if ((i & 1) == 0) {
                        evenLatch.countDown();
                    }
                    // 奇数
                    else {
                        oddLatch.countDown();
                    }
                }
            }

            public void even(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 偶数打印
                    if ((i & 1) == 0) {
                        evenLatch.await();
                        printNumber.accept(i);
                        evenLatch = new CountDownLatch(1);
                        zeroLatch.countDown();
                    }
                }
            }

            public void odd(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 奇数打印
                    if ((i & 1) != 0) {
                        oddLatch.await();
                        printNumber.accept(i);
                        oddLatch = new CountDownLatch(1);
                        zeroLatch.countDown();
                    }
                }
            }
        }

    }

    /**
     * Thread.yield():是当前线程从执行状态（运行状态）变为可执行状态（就绪状态）。cpu会从众多可执行态里选择，也就是说，当前也就是
     * 刚才那个线程还是有可能再次执行到，并不是说一定会执行到其他线程而该线程在下一次执行中不会执行到了
     */
    static class Solution3{
        static class ZeroEvenOdd{
            private int n;
            private volatile int state;
            private volatile boolean control = true;

            public ZeroEvenOdd(int n) {
                this.n = n;
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void zero(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    while (state != 0) {
                        Thread.yield();
                    }
                    printNumber.accept(0);
                    if (control) {
                        state = 1;
                    }
                    else {
                        state = 2;
                    }
                }
            }

            public void even(IntConsumer printNumber) throws InterruptedException {
                for (int i = 2; i <= n; i += 2) {
                    // 当state 不为2的时候，为就绪状态
                    while (state != 2) {
                        Thread.yield();
                    }
                    printNumber.accept(i);
                    control = true;
                    state = 0;
                }
            }

            public void odd(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i += 2) {
                    while (state != 1) {
                        Thread.yield();
                    }
                    printNumber.accept(i);
                    control = false;
                    state = 0;
                }
            }
        }
    }

    /**
     * LockSupport 类
     * 核心两个方法 park() 和 unpark() ，其中 park() 方法用来阻塞当前调用线程， unpark() 方法用于唤醒指定线程。
     * 这其实和 Object 类的 wait() 和 signal() 方法有些类似，但是 LockSupport的这两个方法从语义上讲比 Object类
     * 的方法更清晰，而且可以正对指定线程进行阻塞和唤醒。
     * <p>
     * LockSupport 类使用了一种名为 Permit(许可) 的概念来做到阻塞和唤醒线程的功能，可以把许可看成是一种(0,1) 信号量(Semaphore) ，
     * 但和信号量不同的是，许可的累加上线是 1.
     * 初始是，permit为0，调用unparkk()方法时，线程permit 加1，当调用 park() 方法时，如果permit 为0，则调用线程进入阻塞状态。
     */
    static class Solution4{
        static class ZeroEvenOdd{
            private int n;
            private Map<String, Thread> map = new ConcurrentHashMap<>();
            //0 打印 0, 1打印奇数， 2打印偶数
            private volatile int state = 0;

            public ZeroEvenOdd(int n) {
                this.n = n;
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void zero(IntConsumer printNumber) throws InterruptedException {
                map.put("zero", Thread.currentThread());
                for (int i = 1; i <= n; i++) {
                    while (state != 0) {
                        LockSupport.park();
                    }
                    printNumber.accept(0);
                    if ((i & 1) == 0) {
                        state = 2;
                    }
                    else {
                        state = 1;
                    }
                    // 通知其他两个线程
                    map.forEach((k, v) -> LockSupport.unpark(v));
                }
            }

            public void even(IntConsumer printNumber) throws InterruptedException {
                map.put("even", Thread.currentThread());
                for (int i = 2; i <= n; i += 2) {
                    while (state != 2) {
                        LockSupport.park();
                    }
                    printNumber.accept(i);
                    state = 0;
                    // 通知 zero线程
                    LockSupport.unpark(map.get("zero"));
                }
            }

            public void odd(IntConsumer printNumber) throws InterruptedException {
                map.put("odd", Thread.currentThread());
                for (int i = 1; i <= n; i += 2) {
                    while (state != 1) {
                        LockSupport.park();
                    }
                    printNumber.accept(i);
                    state = 0;
                    // 通知 zero线程
                    LockSupport.unpark(map.get("zero"));
                }
            }
        }
    }

    /**
     * ReentrantLock + Condition
     */
    static class Solution{
        static class ZeroEvenOdd{
            private int n;

            private volatile int start = 1;

            private volatile int state;
            private Lock lock = new ReentrantLock();
            private Condition zero = lock.newCondition();
            private Condition even = lock.newCondition();
            private Condition odd = lock.newCondition();

            public ZeroEvenOdd(int n) {
                this.n = n;
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void zero(IntConsumer printNumber) throws InterruptedException {
                lock.lock();
                try {
                    while (start <= n) {
                        if (state != 0) {
                            zero.await();
                        }
                        printNumber.accept(0);
                        if (start % 2 == 0) {
                            state = 2;
                            even.signal();
                        }
                        else {
                            state = 1;
                            odd.signal();
                        }
                        zero.await();
                    }
                    odd.signal();
                    even.signal();
                } finally {
                    lock.unlock();
                }
            }

            //偶数
            public void even(IntConsumer printNumber) throws InterruptedException {
                lock.lock();
                try {
                    while (start <= n) {
                        if (state != 2) {
                            even.await();
                        }
                        else {
                            printNumber.accept(start++);
                            state = 0;
                            zero.signal();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }

            //基数
            public void odd(IntConsumer printNumber) throws InterruptedException {
                lock.lock();
                try {
                    while (start <= n) {
                        if (state != 1) {
                            odd.await();
                        }
                        else {
                            printNumber.accept(start++);
                            state = 0;
                            zero.signal();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void test1() throws InterruptedException {
        Solution1.ZeroEvenOdd zeroEvenOdd = new Solution1.ZeroEvenOdd(2);

        new Thread(() -> {
            try {
                zeroEvenOdd.zero(new IntConsumer(){
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.even(new IntConsumer(){
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(new IntConsumer(){
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

package leetcode.多线程;
//编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
//
//
// 如果这个数字可以被 3 整除，输出 "fizz"。
// 如果这个数字可以被 5 整除，输出 "buzz"。
// 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
//
//
// 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14
//, fizzbuzz。
//
// 假设有这么一个类：
//
//
//class FizzBuzz {
//  public FizzBuzz(int n) { ... }              // constructor
//  public void fizz(printFizz) { ... }          // only output "fizz"
//  public void buzz(printBuzz) { ... }          // only output "buzz"
//  public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
//  public void number(printNumber) { ... }      // only output the numbers
//}
//
// 请你实现一个有四个线程的多线程版 FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
//
//
// 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
// 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
// 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
// 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
//
//
//
//
// 提示：
//
//
// 本题已经提供了打印字符串的相关方法，如 printFizz() 等，具体方法名请参考答题模板中的注释部分。
//
//
//
//
// Related Topics 多线程 👍 83 👎 0

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author mayanwei
 * @date 2022-10-07.
 */
public class _1195_交替打印字符串{
    /**
     * CyclicBarrier
     */
    static class Solution1{
        /**
         * public CyclicBarrier(int parties)
         * public CyclicBarrier(int parties, Runnable barrierAction)
         * 构造方法： parties 是参与线程的个数，barriAction  最后一个到达线程要做的事
         * <p>
         * public int await() throws InterruptedException, BrokenBarrierException
         * public int await() throws InterruptedException, BrokenBarrierException
         * 函数调用 await 表示已经到达栅栏
         * BrokenBarrierException 表示栅栏已经破坏，破坏的原因可能是其中的一个线程 await 时被中断或者超时。
         * 调用 await 方法的线程告诉CyclicBarrier自己已经到达同步点，然后当前线程阻塞，直到 parties个线程调用了await方法
         * <p>
         * CyclicBarrier和CountDownLatch区别
         * CountDownLatch 是一次性的，CyclicBarrier是可循环利用的。
         * CountDownLatch 参与线程职责不一样，有的在倒计时，有的在等到倒计时结束。CyclicBarrier参与的指责是一样的。
         */
        static class FizzBuzz{
            private int n;
            CyclicBarrier cb = new CyclicBarrier(4);

            public FizzBuzz(int n) {
                this.n = n;
            }

            // printFizz.run() outputs "fizz".
            public void fizz(Runnable printFizz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 能被3整除
                    if ((i % 5 != 0) && (i % 3 == 0)) {
                        printFizz.run();
                    }
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

            // printBuzz.run() outputs "buzz".
            public void buzz(Runnable printBuzz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    if ((i % 15 != 0) && (i % 5 == 0)) {
                        printBuzz.run();
                    }
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

            // printFizzBuzz.run() outputs "fizzbuzz".
            public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    if (i % 15 == 0) {
                        printFizzBuzz.run();
                    }
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void number(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    if ((i % 3 != 0) && (i % 5 != 0)) {
                        printNumber.accept(i);
                    }
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class Solution2{
        /**
         * Semaphore是用来保护一个或者多个共享资源的访问，Semaphore内部维护了一个计数器，
         * 其值为可以访问的共享资源的个数。一个线程要访问共享资源，先获得信号量，如果信号量的计数器值大于1，
         * 意味着有共享资源可以访问，则使其计数器值减去1，再访问共享资源。如果计数器值为0,线程进入休眠。
         * 当某个线程使用完共享资源后，释放信号量，并将信号量内部的计数器加1，之前进入休眠的线程将被唤醒并再次试图获得信号量。
         * <p>
         * 主要控制逻辑在number线程，控制哪个线程的启动，其他线程负责按条件打印
         */
        class FizzBuzz{
            private int n;
            private Semaphore number = new Semaphore(1);
            private Semaphore fizz = new Semaphore(0);
            private Semaphore buzz = new Semaphore(0);
            private Semaphore fizzbuzz = new Semaphore(0);

            public FizzBuzz(int n) {
                this.n = n;
            }

            // printFizz.run() outputs "fizz".
            public void fizz(Runnable printFizz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    // 能被3整除
                    if ((i % 5 != 0) && (i % 3 == 0)) {
                        fizz.acquire();
                        printFizz.run();
                        number.release();
                    }
                }
            }

            // printBuzz.run() outputs "buzz".
            public void buzz(Runnable printBuzz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    if ((i % 3 != 0) && (i % 5 == 0)) {
                        buzz.acquire();
                        printBuzz.run();
                        number.release();
                    }
                }
            }

            // printFizzBuzz.run() outputs "fizzbuzz".
            public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    if (i % 15 == 0) {
                        fizzbuzz.acquire();
                        printFizzBuzz.run();
                        number.release();
                    }
                }
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void number(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    number.acquire();
                    if ((i % 3 != 0) && (i % 5 != 0)) {
                        printNumber.accept(i);
                        number.release();
                    }
                    //fizz开始打印
                    else if (i % 3 == 0 && i % 5 != 0) {
                        fizz.release();
                    }
                    //buzz 开始打印
                    else if (i % 3 != 0 && i % 5 == 0) {
                        buzz.release();
                    }
                    // fizzbuzz 开始打印
                    else {
                        // fizzbuzz 开始打印
                        fizzbuzz.release();
                    }


                }
            }
        }
    }

    static class Solution3{
        /**
         * Semaphore是用来保护一个或者多个共享资源的访问，Semaphore内部维护了一个计数器，其值为可以访问的共享资源的个数。
         * 一个线程要访问共享资源，先获得信号量，如果信号量的计数器值大于1，意味着有共享资源可以访问，则使其计数器值减去1，再访问共享资源。
         * 如果计数器值为0,线程进入休眠。
         * 当某个线程使用完共享资源后，释放信号量，并将信号量内部的计数器加1，之前进入休眠的线程将被唤醒并再次试图获得信号量。
         * 主要控制逻辑在number线程，控制哪个线程的启动，其他线程负责按条件打印
         */
        static class FizzBuzz{
            private int n;
            private Semaphore semaphore = new Semaphore(1);
            private int cur = 1;

            public FizzBuzz(int n) {
                this.n = n;
            }

            // printFizz.run() outputs "fizz".
            public void fizz(Runnable printFizz) throws InterruptedException {
                while (true) {
                    semaphore.acquire(1);
                    try {
                        if (cur > n) {
                            return;
                        }
                        if (cur % 3 == 0 && cur % 5 != 0) {
                            printFizz.run();
                            cur++;
                        }
                    } finally {
                        semaphore.release(1);
                    }
                }
            }

            // printBuzz.run() outputs "buzz".
            public void buzz(Runnable printBuzz) throws InterruptedException {
                while (true) {
                    semaphore.acquire(1);
                    try {
                        if (cur > n) {
                            return;
                        }
                        if ((cur % 3 != 0) && (cur % 5 == 0)) {
                            printBuzz.run();
                            cur++;
                        }
                    } finally {
                        semaphore.release(1);
                    }

                }
            }

            // printFizzBuzz.run() outputs "fizzbuzz".
            public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
                while (true) {
                    semaphore.acquire(1);
                    try {
                        if (cur > n) {
                            return;
                        }
                        if (cur % 15 == 0) {
                            printFizzBuzz.run();
                            cur++;
                        }
                    } finally {
                        semaphore.release(1);
                    }

                }
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void number(IntConsumer printNumber) throws InterruptedException {
                while (true) {
                    semaphore.acquire(1);
                    try {
                        if (cur > n) {
                            return;
                        }
                        if ((cur % 3 != 0) && (cur % 5 != 0)) {
                            printNumber.accept(cur);
                            cur++;
                        }
                    } finally {
                        semaphore.release(1);
                    }
                }
            }
        }
    }

    /**
     * volatile + Thread.yield()
     */
    static class Solution4{
        static class FizzBuzz{
            private int n;
            private volatile int state = 0;

            public FizzBuzz(int n) {
                this.n = n;
            }

            // printFizz.run() outputs "fizz".
            public void fizz(Runnable printFizz) throws InterruptedException {
                for (int i = 3; i <= n; i += 3) {   //只输出3的倍数(不包含15的倍数)
                    if (i % 15 == 0) {
                        continue;   //15的倍数不处理，交给fizzbuzz()方法处理
                    }
                    while (state != 3) {
                        Thread.yield();
                    }
                    printFizz.run();
                    state = 0;
                }
            }

            // printBuzz.run() outputs "buzz".
            public void buzz(Runnable printBuzz) throws InterruptedException {
                for (int i = 5; i <= n; i += 5) {   //只输出5的倍数(不包含15的倍数)
                    if (i % 15 == 0)    //15的倍数不处理，交给fizzbuzz()方法处理
                    {
                        continue;
                    }
                    while (state != 5) {
                        Thread.yield();
                    }
                    printBuzz.run();
                    state = 0;
                }
            }

            // printFizzBuzz.run() outputs "fizzbuzz".
            public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
                for (int i = 15; i <= n; i += 15) {   //只输出15的倍数
                    while (state != 15) {
                        Thread.yield();
                    }
                    printFizzBuzz.run();
                    state = 0;
                }
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void number(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; ++i) {
                    while (state != 0) {
                        Thread.yield();
                    }
                    if (i % 3 != 0 && i % 5 != 0) {
                        printNumber.accept(i);
                    }
                    else {
                        if (i % 15 == 0) {
                            state = 15;    //交给fizzbuzz()方法处理
                        }
                        else if (i % 5 == 0) {
                            state = 5;    //交给buzz()方法处理
                        }
                        else {
                            state = 3;    //交给fizz()方法处理
                        }
                    }
                }
            }
        }
    }

    /**
     * Reentrant + Condition
     */
    static class Solution5{
        static
        class FizzBuzz{
            private int n;
            private ReentrantLock lock = new ReentrantLock();
            int state = 0;
            private Condition condition = lock.newCondition();

            public FizzBuzz(int n) {
                this.n = n;
            }

            // printFizz.run() outputs "fizz".
            public void fizz(Runnable printFizz) throws InterruptedException {
                for (int i = 3; i <= n; i += 3) {
                    if (i % 3 == 0 && i % 5 == 0) {
                        continue;
                    }
                    lock.lock();
                    try {
                        while (state != 3) {
                            condition.await();
                        }
                        printFizz.run();
                        state = 0;
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }

            // printBuzz.run() outputs "buzz".
            public void buzz(Runnable printBuzz) throws InterruptedException {
                for (int i = 5; i <= n; i += 5) {
                    if (i % 3 == 0 && i % 5 == 0) {
                        continue;
                    }
                    lock.lock();
                    try {
                        while (state != 5) {
                            condition.await();
                        }
                        printBuzz.run();
                        state = 0;
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }

            // printFizzBuzz.run() outputs "fizzbuzz".
            public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
                for (int i = 15; i <= n; i += 15) {
                    lock.lock();
                    try {
                        while (state != 15) {
                            condition.await();
                        }
                        printFizzBuzz.run();
                        state = 0;
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }

            // printNumber.accept(x) outputs "x", where x is an integer.
            public void number(IntConsumer printNumber) throws InterruptedException {
                for (int i = 1; i <= n; i++) {
                    lock.lock();
                    try {
                        while (state != 0) {
                            condition.await();
                        }
                        if (i % 3 != 0 && i % 5 != 0) {
                            printNumber.accept(i);
                        }
                        else {
                            if (i % 3 == 0 && i % 5 == 0) {
                                state = 15;
                            }
                            else if (i % 3 == 0) {
                                state = 3;
                            }
                            else if (i % 5 == 0) {
                                state = 5;
                            }
                            condition.signalAll();
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable printFizz = () -> {
            System.out.printf("%s", "fizz");
        };
        Runnable printBuzz = () -> {
            System.out.printf("%s", "buzz");
        };
        Runnable printFizzBuzz = () -> {
            System.out.printf("%s", "fizzbuzz");
        };
        IntConsumer intConsumer = new IntConsumer();
        Solution1.FizzBuzz fb = new Solution1.FizzBuzz(15);
        new Thread(() -> {
            try {
                fb.fizz(printFizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fb.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fb.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fb.number(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    static class IntConsumer{
        public void accept(int i) {
            System.out.printf("%d", i);
        }
    }
}

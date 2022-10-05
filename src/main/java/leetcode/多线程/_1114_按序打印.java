package leetcode.多线程;
//给你一个类： 
//
// 
//public class Foo {
// public void first() { print("first"); }
// public void second() { print("second"); }
// public void third() { print("third"); }
//} 
//
// 三个不同的线程 A、B、C 将会共用一个 Foo 实例。 
//
// 
// 线程 A 将会调用 first() 方法 
// 线程 B 将会调用 second() 方法 
// 线程 C 将会调用 third() 方法 
// 
//
// 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。 
//
// 提示： 
//
// 
// 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。 
// 你看到的输入格式主要是为了确保测试的全面性。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出："firstsecondthird"
//解释：
//有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 
//third() 方法。正确的输出是 "firstsecondthird"。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,3,2]
//输出："firstsecondthird"
//解释：
//输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。正
//确的输出是 "firstsecondthird"。 
//
// 
//
// 
// 
//提示：
//
// 
// nums 是 [1, 2, 3] 的一组排列 
// 
//
// Related Topics 多线程 👍 427 👎 0

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayanwei
 * @date 2022-10-05.
 */
public class _1114_按序打印{
    static class Solution1{
        public static class Foo{
            public Foo() {
            }

            private Semaphore second = new Semaphore(0);
            private Semaphore third = new Semaphore(0);

            public void first(Runnable printFirst) throws InterruptedException {
                // printFirst.run() outputs "first". Do not change or remove this line.
                printFirst.run();
                second.release();
            }

            public void second(Runnable printSecond) throws InterruptedException {
                // printSecond.run() outputs "second". Do not change or remove this line.
                second.acquire();
                printSecond.run();
                second.release();
                third.release();
            }

            public void third(Runnable printThird) throws InterruptedException {
                // printThird.run() outputs "third". Do not change or remove this line.
                third.acquire();
                printThird.run();
                third.release();
            }
        }
    }

    static class Solution2{
        static class Foo{
            private volatile int step = 1;

            public Foo() {

            }

            public void first(Runnable printFirst) throws InterruptedException {

                // printFirst.run() outputs "first". Do not change or remove this line.
                printFirst.run();
                step++;
            }

            public void second(Runnable printSecond) throws InterruptedException {

                while (step != 2) ;
                // printSecond.run() outputs "second". Do not change or remove this line.
                printSecond.run();
                step++;
            }

            public void third(Runnable printThird) throws InterruptedException {

                while (step != 3) ;
                // printThird.run() outputs "third". Do not change or remove this line.
                printThird.run();
            }
        }
    }

    static class Solution3{
        class Foo{
            public Foo() {
                i = 1;
            }

            int i;

            public synchronized void first(Runnable printFirst) throws InterruptedException {
                // printFirst.run() outputs "first". Do not change or remove this line.
                while (i != 1) {
                    wait();
                }
                printFirst.run();
                i = 2;
                notifyAll();
            }

            public synchronized void second(Runnable printSecond) throws InterruptedException {
                // printSecond.run() outputs "second". Do not change or remove this line.
                while (i != 2) {
                    wait();
                }
                printSecond.run();
                i = 3;
                notifyAll();
            }

            public synchronized void third(Runnable printThird) throws InterruptedException {
                // printThird.run() outputs "third". Do not change or remove this line.
                while (i != 3) {
                    wait();
                }
                printThird.run();
                notifyAll();
            }
        }
    }

    static class Solution4{
        class Foo{
            private ReentrantLock lock;
            private Condition condition1, condition2, condition3;
            volatile int i;

            public Foo() {
                i = 1;
                lock = new ReentrantLock();
                condition1 = lock.newCondition();
                condition2 = lock.newCondition();
                condition3 = lock.newCondition();
            }


            public void first(Runnable printFirst) throws InterruptedException {
                // printFirst.run() outputs "first". Do not change or remove this line.
                lock.lock();
                try {
                    while (i != 1) {
                        condition1.await();
                    }
                    // printFirst.run() outputs "first". Do not change or remove this line.
                    printFirst.run();
                    i = 2;
                    condition2.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            public void second(Runnable printSecond) throws InterruptedException {
                // printSecond.run() outputs "second". Do not change or remove this line.
                lock.lock();
                try {
                    while (i != 2) {
                        condition2.await();
                    }
                    printSecond.run();
                    i = 3;
                    condition3.signal();
                } finally {
                    lock.unlock();
                }


            }

            public void third(Runnable printThird) throws InterruptedException {
                // printThird.run() outputs "third". Do not change or remove this line.
                lock.lock();
                try {
                    while (i != 3) {
                        condition3.await();
                    }
                    printThird.run();

                } finally {
                    lock.unlock();
                }

            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        Solution1.Foo foo = new Solution1.Foo();
        new Thread(() -> {
            try {
                foo.first(new Runnable(){
                    @Override
                    public void run() {
                        System.out.print("first");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                foo.second(new Runnable(){
                    @Override
                    public void run() {
                        System.out.print("second");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                foo.third(new Runnable(){
                    @Override
                    public void run() {
                        System.out.print("third");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }
}

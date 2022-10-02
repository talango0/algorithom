package leetcode.多线程;
//给你一个类：
//
//
//class FooBar {
//  public void foo() {
//  for (int i = 0; i < n; i++) {
//   print("foo");
//   }
//  }
//
//  public void bar() {
//  for (int i = 0; i < n; i++) {
//   print("bar");
//  }
//  }
//}
//
//
// 两个不同的线程将会共用一个 FooBar 实例：
//
//
// 线程 A 将会调用 foo() 方法，而
// 线程 B 将会调用 bar() 方法
//
//
// 请设计修改程序，以确保 "foobar" 被输出 n 次。
//
//
//
// 示例 1：
//
//
//输入：n = 1
//输出："foobar"
//解释：这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
//
//
// 示例 2：
//
//
//输入：n = 2
//输出："foobarfoobar"
//解释："foobar" 将被输出两次。
//
//
//
//
// 提示：
//
//
// 1 <= n <= 1000
//
// Related Topics 多线程 👍 146 👎 0


import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayanwei
 * @date 2022-03-12.
 */
public class _1115_交替打印FooBar{

    //BLOCKING Queue
    class FooBar0{
        private int n;
        private BlockingQueue<Integer> bar = new LinkedBlockingQueue<>(1);
        private BlockingQueue<Integer> foo = new LinkedBlockingQueue<>(1);

        public FooBar0(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                foo.put(i);
                printFoo.run();
                bar.put(i);
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                bar.take();
                printBar.run();
                foo.take();
            }
        }
    }

    @Test
    public void testforbar0() throws InterruptedException {
        FooBar0 fooBar0 = new FooBar0(2);
        Runnable foo = new Runnable(){
            @Override
            public void run() {
                System.out.print("foo");
            }
        };
        new Thread(()->{
            try {
                fooBar0.foo(foo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Runnable bar = new Runnable(){
            @Override
            public void run() {
                System.out.print("bar");
            }
        };
        fooBar0.bar(bar);
        new Thread(()->{
            try {
                fooBar0.bar(bar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    //CyclicBarrier 控制先后
    class FooBar6{
        private int n;

        public FooBar6(int n) {
            this.n = n;
        }

        CyclicBarrier cb = new CyclicBarrier(2);
        volatile boolean fin = true;

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while (!fin) ;
                printFoo.run();
                fin = false;
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                }
                printBar.run();
                fin = true;
            }
        }
    }

    // 自旋 + 让出CPU
    class FooBar5{
        private int n;

        public FooBar5(int n) {
            this.n = n;
        }

        volatile boolean permitFoo = true;

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; ) {
                if (permitFoo) {
                    printFoo.run();
                    i++;
                    permitFoo = false;
                }
                else {
                    Thread.yield();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; ) {
                if (!permitFoo) {
                    printBar.run();
                    i++;
                    permitFoo = true;
                }
                else {
                    Thread.yield();
                }
            }
        }
    }


    // 可重入锁 + Condition
    class FooBar4{
        private int n;

        public FooBar4(int n) {
            this.n = n;
        }

        Lock lock = new ReentrantLock(true);
        private final Condition foo = lock.newCondition();
        volatile boolean flag = true;

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (!flag) {
                        foo.await();
                    }
                    printFoo.run();
                    flag = false;
                    foo.signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (flag) {
                        foo.await();
                    }
                    printBar.run();
                    flag = true;
                    foo.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    //synchronized + 标志位 + 唤醒
    class FooBar3{
        private int n;
        // 标志位，控制执行顺序，true执行printFoo，false执行printBar
        private volatile boolean type = true;
        private final Object foo = new Object(); // 锁标志

        public FooBar3(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (foo) {
                    while (!type) {
                        foo.wait();
                    }
                    printFoo.run();
                    type = false;
                    foo.notifyAll();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (foo) {
                    while (type) {
                        foo.wait();
                    }
                    printBar.run();
                    type = true;
                    foo.notifyAll();
                }
            }
        }
    }


    //信号量 适合控制顺序
    class FooBar2{
        private int n;
        private Semaphore foo = new Semaphore(1);
        private Semaphore bar = new Semaphore(0);

        public FooBar2(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                foo.acquire();
                printFoo.run();
                bar.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                bar.acquire();
                printBar.run();
                foo.release();
            }
        }
    }


}

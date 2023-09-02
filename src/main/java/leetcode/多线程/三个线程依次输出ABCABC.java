package leetcode.多线程;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayanwei
 * @date 2023-08-17.
 */
public class 三个线程依次输出ABCABC{

    @Test
    public void main() throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.lock();
                    System.out.print("A");

                    c2.signal();
                    if (i + 1 != 10) {
                        c1.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.lock();
                    System.out.print("B");
                    c3.signal();
                    if (i + 1 != 10) {
                        c2.await();
                    }


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {

                    lock.lock();
                    System.out.print("C");
                    c1.signal();
                    if (i + 1 != 10) {
                        c3.await();
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

    }

    /**
     * 利用信号量控制三个线程依次输出ABCABC
     *
     * @throws InterruptedException
     */
    @Test
    public void main2() throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        Semaphore s3 = new Semaphore(0);


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s1.acquire();
                    System.out.print("A");
                    s2.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s2.acquire();
                    System.out.print("B");
                    s3.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s3.acquire();
                    System.out.print("C");
                    s1.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

    }

    @Test
    public void main3() throws InterruptedException {
        Object l1 = new Object();
        Object l2 = new Object();
        Object l3 = new Object();


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (l3) {
                    synchronized (l1) {
                        System.out.print("A");
                        l1.notifyAll();
                    }
                    try {
                        if (i + 1 != 10) {
                            l3.wait();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (l1) {
                    synchronized (l2) {
                        System.out.print("B");
                        l2.notifyAll();

                    }
                    try {
                        if (i + 1 != 10) {
                            l1.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (l2) {
                    synchronized (l3) {
                        System.out.print("C");
                        l3.notifyAll();

                    }
                    if (i + 1 != 10) {
                        try {
                            l2.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }
        });

        t1.start();
        Thread.sleep(100);
        t2.start();
        Thread.sleep(100);
        t3.start();
        t1.join();
        t2.join();
        t3.join();

    }

    @Test
    public void test4() throws InterruptedException {
        AtomicInteger i = new AtomicInteger(0);
        Thread t1 = new Thread(() -> {
            while (i.get() < 30) {
                if (i.get() % 3 == 0) {
                    System.out.print("A");
                    i.getAndIncrement();
                }
                else {
                    Thread.yield();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (i.get() < 30) {
                if (i.get() % 3 == 1) {
                    System.out.print("B");
                    i.getAndIncrement();
                }
                else {
                    Thread.yield();
                }
            }
        });
        Thread t3 = new Thread(() -> {
            while (i.get() < 30) {
                if (i.get() % 3 == 2) {
                    System.out.print("C");
                    i.getAndIncrement();
                }
                else {
                    Thread.yield();
                }
            }
        });
        t1.start();
        Thread.sleep(100);
        t2.start();
        Thread.sleep(100);
        t3.start();
        t1.join();
        t2.join();
        t3.join();

    }
}

package leetcode.多线程;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @author mayanwei
 * @date 2022-10-04.
 */
public class StampedLockDemo{

    public class Point {
        private final StampedLock stampedLock = new StampedLock();

        private double x;
        private double y;

        public void move(double deltaX, double deltaY) {
            long stamp = stampedLock.writeLock(); // 获取写锁
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlockWrite(stamp); // 释放写锁
            }
        }

        public double distanceFromOrigin() {
            long stamp = stampedLock.tryOptimisticRead(); // 获得一个乐观读锁
            // 注意下面两行代码不是原子操作
            // 假设x,y = (100,200)
            double currentX = x;
            // 此处已读取到x=100，但x,y可能被写线程修改为(300,400)
            double currentY = y;
            // 此处已读取到y，如果没有写入，读取是正确的(100,200)
            // 如果有写入，读取是错误的(100,400)
            if (!stampedLock.validate(stamp)) { // 检查乐观读锁后是否有其他写锁发生
                stamp = stampedLock.readLock(); // 获取一个悲观读锁
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp); // 释放悲观读锁
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }

    @Test
    public void testReadLockCpu100() throws InterruptedException {
        StampedLock lock = new StampedLock();
        Thread t1 = new Thread(() -> {
            // 获取写锁
            lock.writeLock();
            // 模拟程序阻塞等待其他资源
            LockSupport.park();
        });
        t1.start();
        //保证 t1 获得写锁
        Thread.sleep(100);

        Thread t2 = new Thread(() -> {
            // 阻塞在悲观读锁
            lock.readLock();
            //try {
            //    lock.readLockInterruptibly();
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        });
        t2.start();
        // 保证 t2 阻塞在读锁
        Thread.sleep(100);

        // 中断线程2会导致 t2 所在的cpu飙升
        t2.interrupt();
        t2.join();
    }
}

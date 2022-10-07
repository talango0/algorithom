package leetcode.多线程;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayanwei
 * @date 2022-10-07.
 */
public class _1226_哲学家进餐{
    static class Solution1{
        static class DiningPhilosophers{
            // 叉子的数目时固定的(个数为 5)，直觉来讲 3个人去抢 5个叉子比 4个人去抢5个叉子的效率高
            // 最多 4个人持有叉子
            private Semaphore eatLimit = new Semaphore(4);
            // 一共5个叉子，是为5个 ReentrantLock, 并将它们全放入 1 个数组中。

            private final ReentrantLock[] lockList = {
                    new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock()
            };

            public DiningPhilosophers() {

            }

            // call the run() method of any runnable to execute its code
            public void wantsToEat(int philosopher,
                                   Runnable pickLeftFork,
                                   Runnable pickRightFork,
                                   Runnable eat,
                                   Runnable putLeftFork,
                                   Runnable putRightFork) throws InterruptedException {
                // 左边的叉子的编号
                int leftFork = (philosopher + 1) % 5;
                // 右边的叉子的编号
                int rightFork = philosopher;

                // 限制的人数 -1
                eatLimit.acquire();

                // 拿起左边的叉子
                lockList[leftFork].lock();
                // 拿起右边的叉子
                lockList[rightFork].lock();

                // 拿起左边的叉子的具体执行
                pickLeftFork.run();
                // 拿起右边的叉子的具体执行
                pickRightFork.run();
                // 吃面的具体执行
                eat.run();
                // 放左边的叉子的具体执行
                putLeftFork.run();
                // 放右边的叉子的具体执行
                putRightFork.run();
                // 放下左边的叉子
                lockList[leftFork].unlock();
                // 放下右边的叉子
                lockList[rightFork].unlock();

                // 限制的人数 +1
                eatLimit.release();

            }
        }
    }

    static class Solution2{
        static class DiningPhilosophers{

            //1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
            private final ReentrantLock[] lockList = {new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock(),
                    new ReentrantLock()};

            //让 1个哲学家可以 “同时”拿起2个叉子(搞个临界区)
            private ReentrantLock pickBothForks = new ReentrantLock();

            public DiningPhilosophers() {

            }

            // call the run() method of any runnable to execute its code
            public void wantsToEat(int philosopher,
                                   Runnable pickLeftFork,
                                   Runnable pickRightFork,
                                   Runnable eat,
                                   Runnable putLeftFork,
                                   Runnable putRightFork) throws InterruptedException {

                int leftFork = (philosopher + 1) % 5;    //左边的叉子 的编号
                int rightFork = philosopher;    //右边的叉子 的编号

                pickBothForks.lock();    //进入临界区
                try {
                    lockList[leftFork].lock();    //拿起左边的叉子
                    lockList[rightFork].lock();    //拿起右边的叉子

                    pickLeftFork.run();    //拿起左边的叉子 的具体执行
                    pickRightFork.run();    //拿起右边的叉子 的具体执行

                } finally {
                    pickBothForks.unlock();    //退出临界区
                }

                eat.run();    //吃意大利面 的具体执行

                putLeftFork.run();    //放下左边的叉子 的具体执行
                putRightFork.run();    //放下右边的叉子 的具体执行

                lockList[leftFork].unlock();    //放下左边的叉子
                lockList[rightFork].unlock();    //放下右边的叉子
            }
        }
    }
}

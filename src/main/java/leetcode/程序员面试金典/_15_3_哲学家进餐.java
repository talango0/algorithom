package leetcode.程序员面试金典;

import leetcode.多线程._1226_哲学家进餐;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//15.3 哲学家用餐。在著名的哲学家用餐问题中，一群哲学家围坐在圆桌周围，每两位哲 学家之间有一根筷子。
// 每位哲学家需要两根筷子才能用餐，并且一定会先拿起左手边的筷子， 然后才会去拿右手边的筷子。如果所有
// 哲学家在同一时间拿起左手边的筷子，就有可能造成死 锁。请使用线程和锁，编写代码模拟哲学家用餐问题，避免出现死锁。

/**
 * @author mayanwei
 * @date 2023-06-02.
 * @see _1226_哲学家进餐
 */
public class _15_3_哲学家进餐{
    // 首先不管思索，写出代码模拟哲学家进餐问题。
    // 叉子，拿起叉子会调用 lock.lock()，放下叉子会调用 lock.unlock
    class Solution{
        class ChopStick{
            private final int num;
            private Lock lock;

            public ChopStick(int num) {
                this.num = num;
                this.lock = new ReentrantLock();
            }

            public void pickUp() {
                System.out.println("拿起筷子" + num);
                lock.lock();
            }

            public void putDown() {
                System.out.println("放下叉子" + num);
                lock.unlock();
            }
        }

        class Philosopher extends Thread{
            private int bites = 10;
            private ChopStick left, right;

            public Philosopher(ChopStick left, ChopStick right) {
                this.left = left;
                this.right = right;
            }

            public void eat() {
                pickUp();
                chew();
                pickDown();
            }

            private void pickDown() {
                left.putDown();
                right.putDown();
            }

            private void chew() {
                System.out.println("吃...");

            }

            private void pickUp() {
                left.pickUp();
                right.pickUp();
            }

            @Override
            public void run() {
                for (int i = 0; i < bites; i++) {
                    eat();
                }
            }
        }


        public void test() {
            Philosopher[] philosophers = new Philosopher[5];
            ChopStick[] chopSticks = new ChopStick[5];
            for (int i = 0; i < chopSticks.length; i++) {
                chopSticks[i] = new ChopStick(i);
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i] = new Philosopher(chopSticks[i], chopSticks[(i + 1) % chopSticks.length]);
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i].start();
            }

        }
    }

    @Test
    public void test() throws InterruptedException {
        new Solution().test();
        Thread.sleep(1000 * 3600);
    }


    // 如果所有的哲学家都拿起了左筷子，并等着拿右筷子，运行上面的代码就可能出现死锁。
    // 死锁发生的条件：
    // 1.互斥条件：临界资源只能被一个进程使用。
    // 2.请求和保持条件：一个进程因请求资源而阻塞，对已获得的资源保持不放。
    // 3.不可剥夺条件：进程已获得资源，在未使用完成之前，不能强行剥夺。
    // 4.循环等待条件：若干进程形成一种头尾相连的循环等待资源关系。

    /**
     * 解法1 ：全部或无
     * 为防止发生死锁，我们的实现可以采用如下策略：如果哲学家拿不到右手边的筷子，就让它放下一拿在手中的筷子。
     */
    class Solution2{
        public class Chopsticks{
            private Lock lock;

            public Chopsticks() {
                this.lock = new ReentrantLock();
            }

            public boolean pickUp() {
                System.out.println("拿起筷子");
                return lock.tryLock();
            }

            public void putDown() {
                System.out.println("放下叉子");
                lock.unlock();
            }
        }

        class Philosopher extends Thread{
            private int bites = 10;
            private Chopsticks left, right;

            public Philosopher(Chopsticks left, Chopsticks right) {
                this.left = left;
                this.right = right;
            }

            public void eat() {
                if (pickUp()) {
                    chew();
                    pickDown();
                }

            }

            private void pickDown() {
                left.putDown();
                right.putDown();
            }

            private void chew() {
                System.out.println("吃...");

            }

            private boolean pickUp() {
                // 试着拿起筷子
                if (!left.pickUp()) {
                    return false;
                }
                if (!right.pickUp()) {
                    left.putDown();
                    return false;
                }
                return true;
            }

            @Override
            public void run() {
                for (int i = 0; i < bites; i++) {
                    eat();
                }
            }


        }

        // 该方案去包拿不到右手边筷子时就放下左手边的筷子，如果手上根本没有筷子，就不该调用putDown() 方法。
        // 一个问题时，如果所有哲学家都完全同步，他们可以同时拿起左手边筷子，无法拿起右手边筷子，然后把筷子放回左手边，会不断重复该过程。
        public void test() {
            Philosopher[] philosophers = new Philosopher[5];
            Chopsticks[] chopSticks = new Chopsticks[5];
            for (int i = 0; i < chopSticks.length; i++) {
                chopSticks[i] = new Chopsticks();
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i] = new Philosopher(chopSticks[i], chopSticks[(i + 1) % chopSticks.length]);
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i].start();
            }

        }
    }

    @Test
    public void test2() throws InterruptedException {
        new Solution2().test();
        Thread.sleep(1000 * 3600);
    }

    /**
     * 解法2，区分筷子优先级
     * <p>
     * 我们用数字 0~N-1记录筷子。每位哲学家首先尝试拿起较低编号的筷子。
     * 每位哲学家都会选择右手边之前选择左手边的筷子（假设这是你标记它的方式），除了最后一位哲学家以相反的方式取筷子。这将打破循环。
     * <p>
     * 这个解决方案，一位哲学家就不能拿着较大编号的筷子而不拿这拿较小编号的筷子，阻止了循环发生，因为循环意味着更高优先级的筷子会指向
     * 优先级更低的筷子。
     */
    class Solution3{
        public class Chopsticks{
            private int number;
            private Lock lock;

            public Chopsticks(int n) {
                this.number = n;
                this.lock = new ReentrantLock();
            }

            public void pickUp() {
                System.out.println("拿起筷子");
                lock.lock();
            }

            public void putDown() {
                System.out.println("放下叉子");
                lock.unlock();
            }

            public int getNumber() {
                return number;
            }
        }

        class Philosopher extends Thread{
            private int bites = 5;
            private Chopsticks lower, higher;
            private int index;

            public Philosopher(int i, Chopsticks left, Chopsticks right) {
                this.index = i;
                if (left.getNumber() < right.getNumber()) {
                    this.lower = left;
                    this.higher = right;
                }
                else {
                    this.lower = right;
                    this.higher = left;
                }
            }

            public void eat() {
                pickUp();
                chew();
                pickDown();
            }

            private void pickDown() {
                higher.putDown();
                lower.putDown();
            }

            private void chew() {
                System.out.println("吃...");

            }

            private void pickUp() {
                // 试着拿起筷子
                lower.pickUp();
                higher.pickUp();
            }

            @Override
            public void run() {
                for (int i = 0; i < bites; i++) {
                    eat();
                }
            }

        }

        public void test() {
            Philosopher[] philosophers = new Philosopher[5];
            Chopsticks[] chopSticks = new Chopsticks[5];
            for (int i = 0; i < chopSticks.length; i++) {
                chopSticks[i] = new Chopsticks(i);
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % chopSticks.length]);
            }
            for (int i = 0; i < philosophers.length; i++) {
                philosophers[i].start();
            }

        }
    }

    @Test
    public void test3() throws InterruptedException {
        new Solution3().test();
        Thread.sleep(1000 * 3600);
    }

}

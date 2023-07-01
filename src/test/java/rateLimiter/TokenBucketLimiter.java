package rateLimiter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 令牌捅算法
 *
 * @author mayanwei
 * @date 2022-12-17.
 */
public class TokenBucketLimiter{
    /**
     * 桶大小/容量
     */
    private final int bucketSize;
    /**
     * 桶中令牌数
     */
    private AtomicInteger tokenSize;
    /**
     * 产生令牌的速度 n/s,可以调整产生令牌的速度调节
     */
    private volatile int rate;


    public TokenBucketLimiter(int bucketSize, int rate) {
        this.bucketSize = bucketSize;
        this.rate = rate;
        this.tokenSize = new AtomicInteger(0);
        new Thread(() -> {
            while (true) {
                if (tokenSize.get() < this.bucketSize) {
                    tokenSize.incrementAndGet();
                }
                try {
                    Thread.sleep(1000 / this.rate);
                } catch (InterruptedException e) {
                    //just do nothing
                }
            }

        }).start();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public synchronized boolean tryAcquire() {
        if (tokenSize.get() > 0) {
            tokenSize.decrementAndGet();
            return true;
        }
        return false;
    }
}

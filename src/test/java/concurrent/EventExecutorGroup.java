package concurrent;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 负责通过 next() 方法给用户提供 {@link  EventExecutor} 执行器。
 * 负责管理这些  {@link  EventExecutor} 的生命周期，提供全局关闭的操作。
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface EventExecutorGroup extends ScheduledExecutorService, Iterable<EventExecutor>{
    /**
     * Returns one of {@link EventExecutor}s managed by this {@link EventExecutorGroup} .
     * @return
     */
    EventExecutor next();

    boolean isShuttingDown();

    Future<?> shutDownGracefully();
    Future<?> shutDownGracefully(long quitPeriod, long timeout, TimeUnit timeUnit);

    /**
     * Return the Future which is notified when all EventExecutors managed by EventExecutorGroup
     * have been terminated.
     * @return
     */
    Future<?> terminationFuture();

    @Override
    Iterator<EventExecutor> iterator();

    @Override
    Future<?> submit(Runnable task);

    @Override
    <T> Future<T> submit(Runnable task, T result);

    @Override
    <T> Future<T> submit(Callable<T> task);

    @Override
    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

    @Override
    <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

    @Override
    ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);

    @Override
    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
}

package concurrent;

/**
 * {@link EventExecutor} 是一个特殊的 {@link EventExecutorGroup} ,提供了检测 {@link Thread} 是否在 event loop 执行的方法。
 * 拓展了  {@link EventExecutorGroup} 允许更加通用的获取方法。
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface EventExecutor extends EventExecutorGroup{
    /**
     * return the reference to itself
     * @return
     */
    @Override
    EventExecutor next();

    /**
     * Return the  {@link EventExecutorGroup} which is the parent of the {@link EventExecutor}。
     * @return
     */
    EventExecutorGroup parent();

    boolean inEventLoop();

    /**
     * Return true if the given {@link Thread} is executed in the Event loop, false otherwise.
     * @param thread
     * @return
     */
    boolean inEventLoop(Thread thread);

    /**
     * Return a new {@link Promise}
     * @return
     * @param <V>
     */
    <V> Promise<V> newPromise();

    /**
     * Create a new {@link ProgressivePromise}
     * @return
     * @param <V>
     */
    <V> ProgressivePromise<V> newProgressivePromise();

    /**
     * Create a new {@link Future} which is marked as succeeded already.
     * {@link  Future#isSuccess()} will return true. All {@link FutureListener} added to it
     * will be notified directly.
     * Also, every call of blocking methods will just return without blocking.
     *
     * @param result
     * @return
     * @param <V>
     */
    <V> ProgressivePromise<V> newSucceededFuture(V result);

    /**
     * Create a new Future which is marked as succeeded already.{@link Future#isSuccess()} will return false.
     * All FutureListener added to it will be notified directly.
     * Also, every call of blocking methods will just return without blocking.
     * @param cause
     * @return
     * @param <V>
     */
    <V> ProgressivePromise<V> newFailedFuture(Throwable cause);


}

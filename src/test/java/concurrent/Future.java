package concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;

/**
 * The Result of an asynchronous operation.
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface Future<V> extends java.util.concurrent.Future<V> {
    boolean isSuccess();

    boolean isCancellable();

    Throwable cause();

    Future<V> addListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);
    Future<V> addListeners(GenericProgressiveFutureListener<? extends Future<? super V>> ... listener);

    Future<V> removeListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);
    Future<V> removeListeners(GenericProgressiveFutureListener<? extends Future<? super V>> ... listener);

    /**
     * Waits for the future until it is done, and rethrows the cause of the failure if the future failed.
     * @return
     * @throws InterruptedException
     */
    Future<V> sync() throws  InterruptedException;

    /**
     * Waits for the future unit it is done, and rethrows the cause of the failure if the future failed.
     * @return
     */
    Future<V> syncUninterruptible();

    /**
     * waits for this future to be completed.
     * @return
     * @throws InterruptedException
     */
    Future<V> await() throws InterruptedException;


    /**
     * Waits for this future to be completed without interruption. This method catchs an
     * {@link InterruptedException} and discards it silently.
     * @return
     */
    Future<V> awaitUninterruptibly();

    /**
     * Waits for the future to be completed within the specified time limit.
     * @param timeout
     * @param timeUnit
     * @return true if and only if the future was completed within the specified time limit.
     * @throws InterruptedException
     */
    boolean await(long timeout, TimeUnit timeUnit) throws InterruptedException;

    boolean await(long timeoutMillis) throws InterruptedException;

    /**
     * Wait for the future to be completed within the specified time limit without interruption.
     * This method catches an {@link InterruptedException} and discards it silently.
     * @param timeout
     * @param unit
     * @return
     */
    boolean awaitUninterruptibly(long timeout, TimeUnit unit);
    boolean awaitUninterruptibly(long timeoutMillis);

    /**
     * Return the result without blocking. If the future is not done yet this will return null. As it is
     * possible that a null value is used to mark the future as successful you also need to check if the
     * future is really done with {@link #isDone()} and not rely on the return null value.
     * @return
     */
    V getNow();

    /**
     * {@inheritDoc}
     *
     * If the cancellation was successful it will fail the future with a {@link CancellationException}
     * @param mayInterruptIfRunning {@code true} if the thread executing this
     * task should be interrupted; otherwise, in-progress tasks are allowed
     * to complete
     * @return
     */
    boolean cancel(boolean mayInterruptIfRunning);

}

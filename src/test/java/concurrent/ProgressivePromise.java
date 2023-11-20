package concurrent;

/**
 * A {@link Future} which is writable.
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface ProgressivePromise<V> extends Promise<V>, ProgressiveFuture<V>{


    /**
     * Sets the current progress of the operation and notifies the listeners that implement
     * {@link GenericProgressiveFutureListener}
     * @param progress
     * @param total
     * @return
     */
    ProgressivePromise<V> setPromise(long progress, long total);

    /**
     * Tries to set the current progress of the operation and notifies the listeners that implement
     * {@link GenericProgressiveFutureListener}. If the operation is already complete or the progress is out of
     * range, this method does nothing but return false.
     * @param progress
     * @param total
     * @return
     */
    boolean tryProgress(long progress, long total);

    @Override
    ProgressivePromise<V> setSuccess(V result);

    @Override
    ProgressivePromise<V> setFailed(Throwable cause);

    @Override
    ProgressiveFuture<V> addListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);

    @Override
    ProgressiveFuture<V> addListeners(GenericProgressiveFutureListener<? extends Future<? super V>>... listener);

    @Override
    ProgressiveFuture<V> removeListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);

    @Override
    ProgressiveFuture<V> removeListeners(GenericProgressiveFutureListener<? extends Future<? super V>>... listener);

    @Override
    ProgressiveFuture<V> sync() throws InterruptedException;

    @Override
    ProgressiveFuture<V> syncUninterruptible();

    @Override
    ProgressiveFuture<V> await() throws InterruptedException;

    @Override
    ProgressiveFuture<V> awaitUninterruptibly();
}

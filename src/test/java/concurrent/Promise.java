package concurrent;


/**
 * Special {@link Future} which is writable
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface Promise<V> extends Future<V>{
    Promise<V> setSuccess(V result);

    boolean trySuccess(V result);

    Promise<V> setFailed(Throwable cause);

    boolean tryFailed(Throwable cause);

    /**
     * Make this future is impossible to cancel.
     * @return : true if and only if successfully marked this future as uncancellable or it is already done
     * without being cancelled. false if this future has been cancelled already.
     */
    boolean setUncancelable();

    @Override
    Future<V> addListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);

    @Override
    Future<V> addListeners(GenericProgressiveFutureListener<? extends Future<? super V>>... listener);

    @Override
    Future<V> removeListener(GenericProgressiveFutureListener<? extends Future<? super V>> listener);

    @Override
    Future<V> removeListeners(GenericProgressiveFutureListener<? extends Future<? super V>>... listener);

    @Override
    Future<V> sync() throws InterruptedException;

    @Override
    Future<V> syncUninterruptible();

    @Override
    Future<V> await() throws InterruptedException;

    @Override
    Future<V> awaitUninterruptibly();
}

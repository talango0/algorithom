package concurrent;


/**
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface ProgressiveFuture<V> extends Future<V>{
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

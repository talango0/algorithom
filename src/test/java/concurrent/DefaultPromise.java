package concurrent;

import concurrent.util.SystemPropertyUtil;
import concurrent.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static concurrent.util.ObjectUtil.checkNotNull;

/**
 * @author mayanwei
 * @date 2023-11-19.
 */
public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V>{
    private static final Logger logger = LoggerFactory.getLogger(DefaultPromise.class);

    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt(
            "demo.defaultPromise.maxListenerStackDepth", 8));

    private static final Object SUCCESS = new Object();
    private static final Object UNCANCELLABLE = new Object();
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(
            new CancellationException(), DefaultPromise.class, "cancel(...)"
    ));
    private static final StackTraceElement [] CANCELLATION_STACK = CANCELLATION_CAUSE_HOLDER.cause.getStackTrace();

    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATE =
            AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
    private Object result;
    private final EventExecutor executor;
    /**
     * one or more listeners. Can be a {@link GenericFutureListener} or a {@link DefaultFutureListeners}
     */
    private Object listeners;
    /**
     * Threading - synchronized(this). We are required to hold the monitor to use Java's underlying wait()/notifyAll().
     */
    private short waiters;

    /**
     * Threading - synchronized(this). We must prevent concurrent notification and FIFO listener notification.
     */
    private boolean notifyingListeners;



    public DefaultPromise() {
        executor = null;
    }

    /**
     * Creates a new instance. It preferable to use {@link EventExecutor#newPromise()} to create a new Promise.
     * @param executor the {@link EventExecutor} which is used to notify the promise once it is complete. It is
     *                 assumed this executor will protect
     */
    public DefaultPromise(EventExecutor executor) {
        this.executor = checkNotNull(executor, "executor");
    }

    @Override
    public Promise<V> setSuccess(V result) {
        if (setSuccess0(result)){
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    private boolean setSuccess0(V result) {
        return setValue0(result == null ? SUCCESS : result);
    }

    private boolean setValue0(Object objResult) {
        if (RESULT_UPDATE.compareAndSet(this, null, objResult) ||
            RESULT_UPDATE.compareAndSet(this, UNCANCELLABLE, objResult)) {
            if (checkNotifyWaiters()) {
                notifyingListeners();
            }
            return true;
        }
        return false;
    }

    /**
     * Check if there are any waiters and if so notify these.
     * @return true if there are any listeners attached to the promise, false otherwise.
     */
    private synchronized boolean checkNotifyWaiters() {
        if (waiters > 0) {
            notifyAll();
        }
        return listeners != null;
    }

    private static ThreadLocal<Integer> threadLocals =  new ThreadLocal<Integer>();
    private void notifyingListeners() {
        EventExecutor executor = executor();
        if (executor.inEventLoop()) {
            Integer depth = threadLocals.get();
            if (depth == null) {
                depth = 0;
            }
            if (depth < MAX_LISTENER_STACK_DEPTH) {
                try {

                    threadLocals.set(depth + 1);
                    notifyListenersNow();
                } finally {
                    threadLocals.set(depth);
                }
                return;
            }
        }
        safeExecute(executor, new Runnable() {
            @Override
            public void run() {
                notifyListenersNow();
            }
        });
    }

    private void notifyListenersNow() {
        Object listeners;
        synchronized (this) {
            if (notifyingListeners || this.listeners == null) {
                return;
            }
            notifyingListeners = true;
            listeners = this.listeners;
            this.listeners = null;
        }
        for (;;) {
            if (listeners instanceof DefaultFutureListeners) {
                notifyListeners0((DefaultFutureListeners) listeners);
            } else {
                notifyListeners0(this, (GenericFutureListener<?>)listeners);
            }
            
            synchronized (this) {
                if(this.listeners == null) {
                    notifyingListeners = false;
                    return;
                }
                listeners = this.listeners;
                this.listeners = null;
            }
        }
    }

    private void notifyListeners0(Future future, GenericFutureListener<?> l) {
        try {
            l.operationComplete(future);
        } catch (Throwable t) {
            if (logger.isDebugEnabled()) {
                logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
            }
        }
    }

    private void notifyListeners0(DefaultFutureListeners listeners) {
        GenericFutureListener<? extends Future<?>>[] a = listeners.listeners();
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            notifyListeners0(this, a[i]);
        }
    }

    private void safeExecute(EventExecutor executor, Runnable task) {
        try {
            executor.execute(task);
        } catch (Throwable t) {
            logger.error("Failed to submit a listener notification task.", t);
        }

    }

    /**
     * Get the executor used to notify listeners when this promise is complete.
     *
     * It is assumed this executor will protect against {@link StackOverflowError} exceptions. This
     * executor may be used to avoid {@link StackOverflowError} by executing a {@link Runnable}
     * if the stack depth exceeds a threshold.
     * @return The executor used to notify listeners when this promise is complete.
     */
    protected EventExecutor executor() {
        return executor;
    }


    private static final class CauseHolder {
        final Throwable cause;
        CauseHolder(Throwable cause) {
            this.cause = cause;
        }
    }


}

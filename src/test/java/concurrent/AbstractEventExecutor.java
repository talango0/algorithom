package concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for EventExecutor implemention
 * @author mayanwei
 * @date 2023-11-19.
 */
public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor{
    private static Logger logger = LoggerFactory.getLogger(AbstractEventExecutor.class);
    static final long DEFAULT_SHUTDOWN_QUIT_PERIOD = 2;
    static final long DEFAULT_SHUTDOWN_TIMEOUT = 15;
    private final EventExecutorGroup parent;
    private final Collection<EventExecutor> selfCollection = Collections.singleton(this);

    protected AbstractEventExecutor(EventExecutorGroup parent) {
        this.parent = parent;
    }

    protected AbstractEventExecutor() {
        this(null);
    }

    @Override
    public EventExecutorGroup parent() {
        return parent;
    }

    @Override
    public EventExecutor next() {
        return this;
    }

    @Override
    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return selfCollection.iterator();
    }

    @Override
    public Future<?> shutDownGracefully() {
        return shutDownGracefully(DEFAULT_SHUTDOWN_QUIT_PERIOD, DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * @deprecated {@link  #shutDownGracefully()} or {@link #shutDownGracefully(long, long, TimeUnit)} instead.
     */
    @Override
    @Deprecated
    public abstract void shutdown();

    /**
     * @deprecated {@link  #shutDownGracefully()} or {@link #shutDownGracefully(long, long, TimeUnit)} instead.
     */
    @Override
    @Deprecated
    public List<Runnable> shutdownNow(){
        shutdown();
        return Collections.emptyList();
    }

    @Override
    public <V> Promise<V> newPromise() {
        return new DefaultPromise<V>(this);
    }
}

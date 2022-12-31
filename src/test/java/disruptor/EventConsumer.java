package disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Consumer that consumes event from ring buffer.
 * @author mayanwei
 * @date 2022-12-10.
 */
public interface EventConsumer{
    /**
     * One or more event handler to handle event from ring buffer.
     */
    public EventHandler<ValueEvent>[] getEventHandler();
}

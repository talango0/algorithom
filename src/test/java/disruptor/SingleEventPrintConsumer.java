package disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mayanwei
 * @date 2022-12-10.
 */
public class SingleEventPrintConsumer implements EventConsumer{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);
        return new EventHandler[] { eventHandler };
    }
    private void print(final int id, final long sequenceId) {
        System.out.println("Id is " + id + " sequence id that was used is " + sequenceId);
        logger.info("Id is " + id + " sequence id that was used is " + sequenceId);
    }
}

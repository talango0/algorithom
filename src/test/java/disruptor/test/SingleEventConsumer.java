package disruptor.test;

import com.lmax.disruptor.EventHandler;
import disruptor.EventConsumer;
import disruptor.ValueEvent;

import static org.junit.Assert.assertEquals;

/**
 * @author mayanwei
 * @date 2022-12-10.
 */
public class SingleEventConsumer implements EventConsumer{
    private int expectedValue = -1;

    @Override
    @SuppressWarnings("unchecked")
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> assertExpectedValue(event.getValue());
        return new EventHandler[] { eventHandler };
    }

    private void assertExpectedValue(final int id) {
        assertEquals(++expectedValue, id);
    }


}

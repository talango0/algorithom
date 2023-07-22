package disruptor;

import com.lmax.disruptor.EventFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Firstly we will define the Event that will carry the data and is common to all following
 * @author mayanwei
 * @date 2022-12-10.
 */
public class ValueEvent{
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /***
     * In order to allow the Disruptor to preallocate these events for us,
     * we need to an EventFactory that will perform the construction.
     * This could be a method reference, such as LongEvent::new
     * or an explicit implementation of the EventFactory interface
     */
    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

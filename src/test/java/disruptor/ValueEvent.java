package disruptor;

import com.lmax.disruptor.EventFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
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

    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

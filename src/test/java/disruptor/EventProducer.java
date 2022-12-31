package disruptor;

import com.lmax.disruptor.RingBuffer;
import disruptor.test.DisruptorTest;

/**
 * Producer that produces event for ring buffer.
 * @author mayanwei
 * @date 2022-12-10.
 */
public interface EventProducer{
    /**
     * Start the producer that would start producing the values.
     * @param ringBuffer
     * @param count
     */
    public void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count);
}

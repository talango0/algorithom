package disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * Finally, we will need a source for these events.
 * For simplicity, we will assume that the data is coming from some sort of I/O device,
 * e.g. network or file in the form of a ByteBuffer.
 * @author mayanwei
 * @date 2022-12-10.
 */
public class DelayedMultiEventProducer implements EventProducer{
    @Override
    public void startProducing(RingBuffer<ValueEvent> ringBuffer, int count) {
        final Runnable simpleProducer = () -> produce(ringBuffer, count, false);
        final Runnable delayedProducer = () -> produce(ringBuffer, count, true);
        new Thread(simpleProducer).start();
        new Thread(delayedProducer).start();
    }

    private void produce(final RingBuffer<ValueEvent> ringBuffer, final int count, final boolean addDelay) {
        for (int i = 0; i < count; i++) {
            final long seq = ringBuffer.next();
            final ValueEvent valueEvent = ringBuffer.get(seq);
            valueEvent.setValue(i);
            ringBuffer.publish(seq);
            if (addDelay) {
                addDelay();
            }
        }
    }

    private void addDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            // No-Op lets swallow it
        }
    }
}

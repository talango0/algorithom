package disruptor;

import com.lmax.disruptor.RingBuffer;


/**
 * @author mayanwei
 * @date 2022-12-10.
 */
public class SingleEventProducer implements EventProducer{
    @Override
    public void startProducing(RingBuffer<ValueEvent> ringBuffer, int count) {
        final Runnable producer = () -> produce(ringBuffer, count);
        new Thread(producer).start();
    }
    private void produce(final RingBuffer<ValueEvent> ringBuffer, final int count) {
        for (int i = 0; i < count; i++) {
            final long seq = ringBuffer.next();
            try {
                final ValueEvent valueEvent = ringBuffer.get(seq);
                valueEvent.setValue(i);
            }
            //It is also necessary to wrap publication in a try/finally block.
            //
            //If we claim a slot in the Ring Buffer (calling RingBuffer#next()) then we must publish this sequence.
            // Failing to do so can result in corruption of the state of the Disruptor.
            //
            //Specifically, in the multi-producer case, this will result in the consumers stalling and being unable
            // to recover without a restart.
            // Therefore, it is recommended that either the lambda or EventTranslator APIs be used.
            finally {
                ringBuffer.publish(seq);
            }
        }
    }
}

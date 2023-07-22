package disruptor.test;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import disruptor.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

/**
 * @author mayanwei
 * @date 2022-12-10.
 */

public class DisruptorTest{
    private static Logger log = LoggerFactory.getLogger(DisruptorTest.class);

    private Disruptor<ValueEvent> disruptor;
    private WaitStrategy waitStrategy;

    @Before
    public void setUp() throws Exception {
        waitStrategy = new BusySpinWaitStrategy();
    }

    private void createDisruptor(final ProducerType producerType, final EventConsumer eventConsumer) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        // Specify the size of the ring buffer, must be power of 2.
        int ringBufferSize = 16;
        // Construct the Disruptor
        disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, ringBufferSize, threadFactory, producerType, waitStrategy);
        // Connect the handler
        disruptor.handleEventsWith(eventConsumer.getEventHandler());
    }

    private void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count, final EventProducer eventProducer) {
        eventProducer.startProducing(ringBuffer, count);
    }

    @Test
    public void testMain() throws Exception {
        int bufferSize = 1024;

        Disruptor<ValueEvent> disruptor =
                new Disruptor<>(ValueEvent.EVENT_FACTORY, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) ->
                System.out.println("Event: " + event));
        disruptor.start();


        RingBuffer<ValueEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(4);
        for (int l = 0; true; l++) {
            bb.putInt(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getInt(0)), bb);
            /*
            This would create a capturing lambda, meaning that it would need to instantiate an object
            to hold the ByteBuffer bb variable as it passes the lambda through to the publishEvent() call.
            This will create additional (unnecessary) garbage, so the call that passes the argument through
            to the lambda should be preferred if low GC pressure is a requirement.
            */
            // ringBuffer.publishEvent((event, sequence) -> event.set(bb.getLong(0)));
            Thread.sleep(1000);
        }
    }

    public static void translate(ValueEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getInt(0));
    }

    @Test
    public void testMain2() throws Exception {
        int bufferSize = 1024;

        Disruptor<ValueEvent> disruptor =
                new Disruptor<>(ValueEvent.EVENT_FACTORY, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) ->
                System.out.println("Event: " + event));
        disruptor.start();


        RingBuffer<ValueEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(4);
        for (int l = 0; true; l++) {
            bb.putInt(0, l);
            //Given that method references can be used instead of anonymous lambdas,
            // it is possible to rewrite the example in this fashion:
            ringBuffer.publishEvent(DisruptorTest::translate, bb);

            Thread.sleep(1000);
        }
    }

    /**
     * This approach uses number of extra classes (e.g. handler, translator)
     * that are not explicitly required when using lambdas.
     * The advantage of here is that the translator code can be pulled into
     * a separate class and easily unit tested.
     */
    static class LongEventProducer{
        private final RingBuffer<ValueEvent> ringBuffer;

        public LongEventProducer(RingBuffer<ValueEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        private static final EventTranslatorOneArg<ValueEvent, ByteBuffer> TRANSLATOR =
                new EventTranslatorOneArg<ValueEvent, ByteBuffer>(){
                    @Override
                    public void translateTo(ValueEvent event, long sequence, ByteBuffer bb) {
                        event.setValue(bb.getInt(0));
                    }
                };

        public void onData(ByteBuffer bb) {
            ringBuffer.publishEvent(TRANSLATOR, bb);
        }
    }


    @Test
    public void whenMultipleProducerSingleConsumer_thenOutputInFifoOrder() throws IOException {
        final EventConsumer eventConsumer = new SingleEventPrintConsumer();
        final EventProducer eventProducer = new DelayedMultiEventProducer();
        createDisruptor(ProducerType.MULTI, eventConsumer);
        // 	Start the Disruptor, starts all threads running
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenSingleProducerSingleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new SingleEventConsumer();
        final EventProducer eventProducer = new SingleEventProducer();
        createDisruptor(ProducerType.SINGLE, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenSingleProducerMultipleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new MultiEventConsumer();
        final EventProducer eventProducer = new SingleEventProducer();
        createDisruptor(ProducerType.SINGLE, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenMultipleProducerMultipleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new MultiEventPrintConsumer();
        final EventProducer eventProducer = new DelayedMultiEventProducer();
        createDisruptor(ProducerType.MULTI, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

}

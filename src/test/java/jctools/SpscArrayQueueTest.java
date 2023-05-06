package jctools;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.jctools.queues.SpscArrayQueue;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThat;


/**
 * @author mayanwei
 * @date 2023-03-19.
 */
public class SpscArrayQueueTest{
    @Test
    public void test1() throws InterruptedException {
        SpscArrayQueue<Integer> queue = new SpscArrayQueue<>(2);

        Thread producer1 = new Thread(() -> queue.offer(1));
        producer1.start();
        producer1.join();

        Thread producer2 = new Thread(() -> queue.offer(2));
        producer2.start();
        producer2.join();

        Set<Integer> fromQueue = new HashSet<>();
        Thread consumer = new Thread(() -> queue.drain(fromQueue::add));
        consumer.start();
        consumer.join();

    }
}

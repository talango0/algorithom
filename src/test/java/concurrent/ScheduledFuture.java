package concurrent;

/**
 * The result of an scheduled asynchronous operation
 * @author mayanwei
 * @date 2023-11-19.
 */
public interface ScheduledFuture<V> extends java.util.concurrent.ScheduledFuture<V>, Future<V>{
}

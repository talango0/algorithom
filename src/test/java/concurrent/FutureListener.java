package concurrent;

/**
 * A subtype of GenericFutureListener that hides type parameter for convenience.
 * <pre>
 *   Future f = new DefaultPromise(..);
 *   f.addListener(new FutureListener() {
 *       public void operationComplete(Future f) { .. }
 *   });
 * </pre>
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface FutureListener<V> extends GenericFutureListener<Future<V>>{
}

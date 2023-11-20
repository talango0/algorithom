package concurrent;

/**
 * Marker interface for {@link EventExecutor} that will process all submitted tasks
 * in ordered / serial fashion.
 * @author mayanwei
 * @date 2023-11-19.
 */
public interface OrderedEventExecutor extends EventExecutor{
}

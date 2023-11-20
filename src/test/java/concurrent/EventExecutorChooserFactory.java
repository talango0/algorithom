package concurrent;

/**
 * Factory that creates new {@link EventExecutorChooser}
 * @author mayanwei
 * @date 2023-11-19.
 */
public interface EventExecutorChooserFactory{
    EventExecutorChooser newChooser(EventExecutor [] executors);

    interface EventExecutorChooser {
        EventExecutor next();
    }
}

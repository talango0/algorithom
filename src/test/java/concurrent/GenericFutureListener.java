package concurrent;

import java.util.EventListener;

/**
 * @author mayanwei
 * @date 2023-11-18.
 */
public interface GenericFutureListener<F extends Future<?>> extends EventListener{
    /**
     * invoke when the operation associated with the {@link Future} has been completed.
     * @param future -- the source {@link Future} which called this callback.
     * @throws Exception
     */
    void operationComplete(F future) throws Exception;
}

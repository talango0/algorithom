package concurrent;

import java.util.EventListener;

/**
 * @author mayanwei
 * @date 2023-11-19.
 */
public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>> extends GenericFutureListener<F>{
    /**
     * invoked when the operation associated with {@link Future} has been completed.
     * @param future the source {@link Future}  which called this callback.
     * @throws Exception
     */
    void operationComplete(F future) throws Exception;
}

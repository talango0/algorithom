package lambda;

import java.util.function.Function;

/**
 * @author mayanwei
 * @date 2023-02-11.
 */
public class RunnableTest{
    void run() {
        Function<Integer, Integer> function = input -> input + 1;
        function.apply(1);
    }
}

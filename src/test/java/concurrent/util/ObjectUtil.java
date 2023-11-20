package concurrent.util;

/**
 * @author mayanwei
 * @date 2023-11-19.
 */
public class ObjectUtil{
    public static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }
}

package arrayoutofbound;

/**
 * @author mayanwei
 * @date 2023-04-23.
 */
public class IaloadDemo{
    public static void main(String[] args) {
        int[] src = {10,20,30,40,50};
        int dst = src[10];//数组访问，越界检查

    }
}

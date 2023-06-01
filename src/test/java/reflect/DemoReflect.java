package reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author mayanwei
 * @date 2023-06-01.
 */
public class DemoReflect{
    // 反射特性：
    // 1. 运行时获取类的方法和字段信息
    // 2. 创建某个类的新实例
    // 3. 通过取得某个字段引用直接获取和设置对象字段，不管访问修饰符为何。
    // 反射的作用：
    // 1. 有助于观察或操纵应用程序的运行行为
    // 2. 有助于调试和测试程序，因为我们可以直接访问方法、构造函数和成员字段
    // 3. 即使事前不知道某个方法，我们可以通过名字调用该方法。例如，让用户传入类名、构造
    // 函数的参数和方法名。然后，我们就可以使用该信息来创建对象，并调用方法。如果没有反射
    // 的话，即使可以做到，也需要一系列复杂的 if 语句。


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        // 参数
        Object[] doubleArgs = {4.2, 7.6};
        // 取得类
        Class<?> rectangleClass = Class.forName("reflect.Rectangle");
        Class[] doubleArgClass = {Double.class, Double.class};

        Constructor<?> doubleArgsConstructor = rectangleClass.getConstructor(doubleArgClass);
        Rectangle rectangle = (Rectangle) doubleArgsConstructor.newInstance(doubleArgs);

        Method getArea = rectangleClass.getDeclaredMethod("getArea");

        Double area = (Double) getArea.invoke(rectangle);
        System.out.println(area);

        // 等同于
        Rectangle rectangle1 = new Rectangle(4.2, 7.6);
        Double area1 = rectangle1.getArea();
        System.out.println(area.equals(area1));

    }

}

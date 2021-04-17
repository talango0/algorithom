package proxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class Test {
    class GrandFather{
        void thinking(){
            System.out.println("I am grandfather");
        }
    }

    class Father extends GrandFather{
        void thinking(){
            System.out.println("I am father");
        }
    }

    class Son extends Father{
        void thinking(){
            MethodType mt = MethodType.methodType(void.class);
            try {
                MethodHandle mh = lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new Test().new Son()).thinking();

    }
}

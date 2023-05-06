package jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * 利用JMX 获取内存池情况以及 GC 信息
 * @author mayanwei
 * @date 2023-04-10.
 */
public class MemoryPoolInfo{
    public void printManagement(){
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {

            System.out.println("name \n"+memoryPoolMXBean.getName());
            System.out.println("usage \n" + memoryPoolMXBean.getUsage());
            System.out.println("type \n" + memoryPoolMXBean.getType());
            System.out.println("==============================");
        }

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            System.out.println("name \n"+garbageCollectorMXBean.getName());
            System.out.println("collection Count \n"+garbageCollectorMXBean.getCollectionCount());
            System.out.println("Memory Pool \n"+garbageCollectorMXBean.getMemoryPoolNames());
            System.out.println("class \n"+garbageCollectorMXBean.getClass());
            System.out.println("==============================");

        }
    }

    public static void main(String[] args) {
        MemoryPoolInfo memoryPoolInfo = new MemoryPoolInfo();
        memoryPoolInfo.printManagement();


    }
}

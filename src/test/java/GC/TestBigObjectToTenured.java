package GC;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

public class TestBigObjectToTenured {

    public static void main(String[] args) {
        byte[] bytes = new byte[11 * 1024 * 1024];
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            System.out.println(memoryPoolMXBean.getName()
                    + " 总量:"
                    + memoryPoolMXBean.getUsage().getCommitted()
                    + " 使用的内存："
                    + memoryPoolMXBean.getUsage().getUsed());

        }
    }
}

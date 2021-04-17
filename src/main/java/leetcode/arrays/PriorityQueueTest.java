package leetcode.arrays;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最大优先队列，最小优先队列
 * @author mayanwei
 */
public class PriorityQueueTest {


    public static void main(String[] args) {
        PriorityQueue<Tmp> queue = new PriorityQueue<>(Comparator.comparingInt((Tmp o) -> o.val));
        queue.add(new Tmp(1, 2));
        queue.add(new Tmp(2,4));
        queue.add(new Tmp(3, 1));
        queue.add(new Tmp(4,5));
        while (!queue.isEmpty()){
            Tmp item = queue.poll();
            System.out.println(item.toString());

        }

    }

    static class Tmp{
        int index;
        int val;

        public Tmp(int index, int val) {
            this.index = index;
            this.val = val;
        }

        public Tmp() {
        }

        @Override
        public String toString() {
            return "Tmp{" +
                    "index=" + index +
                    ", val=" + val +
                    '}';
        }
    }
}

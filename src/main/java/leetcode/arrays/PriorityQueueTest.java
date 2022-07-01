package leetcode.arrays;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最大优先队列，最小优先队列
 * @author mayanwei
 */
public class PriorityQueueTest {


    public static void main(String[] args) {

        PriorityQueue<int[]> maxpq = new PriorityQueue<>((o1, o2) ->  o2[1] -o1[1]);
        int [] nums = new int[]{1,3,2,5};
        for (int i = 0; i < nums.length; i++) {
            maxpq.offer(new int[]{i,  nums[i]});
        }
        while (!maxpq.isEmpty()){
            int[] poll = maxpq.poll();
            System.out.println(poll[0]+"\t"+poll[1]);
        }
    }

    public static void testTemp(String[] args) {
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

package leetcode.jzhoffer;

import leetcode.design._295_数据流的中位数;

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-10-18.
 * @see _295_数据流的中位数
 */
public class 剑指_Offer_41_数据流中的中位数{
    class MedianFinder{
        // 优先级队列,小顶堆,后半部分
        PriorityQueue<Integer> large;
        // 优先级队列,大顶堆,前半部分
        PriorityQueue<Integer> small;

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {
            large = new PriorityQueue<>();
            small = new PriorityQueue<>((a, b) -> b - a);
        }

        public void addNum(int num) {
            if (small.size() >= large.size()) {
                small.offer(num);
                large.offer(small.poll());
            }
            else {
                large.offer(num);
                small.offer(large.poll());
            }
        }

        public double findMedian() {
            if (small.size() == large.size()) {
                return small.peek() + (large.peek() - small.peek()) / 2.0;
            }else {
                return large.peek();
            }

        }
    }

}

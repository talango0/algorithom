package leetcode.design;
//中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
//
// 例如，
//
// [2,3,4] 的中位数是 3
//
// [2,3] 的中位数是 (2 + 3) / 2 = 2.5
//
// 设计一个支持以下两种操作的数据结构：
//
//
// void addNum(int num) - 从数据流中添加一个整数到数据结构中。
// double findMedian() - 返回目前所有元素的中位数。
//
//
// 示例：
//
// addNum(1)
//addNum(2)
//findMedian() -> 1.5
//addNum(3)
//findMedian() -> 2
//
// 进阶:
//
//
// 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
// 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
//
// Related Topics 设计 双指针 数据流 排序 堆（优先队列） 👍 724 👎 0

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-07-23.
 */
public class _295_数据流的中位数{
    class MedianFinder {
        //核心思路，两个优先级队列
        //中位数是有序数组中最中间的元素算出来的
        // 大顶堆 和 小顶堆 两个元素的个数最大不超过1
        // 大顶堆的元素小 称为 small
        // 小顶堆的元素大 称为 large　
        // 假设总数 n 为偶数，希望两个堆的元素个数相等的，这样，中位数就是两个堆顶的元素平均值
        // 假设总数 n 为奇数，希望两个堆的元素个数为 n/2+1、n/2， 这样元素多的那个堆的堆顶元素就是中位数
        // addNum方法时间复杂度 O(logN)，findMedian方法时间复杂度 O(1)。


        private final PriorityQueue<Integer> large;
        private final PriorityQueue<Integer> small;
        public MedianFinder() {
            large = new PriorityQueue<Integer>((a,b) -> b-a );
            small = new PriorityQueue<Integer>((a,b) -> a-b );
        }

        public void addNum(int num) {
            // 不仅要维护 large 和 small 的元素个数之差不超过1，还要维护 large 堆的堆顶元素要大于等于 small堆的堆顶元素。
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
            if (small.size() > large.size()) {
                return small.peek();
            }
            else if( small.size() < large.size()) {
                return large.peek();
            }
            else {
                return (small.peek() + large.peek())/2.0;
            }
        }
    }

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}

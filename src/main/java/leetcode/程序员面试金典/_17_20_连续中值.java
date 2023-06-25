package leetcode.程序员面试金典;
//随机产生数字并传递给一个方法。你能否完成这个方法，在每次产生新值时，寻找当前所有值的中间值（中位数）并保存。
//
//中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
//
//例如，
//
//[2,3,4]的中位数是 3
//
//[2,3] 的中位数是 (2 + 3) / 2 = 2.5
//
//设计一个支持以下两种操作的数据结构：
//
//void addNum(int num) - 从数据流中添加一个整数到数据结构中。
//double findMedian() - 返回目前所有元素的中位数。
//示例：
//
//addNum(1)
//addNum(2)
//findMedian() -> 1.5
//addNum(3) 
//findMedian() -> 2
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/continuous-median-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.design._295_数据流的中位数;

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2023-06-25.
 * @see _295_数据流的中位数
 */
public class _17_20_连续中值{
    class MedianFinder{
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
            large = new PriorityQueue<Integer>((a, b) -> b - a);
            small = new PriorityQueue<Integer>((a, b) -> a - b);
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
            else if (small.size() < large.size()) {
                return large.peek();
            }
            else {
                return (small.peek() + large.peek()) / 2.0;
            }
        }
    }

    class MedianFinder2 {
        class Heap {
            private boolean isMaxHeap;
            private int[] heap;
            private int size;
            private int capacity;

            Heap(boolean isMaxHeap) {
                this.isMaxHeap = isMaxHeap;
                this.capacity = 100;
                this.size = 0;
                this.heap = new int[capacity];
            }

            public int size() {
                return size;
            }

            public void push(int x) {
                if (size + 1 > capacity) {
                    capacity *= 2;
                    int[] newHeap = new int[capacity];
                    for (int i = 0; i < size; i++) newHeap[i] = heap[i];
                    heap = newHeap;
                }
                heap[size++] = x;
                int ptr = size - 1;
                while (ptr > 0) {
                    if ((isMaxHeap && heap[ptr] > heap[(ptr - 1) / 2])
                            || (!isMaxHeap && heap[ptr] < heap[(ptr - 1) / 2])) {
                        int temp = heap[ptr];
                        heap[ptr] = heap[(ptr - 1) / 2];
                        heap[(ptr - 1) / 2] = temp;
                        ptr = (ptr - 1) / 2;
                    } else {
                        break;
                    }
                }
            }

            public int pop() {
                int top = heap[0];
                heap[0] = heap[size - 1];
                size--;
                int ptr = 0;
                while (ptr < size) {
                    int ptrLeftChild = 2 * ptr + 1;
                    int ptrRightChild = 2 * ptr + 2;
                    int ptrSelected = ptr;
                    if (isMaxHeap) {
                        if (ptrLeftChild < size && heap[ptrLeftChild] > heap[ptrSelected]) ptrSelected = ptrLeftChild;
                        if (ptrRightChild < size&& heap[ptrRightChild]> heap[ptrSelected]) ptrSelected = ptrRightChild;
                    } else {
                        if (ptrLeftChild < size && heap[ptrLeftChild] < heap[ptrSelected]) ptrSelected = ptrLeftChild;
                        if (ptrRightChild < size&& heap[ptrRightChild]< heap[ptrSelected]) ptrSelected = ptrRightChild;
                    }
                    if (ptrSelected == ptr)
                        break;
                    int temp = heap[ptr];
                    heap[ptr] = heap[ptrSelected];
                    heap[ptrSelected] = temp;
                    ptr = ptrSelected;
                }
                return top;
            }

            public int top() {
                return heap[0];
            }
        }

        private Heap maxHeap = new Heap(true);
        private Heap minHeap = new Heap(false);

        /** initialize your data structure here. */
        public MedianFinder2() {
        }

        public void addNum(int num) {
            int top = maxHeap.top();
            if (num > top) {
                minHeap.push(num);
            } else {
                maxHeap.push(num);
            }
            while (minHeap.size() > maxHeap.size()) {
                maxHeap.push(minHeap.pop());
            }
            while (maxHeap.size() > minHeap.size() + 1) {
                minHeap.push(maxHeap.pop());
            }
        }

        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return ((double)maxHeap.top() + (double)minHeap.top()) / 2.0;
            } else {
                return maxHeap.top();
            }
        }
    }

}

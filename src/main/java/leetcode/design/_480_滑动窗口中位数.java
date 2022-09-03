package leetcode.design;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 字节
 *
 * @author mayanwei
 * @date 2022-09-03.
 * @see _295_数据流的中位数
 */
public class _480_滑动窗口中位数{
    class Solution{
        /**
         * 利用两个优先队列，大顶堆的元素都比小顶堆的元素小。小顶堆里面存储排序以后中间靠后的值大的元素，大顶堆里面存储
         * 排序以后中间靠前的值小的元素。
         * <p>
         * 如果k是偶数，那么两个堆的元素都是 k/2 个元素，中间值就是两个堆顶的元素；
         * <p>
         * 如果k是奇数，那么小顶堆比大顶堆多一个元素，中间值就是小顶堆的堆顶元素。
         * <p>
         * 删除一个元素，元素都标记到删除的堆中，取top 的时候注意需要取出没有删除的元素。
         * <p>
         * 时间复杂度 O(n*logk)
         * <p>
         * 空间复杂度 O(k)
         */
        public double[] medianSlidingWindow(int[] nums, int k) {
            DualHeap dh = new DualHeap(k);
            for (int i = 0; i < k; ++i) {
                dh.insert(nums[i]);
            }
            double[] ans = new double[nums.length - k + 1];
            ans[0] = dh.getMedian();
            for (int i = k; i < nums.length; ++i) {
                dh.insert(nums[i]);
                dh.erase(nums[i - k]);
                ans[i - k + 1] = dh.getMedian();
            }
            return ans;
        }

        class DualHeap{
            // 大根堆，维护较小的一半元素
            private PriorityQueue<Integer> small;
            // 小根堆，维护较大的一半元素
            private PriorityQueue<Integer> large;
            // 哈希表，记录延迟删除的元素，key为元素，value为需要删除的次数
            private Map<Integer, Integer> delayed;
            private int k;
            // small 和 large 当前包含的元素个数，需要扣除被延迟删除的元素
            private int smallSize, largeSize;

            public DualHeap(int k) {
                this.small = new PriorityQueue<Integer>(new Comparator<Integer>(){
                    public int compare(Integer num1, Integer num2) {
                        return num2.compareTo(num1);
                    }
                });
                this.large = new PriorityQueue<Integer>(new Comparator<Integer>(){
                    public int compare(Integer num1, Integer num2) {
                        return num1.compareTo(num2);
                    }
                });
                this.delayed = new HashMap<Integer, Integer>();
                this.k = k;
                this.smallSize = 0;
                this.largeSize = 0;

            }

            public double getMedian() {
                return ((k & 1) == 1) ? small.peek() :((double) small.peek() + large.peek()) / 2;
            }

            public void insert(int num) {
                if (small.isEmpty() || num <= small.peek()) {
                    small.offer(num);
                    smallSize++;
                }
                else {
                    large.offer(num);
                    largeSize++;
                }
                makeBalance();
            }

            public void erase(int num) {
                delayed.put(num, delayed.getOrDefault(num, 0) + 1);
                if (num <= small.peek()) {
                    smallSize--;
                    if (num == small.peek()) {
                        prune(small);
                    }
                }
                else {
                    largeSize--;
                    if (num == large.peek()) {
                        prune(large);
                    }
                }
                makeBalance();
            }

            // 不断地弹出 heap 的堆顶元素，并且更新哈希表
            private void prune(PriorityQueue<Integer> heap) {
                while (!heap.isEmpty()) {
                    int num = heap.peek();
                    if (delayed.containsKey(num)) {
                        delayed.put(num, delayed.get(num) - 1);
                        if (delayed.get(num) == 0) {
                            delayed.remove(num);
                        }
                        heap.poll();
                    }
                    else {
                        break;
                    }
                }
            }

            // 调整 small 和 largelarge 中的元素个数，使得二者的元素个数满足要求
            private void makeBalance() {
                if (smallSize > largeSize + 1) {
                    // small 比 large 多 2 个
                    large.offer(small.poll());
                    smallSize--;
                    largeSize++;
                    // small堆顶元素被移除，需要进行 prune
                    prune(small);
                }
                else if (smallSize < largeSize) {
                    // large 比 small 元素多1个
                    small.offer(large.poll());
                    smallSize++;
                    largeSize--;
                    prune(large);
                }
            }
        }
    }
}

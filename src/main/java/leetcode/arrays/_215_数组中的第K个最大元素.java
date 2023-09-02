package leetcode.arrays;
//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
// 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
//
//
// 示例 1:
//输入: [3,2,1,5,6,4], k = 2
//输出: 5
//
//
// 示例 2:
//输入: [3,2,3,1,2,4,5,5,6], k = 4
//输出: 4
//
// 提示：
//
//
// 1 <= k <= nums.length <= 10⁵
// -10⁴ <= nums[i] <= 10⁴
//
//
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 👍 1936 👎 0

import leetcode.jzhoffer.剑指_Offer_II_060_出现频率最高的k个数字;
import leetcode.tree._912_排序数组;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author mayanwei
 * @date 2022-11-02.
 * @see 剑指_Offer_II_060_出现频率最高的k个数字
 * @see _912_排序数组
 */
public class _215_数组中的第K个最大元素{

    class Solution{
        public int findKthLargest(int[] nums, int k) {
            // 小顶堆，堆顶是最小元素
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int e : nums) {
                // 每个元素都要过一遍二叉堆
                pq.offer(e);
                // 堆中元素多于 k 个时，删除堆顶元素
                if (pq.size() > k) {
                    pq.poll();
                }
            }
            // pq 中剩下的是 nums 中 k 个最大元素，
            // 堆顶是最小的那个，即第 k 个最大元素
            return pq.peek();
        }
    }

    class Solution1{
        Random random = new Random();

        public int findKthLargest(int[] nums, int k) {
            return quickSelect(nums, 0, nums.length - 1, nums.length - k);
        }

        public int quickSelect(int[] a, int l, int r, int index) {
            int q = randomPartition(a, l, r);
            if (q == index) {
                return a[q];
            }
            else {
                return q < index ? quickSelect(a, q + 1, r, index) :quickSelect(a, l, q - 1, index);
            }
        }

        public int randomPartition(int[] a, int l, int r) {
            int i = random.nextInt(r - l + 1) + l;
            swap(a, i, r);
            return partition(a, l, r);
        }

        public int partition(int[] a, int l, int r) {
            int x = a[r], i = l - 1;
            for (int j = l; j < r; ++j) {
                if (a[j] <= x) {
                    swap(a, ++i, j);
                }
            }
            swap(a, i + 1, r);
            return i + 1;
        }

        public void swap(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>((a, b) -> {
            return b - a;
        });
        queue.offer(2);
        queue.offer(1);
        queue.offer(3);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}

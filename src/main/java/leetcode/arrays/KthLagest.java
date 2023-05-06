package leetcode.arrays;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 * <p>
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 */

/**
 * 215. 数组中的第K个最大元素
 */
public class KthLagest{


    /**
     * 思路：
     * 找到第个最大元素，就是在一个序列有序递减时的第k个元素
     * 2，2，2，2
     */
    static class Solution{
        public int findKthLargest(int[] nums, int k) {
            Arrays.sort(nums);
            return nums[nums.length - k];
        }
    }

    static class Solution2{
        public int findKthLargest(int[] nums, int k) {
            int n = nums.length;
            quickSort(nums, 0, n-1);
            return nums[n - k];
        }

        private void quickSort(int[] nums, int start, int end) {
            if (start < end) {
                int p = partition(nums, start, end);
                quickSort(nums, start,p-1);
                quickSort(nums, p+1, end);
            }
        }

        private int partition(int[] nums, int start, int end) {
            int x = nums[end];
            int n = start-1;
            for (int i = start; i <= end - 1; i++) {
                if (nums[i] <= x) {
                    n ++;
                    swap(nums, n, i);
                }
            }
            swap(nums, n+1, end);
            return n+1;
        }

        private void swap(int [] nums, int i, int j) {
            int x = nums[i];
            nums[i] = nums[j];
            nums[j] = x;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int kthLargest = solution.findKthLargest(nums, 4);
        System.out.println(kthLargest);
        Assert.assertEquals(4, new Solution2().findKthLargest(nums, 4));
    }
}

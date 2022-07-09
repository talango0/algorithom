package leetcode.tree;
//给你一个整数数组 nums，请你将该数组升序排列。
//
//
//
//
//
//
// 示例 1：
//
//
//输入：nums = [5,2,3,1]
//输出：[1,2,3,5]
//
//
// 示例 2：
//
//
//输入：nums = [5,1,1,2,0,0]
//输出：[0,0,1,1,2,5]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 5 * 10⁴
// -5 * 10⁴ <= nums[i] <= 5 * 10⁴
//
// Related Topics 数组 分治 桶排序 计数排序 基数排序 排序 堆（优先队列） 归并排序 👍 604 👎 0


public class _912_排序数组 {
    class Solution {
        int [] tmp;
        public int[] sortArray(int[] nums) {
            sort(nums);
            return nums;
        }

        private void sort(int [] nums) {
            tmp = new int[nums.length];
            sort(nums, 0, nums.length-1);
        }
        private void sort(int [] nums, int lo, int hi) {
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi-lo)/2;
            sort(nums, lo, mid);
            sort(nums, mid+1, hi);
            merge(nums, lo, mid, hi);
        }
        private void merge(int [] nums, int lo, int mid, int hi) {
            for (int i = lo; i <= hi; i++) {
                tmp[i] = nums[i];
            }
            int i = lo, j = mid+1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid+1) {
                    nums[p] = tmp[j++];
                }
                else if (j == hi+1) {
                    nums[p] = tmp[i++];
                }
                else if (tmp[i] > tmp[j]) {
                    nums[p] = tmp[j++];
                }
                else {
                    nums[p] = tmp[i++];
                }
            }
        }
    }
}

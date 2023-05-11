package leetcode.tree;
//给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
//
// 你需要返回给定数组中的重要翻转对的数量。
//
// 示例 1:
//输入: [1,3,2,3,1]
//输出: 2
//
//
// 示例 2:
//输入: [2,4,3,5,1]
//输出: 3
//
//
// 注意:
// 给定数组的长度不会超过50000。
// 输入数组中的所有数字都在32位整数的表示范围内。
//
// Related Topics 树状数组 线段树 数组 二分查找 分治 有序集合 归并排序 👍 365 👎 0

public class _493_翻转对 {
    class Solution {
        int [] tmp;
        int count;

        //count[i] = COUNT(j) where j > i and nums[i] > 2*nums[j]
        //然后请你求出这个count数组的所有元素和
        //所以解题的思路当然还是在merge函数中做点儿手脚，当 nums[lo..mid] 和 nums[mid+1..hi] 这两个数组排完序后，
        // 对 nums[lo..mid] 中每一个元素 nums[i] 去 nums[mid+1..hi] 中寻找符合条件的 nums[j] 就行了。
        public int reversePairs(int[] nums) {
            sort(nums);
            return count;
        }
        private void sort(int [] nums) {
            tmp = new int[nums.length];
            sort(nums, 0, nums.length-1);
        }
        private void sort(int [] nums, int lo, int hi) {
            //注意 base case
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi-lo)/2;
            sort(nums, lo, mid);
            sort(nums, mid+1, hi);
            merge(nums, lo, mid, hi);
        }

        public void merge(int [] nums, int lo, int mid, int hi) {
            for (int i = lo; i<= hi; i++) {
                tmp[i] = nums[i];
            }

            //在合并有序数组之前，加点私货
            //for (int i = lo; i <= mid; i++) {
            //    for (int j = mid + 1; j <= hi; j++) {
            //        if ((long)nums[i] > (long)nums[j] * 2) {
            //            count++;
            //        }
            //    }
            //}
            //进行效率优化，维护左闭右开区间 [mid+1, end) 中的元素乘以2 小于 nums[i]
            //为什么 end 是开区间呢？因为这样的话可以保证初始区间 [mid+1, mid+1) 是一个空区间
            int end = mid+1;
            for (int i=lo; i<= mid; i++) {
                while (end<= hi && ((long)nums[i] > (long)nums[end]*2)) {
                    end++;
                }
                count += (end-(mid+1)); 
            }

            //数组双指针技巧，合并两个有效数组
            int i = lo, j = mid+1;
            for (int p = lo; p<=hi; p++) {
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

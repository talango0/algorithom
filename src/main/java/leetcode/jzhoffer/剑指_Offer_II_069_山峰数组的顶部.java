package leetcode.jzhoffer;

import leetcode.arrays.binarySearch._35_搜索插入位置;
import leetcode.arrays.binarySearch._852_山脉数组的峰顶索引;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _852_山脉数组的峰顶索引
 * @see _35_搜索插入位置
 */
public class 剑指_Offer_II_069_山峰数组的顶部{
    class Solution{
        public int peakIndexInMountainArray(int[] nums) {
            // 取两端都闭的二分搜索
            int left = 0, right = nums.length - 1;
            // 因为题目必然有解，所以设置 left == right 为结束条件
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] > nums[mid + 1]) {
                    // mid 本身就是峰值或左侧有一个峰值
                    right = mid;
                }
                else {
                    // mid 右侧有一个峰值
                    left = mid + 1;
                }
            }
            return left;
        }
    }
}

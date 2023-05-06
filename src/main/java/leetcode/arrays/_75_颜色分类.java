package leetcode.arrays;
//给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，
// 使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
// 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
// 必须在不使用库的sort函数的情况下解决这个问题。
//
//示例 1：
//输入：nums = [2,0,2,1,1,0]
//输出：[0,0,1,1,2,2]

//示例 2：
//输入：nums = [2,0,1]
//输出：[0,1,2]
//
//
//提示：
//
//n == nums.length
//1 <= n <= 300
//nums[i] 为 0、1 或 2
//
//
//进阶：
//
//你可以不使用代码库中的排序函数来解决这道题吗？
//你能想出一个仅使用常数空间的一趟扫描算法吗？

public class _75_颜色分类 {
    class Solution {
        /**
         * 时间复杂度：O(n)，其中 n 是数组nums 的长度。
         * <p>
         * 空间复杂度：O(1)。
         *
         * @param nums
         */
        public void sortColors(int[] nums) {
            int n = nums.length;
            // p表示[头部]范围，[0,p-1] 属于头部，初始为0，表示还没有处于头部
            int p = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0) {
                    swap(nums, p++, i);
                }
            }
            for (int i = p; i < n; i++) {
                if (nums[i] == 1) {
                    swap(nums, p++, i);
                }
            }
        }

        void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}

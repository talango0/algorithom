package leetcode.math;

import java.util.Random;
//给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
//
//实现 Solution class:
//
//Solution(int[] nums) 使用整数数组 nums 初始化对象
//int[] reset() 重设数组到它的初始状态并返回
//int[] shuffle() 返回数组随机打乱后的结果
//示例 1：
//
//输入
//["Solution", "shuffle", "reset", "shuffle"]
//[[[1, 2, 3]], [], [], []]
//输出
//[null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
//
//解释
//Solution solution = new Solution([1, 2, 3]);
//solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
//solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
//solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
//提示：
//
//1 <= nums.length <= 50
//-106 <= nums[i] <= 106
//nums 中的所有元素都是 唯一的
//最多可以调用 104 次 reset 和 shuffle
//Related Topics
//
//👍 292, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-15.
 */
public class _384_打乱数组{
    class Solution{
        private int[] nums;
        private int[] original;

        public Solution(int[] nums) {
            this.nums = nums;
            original = new int[nums.length];
            System.arraycopy(nums, 0, original, 0, nums.length);

        }

        public int[] reset() {
            System.arraycopy(original, 0, nums, 0, original.length);
            return nums;
        }

        //整个过程产生的所有可能结果有 5*4*3*2*1=5!=n! 种，所以这个算法是正确的。
        public int[] shuffle() {
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                int rand = randInt(i, n - 1);
                swap(nums, i, rand);
            }
            return nums;
        }

        /*得到一个在闭区间[min, max] 内的随机整数 */
        private int randInt(int min, int max) {
            Random rand = new Random();
            return rand.nextInt(max - min + 1) + min;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
}

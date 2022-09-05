package leetcode.arrays;
//给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
//
//题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
//
//请不要使用除法，且在 O(n) 时间复杂度内完成此题。
//
//
//
//示例 1:
//
//输入: nums = [1,2,3,4]
//输出: [24,12,8,6]
//示例 2:
//
//输入: nums = [-1,1,0,-3,3]
//输出: [0,0,9,0,0]
//
//
//提示：
//
//2 <= nums.length <= 105
//-30 <= nums[i] <= 30
//保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内
//
//
//进阶：你可以在 O(1) 的额外空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）

/**
 * 前缀积，后缀积
 * @see _370_区间加法
 */
public class _238_除自身以外数组的乘积 {
    /**
     * 时间复杂度 O(N)
     * <p>
     * 空间复杂度，如果不算返回的结果，为 O(N)
     */
    class Solution {
        public int[] productExceptSelf(int[] nums) {
            int length = nums.length;

            // L 和 R 分别表示左右两侧的乘积列表
            int[] L = new int[length];
            int[] R = new int[length];

            int[] answer = new int[length];

            // L[i] 为索引 i 左侧所有元素的乘积
            // 对于索引为 '0' 的元素，因为左侧没有元素，所以 L[0] = 1
            L[0] = 1;
            for (int i = 1; i < length; i++) {
                L[i] = nums[i - 1] * L[i - 1];
            }

            // R[i] 为索引 i 右侧所有元素的乘积
            // 对于索引为 'length-1' 的元素，因为右侧没有元素，所以 R[length-1] = 1
            R[length - 1] = 1;
            for (int i = length - 2; i >= 0; i--) {
                R[i] = nums[i + 1] * R[i + 1];
            }

            // 对于索引 i，除 nums[i] 之外其余各元素的乘积就是左侧所有元素的乘积乘以右侧所有元素的乘积
            for (int i = 0; i < length; i++) {
                answer[i] = L[i] * R[i];
            }

            return answer;
        }
    }


    /**
     * 时间复杂度 O(N)
     * <p>
     * 空间复杂度，如果不算返回的结果，为 O(1)
     */
    class Solution2 {
        public int[] productExceptSelf(int[] nums) {
            int length = nums.length;
            int[] answer = new int[length];
            // 前缀积, answer[i] 表示 nums[i]左侧的积
            answer[0] = 1;
            for (int i = 1; i < length; i++) {
                answer[i] = answer[i - 1] * nums[i - 1];
            }

            // R 为右侧所有元素之积
            // 刚开始右边没有元素，所以 R = 1
            int R = 1;
            for (int i = length - 1; i >= 0; i--) {
                // 对于索引i，左边的乘积是 answer[i],右边的乘积为 R
                answer[i] = answer[i] * R;
                // R需要包含右边的乘积，所以计算下一个结果是需要将当前值乘到 R 上
                R *= nums[i];
            }
            return answer;
        }
    }
}

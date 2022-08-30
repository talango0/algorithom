package leetcode.math;
//给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
//
//请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
//示例 1：
//
//输入：nums = [1,2,0]
//输出：3
//示例 2：
//
//输入：nums = [3,4,-1,1]
//输出：2
//示例 3：
//
//输入：nums = [7,8,9,11,12]
//输出：1
//提示：
//
//1 <= nums.length <= 5 * 105
//-231 <= nums[i] <= 231 - 1
//Related Topics
//
//👍 1586, 👎 0
/**
 * 字节
 * @author mayanwei
 * @date 2022-08-29.
 */
public class _41_缺失的第一个正数{
    class Solution {
        // 思路：由于我们只在意 [1,N] 中的数，因此我们可以下对数组进行遍历，把不在 [1,N] 范围内的数修改成任意一个大于N的数（例如 N+1）。这样，数组种的所有数就都是正数了，因此我们就可以将 标记 表示为 “负号”
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;
            for (int i = 0; i<n; i++) {
                if (nums[i] <= 0) {
                    nums[i] = n+1;
                }
            }
            for (int i = 0; i<n ; i++) {
                int num = Math.abs(nums[i]);
                if (num <= n) {
                    nums[num-1] = -Math.abs(nums[num-1]);
                }
            }
            for (int i=0; i<n; i++) {
                if (nums[i] > 0) {
                    return i+1;
                }
            }
            return n+1;
        }
    }
}

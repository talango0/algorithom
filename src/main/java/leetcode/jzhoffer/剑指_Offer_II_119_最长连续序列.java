package leetcode.jzhoffer;
//给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
//
//
//
// 示例 1：
//
//
//输入：nums = [100,4,200,1,3,2]
//输出：4
//解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
//
// 示例 2：
//
//
//输入：nums = [0,3,7,2,5,8,4,6,0,1]
//输出：9
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 10⁴
// -10⁹ <= nums[i] <= 10⁹
//
//
//
//
// 进阶：可以设计并实现时间复杂度为 O(n) 的解决方案吗？
//
//
//
//
// 注意：本题与主站 128 题相同： https://leetcode-cn.com/problems/longest-consecutive-
//sequence/
//
// Related Topics 并查集 数组 哈希表 👍 53 👎 0

import leetcode._128_最长连续序列;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2022-10-19.
 * @see _128_最长连续序列
 */
public class 剑指_Offer_II_119_最长连续序列{
    class Solution1{
        // 思路1，
        public int longestConsecutive(int[] nums) {
            Set<Integer> numSet = new HashSet<>();
            for (int num : nums) {
                numSet.add(num);
            }
            int longestStreak = 0;
            for (int num : numSet) {
                if (!numSet.contains(num - 1)) {
                    int currentNum = num;
                    int currentStreak = 1;
                    while (numSet.contains(currentNum + 1)) {
                        currentNum += 1;
                        currentStreak += 1;
                    }
                    longestStreak = Math.max(longestStreak, currentStreak);
                }
            }
            return longestStreak;
        }
    }

    class Solution{
        // 思路2，先排序在从左到右统计
        public int longestConsecutive(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            Arrays.sort(nums);
            int length = 1;
            length = getMaxLength(0, length, nums);
            return length;
        }

        public int getMaxLength(int start, int length, int[] nums) {
            if (start == nums.length - 1) {
                return length;
            }
            for (int i = start; i < nums.length - 1; i++) {
                if (nums[i] == nums[i + 1]) {
                    continue;
                }
                if (nums[i] + 1 == nums[i + 1]) {
                    length++;
                }
                else {
                    return Math.max(getMaxLength(i + 1, 1, nums), length);
                }
            }
            return length;
        }
    }

}

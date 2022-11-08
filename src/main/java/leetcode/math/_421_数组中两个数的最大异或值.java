package leetcode.math;
//给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
//
//
//
//
//
// 示例 1：
//
//
//
//
//输入：nums = [3,10,5,25,2,8]
//输出：28
//解释：最大运算结果是 5 XOR 25 = 28.
//
// 示例 2：
//
//
//输入：nums = [14,70,53,83,49,91,36,80,92,51,66,70]
//输出：127
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 2 * 10⁵
// 0 <= nums[i] <= 2³¹ - 1
//
//
// Related Topics 位运算 字典树 数组 哈希表 👍 498 👎 0

import java.util.HashSet;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2022-11-03.
 */
public class _421_数组中两个数的最大异或值{
    /**
     * a ^ b = x
     * b = x ^ a
     */
    class Solution{
        public int findMaximumXOR(int[] nums) {
            int len = nums.length;
            int mask = 0;
            int res = 0;
            for (int i = 30; i >= 0; i--) {
                mask |= (1 << i);
                Set<Integer> set = new HashSet<>();
                // 将所有的 pre ^ k(a_j) 放入哈希表中
                for (int num : nums) {
                    set.add(mask & num);
                }
                //targetMax ^ prefix = exist
                int targetMax = res | (1 << i);
                for (Integer prefix : set) {
                    if (set.contains(prefix ^ targetMax)) {
                        res = targetMax;
                        break;
                    }
                }
            }
            return res;
        }
    }
}

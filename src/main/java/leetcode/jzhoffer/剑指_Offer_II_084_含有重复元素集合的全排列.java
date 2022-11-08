package leetcode.jzhoffer;
//给定一个可包含重复数字的整数集合 nums ，按任意顺序 返回它所有不重复的全排列。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,1,2]
//输出：
//[[1,1,2],
// [1,2,1],
// [2,1,1]]
//
//
// 示例 2：
//
//
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 8
// -10 <= nums[i] <= 10
//
//
//
//
//
// 注意：本题与主站 47 题相同： https://leetcode-cn.com/problems/permutations-ii/
//
// Related Topics 数组 回溯 👍 34 👎 0


import leetcode.backtracing._47_全排列2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _47_全排列2
 */
public class 剑指_Offer_II_084_含有重复元素集合的全排列{
    class Solution{
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> tmp = new LinkedList<>();
        boolean[] used;

        public List<List<Integer>> permuteUnique(int[] nums) {
            used = new boolean[nums.length];
            // 需要排序
            Arrays.sort(nums);
            backTrace(nums);
            return res;
        }

        private void backTrace(int[] nums) {
            if (tmp.size() == nums.length) {
                res.add(new ArrayList(tmp));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (used[i]) {
                    continue;
                }
                // 排除重复包含排列
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                    continue;
                }
                tmp.addLast(nums[i]);
                used[i] = true;
                backTrace(nums);
                tmp.removeLast();
                used[i] = false;
            }
        }
    }
}

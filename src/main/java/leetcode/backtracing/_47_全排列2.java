package leetcode.backtracing;
//给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
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
// Related Topics 数组 回溯 👍 1100 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-06-19.
 * @see _46_全排列
 */
public class _47_全排列2{
    class Solution {
        private List<List<Integer>> res = new ArrayList<>();
        private LinkedList<Integer> trace = new LinkedList<>();
        private boolean []used;
        public List<List<Integer>> permuteUnique(int[] nums) {
            used = new boolean[nums.length];
            Arrays.sort(nums);
            backTrace(nums);
            return res;
        }
        private void backTrace(int []nums) {
            if (trace.size() == nums.length) {
                res.add(new LinkedList(trace));
                return;
            }
            for (int i = 0; i< nums.length; i++) {
                if (used[i]) {
                    continue;
                }
                if (i>0 && nums[i] == nums[i-1] && !used[i-1]) {
                    continue;
                }
                trace.add(nums[i]);
                used[i] = true;
                backTrace(nums);
                trace.removeLast();
                used[i] = false;
            }
        }

    }
}

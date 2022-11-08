package leetcode.jzhoffer;
//给定一个无重复元素的正整数数组 candidates 和一个正整数 target ，找出 candidates 中所有可以使数字和为目标数 target 的
//唯一组合。
//
// candidates 中的数字可以无限制重复被选取。如果至少一个所选数字数量不同，则两种组合是不同的。
//
// 对于给定的输入，保证和为 target 的唯一组合数少于 150 个。
//
//
//
// 示例 1：
//
//
//输入: candidates = [2,3,6,7], target = 7
//输出: [[7],[2,2,3]]
//
//
// 示例 2：
//
//
//输入: candidates = [2,3,5], target = 8
//输出: [[2,2,2,2],[2,3,3],[3,5]]
//
// 示例 3：
//
//
//输入: candidates = [2], target = 1
//输出: []
//
//
// 示例 4：
//
//
//输入: candidates = [1], target = 1
//输出: [[1]]
//
//
// 示例 5：
//
//
//输入: candidates = [1], target = 2
//输出: [[1,1]]
//
//
//
//
// 提示：
//
//
// 1 <= candidates.length <= 30
// 1 <= candidates[i] <= 200
// candidate 中的每个元素都是独一无二的。
// 1 <= target <= 500
//
//
//
//
//
// 注意：本题与主站 39 题相同： https://leetcode-cn.com/problems/combination-sum/
//
// Related Topics 数组 回溯 👍 37 👎 0

import leetcode.backtracing._39_组合总和;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _39_组合总和
 */
public class 剑指_Offer_II_081_允许重复选择元素的组合{
    class Solution0{
        private List<List<Integer>> res = new ArrayList<>();
        private LinkedList<Integer> trace = new LinkedList<>();
        private int sum;

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            Arrays.sort(candidates);
            backTrace(candidates, 0, target);
            return res;
        }

        private void backTrace(int[] nums, int start, int target) {
            if (sum == target) {
                res.add(new LinkedList(trace));
                return;
            }
            if (sum > target) {
                return;
            }
            for (int i = start; i < nums.length; i++) {
                trace.add(nums[i]);
                sum += nums[i];
                backTrace(nums, i, target);
                trace.removeLast();
                sum -= nums[i];
            }
        }
    }

    class Solution{
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            dfs(candidates, target, 0);
            return result;
        }

        public void dfs(int[] candidates, int target, int index) {
            if (target == 0) {
                result.add(new ArrayList<>(temp));
                return;
            }
            for (int i = index; i < candidates.length; i++) {
                if (candidates[i] > target) {
                    continue;
                }
                temp.add(candidates[i]);
                dfs(candidates, target - candidates[i], i);
                temp.remove(temp.size() - 1);
            }
        }
    }
}

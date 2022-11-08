package leetcode.backtracing;
//给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的每个数字在每个组合中只能使用 一次 。
//
// 注意：解集不能包含重复的组合。
//
//
//
// 示例 1:
//
//
//输入: candidates = [10,1,2,7,6,1,5], target = 8,
//输出:
//[
//[1,1,6],
//[1,2,5],
//[1,7],
//[2,6]
//]
//
// 示例 2:
//
//
//输入: candidates = [2,5,2,1,2], target = 5,
//输出:
//[
//[1,2,2],
//[5]
//]
//
//
//
// 提示:
//
//
// 1 <= candidates.length <= 100
// 1 <= candidates[i] <= 50
// 1 <= target <= 30
//
// Related Topics 数组 回溯 👍 1001 👎 0

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-06-19.
 */
public class _40_组合总和2 {
    class Solution {
        List<List<Integer>> res = new LinkedList<>();
        LinkedList<Integer> trace = new LinkedList<>();
        int traceSum;
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            backTrace(candidates, 0, target);
            return res;
        }

        private void backTrace(int [] nums, int start,int target) {
            if (traceSum == target) {
                res.add(new LinkedList(trace));
                return;
            }
            if (traceSum > target) {
                return;
            }
            for(int i = start; i< nums.length; i++) {
                if(i > start && nums[i] == nums[i-1] ) {
                    continue;
                }
                trace.add(nums[i]);
                traceSum += nums[i];
                backTrace(nums, i+1, target);
                trace.removeLast();
                traceSum -= nums[i];
            }
        }
    }
}

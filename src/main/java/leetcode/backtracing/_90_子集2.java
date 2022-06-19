package leetcode.backtracing;
//给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
//
// 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
//
//
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,2]
//输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
//
//
// 示例 2：
//
//
//输入：nums = [0]
//输出：[[],[0]]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10
// -10 <= nums[i] <= 10
//
//
//
// Related Topics 位运算 数组 回溯 👍 856 👎 0

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-06-19.
 */
public class _90_子集2{
    class Solution {
        private List<List<Integer>> res = new LinkedList<>();
        private LinkedList<Integer> trace = new  LinkedList<>();
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            //排序，将相同的元素放在一起。
            Arrays.sort(nums);
            backTrace(nums, 0);
            return res;
        }

        private void backTrace(int [] nums, int start){
            res.add(new LinkedList<>(trace));
            for (int i = start; i < nums.length; i++) {
                if (i > start && nums[i] == nums[i-1]){
                    continue;
                }
                trace.add(nums[i]);
                backTrace(nums, i+1);
                trace.removeLast();
            }
        }
    }
}

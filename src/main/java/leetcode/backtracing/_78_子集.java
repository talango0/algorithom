package leetcode.backtracing;

//给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
//
// 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,3]
//输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
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
// nums 中的所有元素 互不相同
//
// Related Topics 位运算 数组 回溯 👍 1681 👎 0


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-06-19.
 */
public class _78_子集{
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> trace = new LinkedList<>();
        public List<List<Integer>> subsets(int[] nums) {
            backTrace(nums, 0);
            return res;
        }

        public void backTrace(int [] nums,int start) {
            res.add(new LinkedList<Integer>(trace));
            for(int i = start; i< nums.length; i++){
                trace.add(nums[i]);
                backTrace(nums, i+1);
                trace.removeLast();
            }
        }
    }
}

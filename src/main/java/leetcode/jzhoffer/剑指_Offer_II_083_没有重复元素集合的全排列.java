package leetcode.jzhoffer;

import leetcode.backtracing._46_全排列;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//给定一个不含重复数字的整数数组 nums ，返回其 所有可能的全排列 。可以 按任意顺序 返回答案。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//
//
// 示例 2：
//
//
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
//
//
// 示例 3：
//
//
//输入：nums = [1]
//输出：[[1]]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 6
// -10 <= nums[i] <= 10
// nums 中的所有整数 互不相同
//
//
//
//
//
// 注意：本题与主站 46 题相同：https://leetcode-cn.com/problems/permutations/
//
// Related Topics 数组 回溯 👍 39 👎 0


/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _46_全排列
 */
public class 剑指_Offer_II_083_没有重复元素集合的全排列{
    class Solution {
        private List<List<Integer>> res = new ArrayList<>();
        private LinkedList<Integer> trace = new LinkedList<>();
        boolean [] used;
        public List<List<Integer>> permute(int[] nums) {
            used = new boolean [nums.length];
            backTrace(nums);
            return res;
        }
        private void backTrace(int [] nums) {
            if (trace.size() == nums.length) {
                res.add(new LinkedList(trace));
                return;
            }

            for(int i = 0; i < nums.length; i++) {
                if( used[i]){
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

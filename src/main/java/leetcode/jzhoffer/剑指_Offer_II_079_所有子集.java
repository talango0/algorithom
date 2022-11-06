package leetcode.jzhoffer;

import leetcode.backtracing._78_子集;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//给定一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
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
//
//
//
//
// 注意：本题与主站 78 题相同： https://leetcode-cn.com/problems/subsets/
//
// Related Topics 位运算 数组 回溯 👍 53 👎 0

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _78_子集
 */
public class 剑指_Offer_II_079_所有子集{
    class Solution0{
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> trace = new LinkedList<>();

        public List<List<Integer>> subsets(int[] nums) {
            backTrace(nums, 0);
            return res;
        }

        public void backTrace(int[] nums, int start) {
            res.add(new LinkedList<Integer>(trace));
            for (int i = start; i < nums.length; i++) {
                trace.add(nums[i]);
                backTrace(nums, i + 1);
                trace.removeLast();
            }
        }
    }

    class Solution{
        List<List<Integer>> ans;

        public List<List<Integer>> subsets(int[] nums) {
            ans = new ArrayList<>();
            dfs(nums, new ArrayList<>(), 0);
            return ans;
        }

        public void dfs(int[] nums, ArrayList<Integer> list, int index) {
            for (; index < nums.length; index++) {
                ArrayList<Integer> list1 = new ArrayList<>(list);
                list1.add(nums[index]);
                dfs(nums, list1, index + 1);
            }
            ans.add(list);
        }
    }
}

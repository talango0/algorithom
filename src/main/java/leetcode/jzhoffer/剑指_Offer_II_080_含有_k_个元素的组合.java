package leetcode.jzhoffer;

import leetcode.backtracing._60_排列序列;
import leetcode.backtracing._77_组合;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
//
//
//
// 示例 1:
//
//
//输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//]
//
// 示例 2:
//
//
//输入: n = 1, k = 1
//输出: [[1]]
//
//
//
// 提示:
//
//
// 1 <= n <= 20
// 1 <= k <= n
//
//
//
//
//
// 注意：本题与主站 77 题相同： https://leetcode-cn.com/problems/combinations/
//
// Related Topics 数组 回溯 👍 35 👎 0

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _77_组合
 * @see _60_排列序列
 */
public class 剑指_Offer_II_080_含有_k_个元素的组合{
    class Solution {
        private List<List<Integer>> res = new ArrayList<>();
        private LinkedList<Integer> trace = new LinkedList<>();
        public List<List<Integer>> combine(int n, int k) {
            int[] arr = IntStream.rangeClosed(1, n).toArray();
            backTrace(arr, 0, k);
            return res;
        }
        public void backTrace(int [] nums, int start,int k) {
            if (trace.size() == k) {
                res.add(new LinkedList<>(trace));
                return;
            }
            for(int i = start; i< nums.length; i++){
                trace.add(nums[i]);
                backTrace(nums, i+1, k);
                trace.removeLast();
            }
        }

    }
}

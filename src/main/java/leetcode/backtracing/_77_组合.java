package leetcode.backtracing;

//给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
//
// 你可以按 任何顺序 返回答案。
//
//
//
// 示例 1：
//
//
//输入：n = 4, k = 2
//输出：
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//]
//
// 示例 2：
//
//
//输入：n = 1, k = 1
//输出：[[1]]
//
//
//
// 提示：
//
//
// 1 <= n <= 20
// 1 <= k <= n
//
// Related Topics 回溯 👍 1021 👎 0


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author mayanwei
 * @date 2022-06-19.
 */
public class _77_组合{

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

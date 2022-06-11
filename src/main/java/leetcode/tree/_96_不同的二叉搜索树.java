package leetcode.tree;
//给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
//
//
//
// 示例 1：
//
//
//输入：n = 3
//输出：5
//
//
// 示例 2：
//
//
//输入：n = 1
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= n <= 19
//
// Related Topics 树 二叉搜索树 数学 动态规划 二叉树 👍 1792 👎 0


/**
 * @author mayanwei
 * @date 2022-06-11.
 */
public class _96_不同的二叉搜索树{
    class Solution{
        int[][] mem;

        public int numTrees(int n) {
            //备忘录初始化为0
            mem = new int[n + 1][n + 1];
            return count(1, n);
        }

        public int count(int l, int r) {
            if (l > r) {
                return 1;
            }
            //查备忘录
            if (mem[l][r] != 0) {
                return mem[l][r];
            }

            int res = 0, left = 0, right = 0;
            for (int i = l; i <= r; i++) {
                left = count(l, i - 1);
                right = count(i + 1, r);
                res += left * right;
            }
            mem[l][r] = res;
            return res;
        }
    }
}

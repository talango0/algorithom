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
 * @see _95_不同的二叉搜索树2
 */
public class _96_不同的二叉搜索树{
    //思考
    //定义区间 [lo, hi] 的数字能组成 count(lo，hi) 种 BST
    int numsTree(int lo, int hi) {
        return count(lo, hi);
    }

    /**
     * <pre>
     * ┌─────────────────────────────────────────┐
     * │  count(1, 3)                            │
     * │     count(1, 1) count(2,1)              │
     * │     count(2, 3) count(3,3) count(4,3)   │
     * │     count(1, 2) count(2,2) count(3,2)   │
     * │     count(3, 3) count(4,3)              │
     * └─────────────────────────────────────────┘
     * </pre>
     * 存在重叠子问题
     * @param lo
     * @param hi
     * @return
     */
    private int count(int lo, int hi) {
        // base case,这里为1，空也是一种情况，对应着空节点 null，所以要返回1而不能返回0
        if (lo < hi) {
            return 1;
        }
        int res = 0;
        for (int i = lo; i <= hi; i++) {
            // 把 i 看作为根节点
            //左子树
            int left = count(lo, i - 1);
            int right = count(i + 1, hi);
            // 左右子树的组合数乘积为可能的组合数
            res += left * right;
        }
        return res;
    }

    /**
     * 上面的实现复杂度非常高，存在重叠子问题
     */
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

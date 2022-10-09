package leetcode.tree;

//路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不
//一定经过根节点。
//
// 路径和 是路径中各节点值的总和。
//
// 给你一个二叉树的根节点 root ，返回其 最大路径和 。
//
//
//
// 示例 1：
//
//
//输入：root = [1,2,3]
//输出：6
//解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
//
// 示例 2：
//
//
//输入：root = [-10,9,20,null,null,15,7]
//输出：42
//解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
//
//
//
//
// 提示：
//
//
// 树中节点数目范围是 [1, 3 * 10⁴]
// -1000 <= Node.val <= 1000
//
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 1615 👎 0

// 二叉树中最大路径和

import leetcode.jzhoffer.剑指_Offer_II_051_节点之和最大的路径;

/**
 * 字节
 * @see 剑指_Offer_II_051_节点之和最大的路径
 * @see _366_寻找二叉树的叶子节点
 * @see _124_二叉树中最大路径和
 * @see _543_二叉树的直径
 */
public class _124_二叉树中最大路径和{
    //后序遍历，选择分支中的最大值与根节点相加
    static class Solution {
        private int res = Integer.MIN_VALUE;
        public int maxPathSum(TreeNode root) {
            onSiderMax(root);
            return res;

        }
        private int onSiderMax(TreeNode root){
            if (root == null) {
                return 0;
            }
            int left = Math.max(0, onSiderMax(root.left));
            int right = Math.max(0, onSiderMax(root.right));
            res = Math.max(res,left+right+root.val);
            return Math.max(left,right)+root.val;
        }
    }
}

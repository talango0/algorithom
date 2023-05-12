package leetcode.tree;
//给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
//
//
//
//
//
// 示例 1：
//
//
//输入：n = 3
//输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
//
//
// 示例 2：
//
//
//输入：n = 1
//输出：[[1]]
//
//
//
//
// 提示：
//
//
// 1 <= n <= 8
//
//
//
// Related Topics 树 二叉搜索树 动态规划 回溯 二叉树 👍 1227 👎 0

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-06-11.
 * @see _96_不同的二叉搜索树
 */
public class _95_不同的二叉搜索树2{
    /**
     * 思路：
     * 1. 穷举 root 节点的所有可能
     * 2. 递归构造出左右子树的所有合法 BST
     * 3. 给 root 节点穷举所有左右子树的组合
     */
    class Solution {
        public List<TreeNode> generateTrees(int n) {
            if (n == 0) {
                return new LinkedList<TreeNode>();
            }

            return build(1, n);
        }

        //构造不闭区间 [l, r] 构造 BST
        public List<TreeNode> build (int l, int r) {
            List<TreeNode> res = new LinkedList<>();
            //base case
            if (l > r) {
                res.add(null);
                return res;
            }
            //1. 群举 root 节点的所有可能
            for (int i = l; i <= r; i++) {
                //2. 递归构造左右子树的所有合法 BST
                List<TreeNode> lTrees = build(l, i-1);
                List<TreeNode> rTrees = build(i+1,r);
                //3. 给 root 节点群举所有的左右子树
                for (TreeNode lTree: lTrees) {
                    for (TreeNode rTree: rTrees){
                        TreeNode root = new TreeNode(i);
                        root.left = lTree;
                        root.right = rTree;
                        res.add(root);
                    }
                }
            }

            return res;
        }
    }
}

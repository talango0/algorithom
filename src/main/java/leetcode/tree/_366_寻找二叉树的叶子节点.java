package leetcode.tree;

import java.util.ArrayList;
import java.util.List;

//示例:
//输入: [1,2,3,4,5]
//
//          1
//         / \
//        2   3
//       / \
//      4   5
//
//输出: [[4,5,3],[2],[1]]
//
//解释:
//
//1. 删除叶子节点 [4,5,3] ，得到如下树结构：
//
//          1
//         /
//        2
//
//
//2. 现在删去叶子节点 [2] ，得到如下树结构：
//
//          1
//
//
//3. 现在删去叶子节点 [1] ，得到空树：
//
//          []
public class _366_寻找二叉树的叶子节点 {
    /**
     * 给一颗二叉树，请按一下要求的顺序手机他的全部节点
     * 1. 一次从左向右，每次收集并删除所有的叶子节点
     * 2. 重复如上过程直到整棵树为空为止
     */

    class Solution {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            dept(root, res);
            return res;
        }
        private int dept(TreeNode root, List<List<Integer>> res) {
            if (root == null) {
                return -1;
            }
            int leftDepth = dept(root.left, res);
            int rightDepth = dept(root.right, res);
            int level = Math.max(leftDepth, rightDepth) + 1;
            if(level == res.size()){
               res.add(new ArrayList<>());
            }
            res.get(level).add(root.val);
            return level;
        }

    }
}

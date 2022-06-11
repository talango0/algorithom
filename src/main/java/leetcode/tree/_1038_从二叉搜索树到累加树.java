package leetcode.tree;
//给定一个二叉搜索树 root (BST)，请将它的每个节点的值替换成树中大于或者等于该节点值的所有节点值之和。
//
// 提醒一下， 二叉搜索树 满足下列约束条件：
//
//
// 节点的左子树仅包含键 小于 节点键的节点。
// 节点的右子树仅包含键 大于 节点键的节点。
// 左右子树也必须是二叉搜索树。
//
//
//
//
// 示例 1：
//
//
//
//
//输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
//输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
//
//
// 示例 2：
//
//
//输入：root = [0,null,1]
//输出：[1,null,1]
//
//
//
//
// 提示：
//
//
// 树中的节点数在 [1, 100] 范围内。
// 0 <= Node.val <= 100
// 树中的所有值均 不重复 。
//
//
//
//
// 注意：该题目与 538: https://leetcode-cn.com/problems/convert-bst-to-greater-tree/ 相同
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 168 👎 0


/**
 * @author mayanwei
 * @date 2022-06-11.
 */
public class _1038_从二叉搜索树到累加树{
    class Solution {
        public TreeNode bstToGst(TreeNode root) {
            return travers(root);
        }

        int sum;
        public TreeNode travers(TreeNode root){
            if (root == null) {
                return null;
            }

            travers(root.right);
            sum += root.val;
            root.val = sum;
            travers(root.left);
            return root;
        }
    }
}

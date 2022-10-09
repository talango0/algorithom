package leetcode.tree;
//给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
//
//
//
// 示例 :
//给定二叉树
//
//           1
//         / \
//        2   3
//       / \
//      4   5
//
//
// 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
//
//
//
// 注意：两结点之间的路径长度是以它们之间边的数目表示。
// Related Topics 树 深度优先搜索 二叉树 👍 1081 👎 0

/**
 * @see _366_寻找二叉树的叶子节点
 * @see _124_二叉树中最大路径和
 */
public class _543_二叉树的直径{
    class Solution{

        //记录最大直径长度
        int maxDiameter = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            traverse(root);
            return maxDiameter;
        }

        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            //
            //计算每个节点的直径
            int leftMax = maxDepth(root.left);
            int rightMax = maxDepth(root.right);
            //更新全局最大直径
            maxDiameter = Math.max(leftMax + rightMax, maxDiameter);
            traverse(root.left);
            traverse(root.right);
        }

        int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            //前序位置无法获取子树信息，所以只能让每个节点调用 maxDepth函数去计算子树的深度。
            int leftMax = maxDepth(root.left);
            int rightMax = maxDepth(root.right);
            return 1 + Math.max(leftMax, rightMax);
        }
    }
}

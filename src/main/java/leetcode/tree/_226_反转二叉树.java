package leetcode.tree;
//给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
//
//
// 示例 2：
//
//
//
//
//输入：root = [2,1,3]
//输出：[2,3,1]
//
//
// 示例 3：
//
//
//输入：root = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 树中节点数目范围在 [0, 100] 内
// -100 <= Node.val <= 100
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1339 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

public class _226_反转二叉树{

    class Solution{
        public TreeNode invertTree(TreeNode root) {
            //遍历二叉树
            traverse(root);
            return root;
        }

        private void traverse(TreeNode node) {
            if (node == null) {
                return;
            }
            //前序位置
            //每一个节点需要做的事情就是交换它的左右子节点
            TreeNode left = node.left;
            TreeNode right = node.right;
            node.right = left;
            node.left = right;
            //遍历框架去递归遍历左右子树
            traverse(left);
            traverse(right);

        }
    }

    class Solution2{
        /**
         * 用分解思维
         * 定义：将以root 为根的这颗二叉树翻转，返回翻转后的二叉树的根节点
         *
         * @param root
         * @return
         */
        public TreeNode invertTree(TreeNode root) {
            if (root == null) {
                return root;
            }
            //利用函数定义先翻转左右子树
            TreeNode left = invertTree(root.left);
            TreeNode right = invertTree(root.right);

            //然后交换左右子节点
            root.left = right;
            root.right = left;

            //和定义逻辑一样，以root为根的这棵树已经被翻转，返回root
            return root;
        }

    }
}
